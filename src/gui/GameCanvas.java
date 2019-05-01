package gui;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

import animation.AnimationClip;
import app.MainApp;
import constants.ImageHolder;
import constants.SystemCache;
import javafx.animation.AnimationTimer;
import javafx.animation.Transition;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import logic.GameObjectTag;
import object.GameObject;
import object.block.BreakableBlock;
import object.block.UnbreakableBlock;
import object.entity.Player;
import object.loot.Mint;
import object.overlay.Bar;
import object.overlay.Popup;
import object.overlay.Pointer;
import object.tile.Ground;
import utility.Utility;

public class GameCanvas extends Canvas {
	public static final int PIXEL_CELLSIZE = 80;
	public static final double CAMERA_LERP_FACTOR = 0.1;
	
	private int cellsWidth;
	private int cellsHeight;
	
	private GraphicsContext gc;

	//camera
	private Point2D viewPosition;

	//gameobjects
	private GameObject pursueObject;
	private Set<GameObject> gameObjects;
	private Queue<GameObject> instantiationQueue;
	private Queue<GameObject> destroyQueue;
	
	public GameObject sample;
	//animation
	private double deltaTime;
	private AnimationTimer gameLoop;
	
	//debug
	private boolean debug;

	public GameCanvas() {		
		SystemCache.getInstance().gameCanvas = this;
		this.gc = getGraphicsContext2D();
		this.gameObjects = new TreeSet<GameObject>((GameObject a, GameObject b) -> {
			if(a.getZOrder() == b.getZOrder()) 
				return a.hashCode() - b.hashCode();
			return a.getZOrder() - b.getZOrder();
		});
		this.instantiationQueue = new LinkedList<GameObject>();
		this.destroyQueue = new LinkedList<GameObject>();
		setWidth(MainApp.WINDOW_WIDTH);
		setHeight(MainApp.WINDOW_HEIGHT);
		setViewPosition(scaledPoint2D(getPixelScreenSize().multiply(0.5)));
		
		setCellDimension(13, 13);
		//wait for manage
		Player slime = new Player();
		slime.setPosition(new Point2D(7,7));
//		slime.setScale(new Point2D(3,2));
		UnbreakableBlock block = new UnbreakableBlock();
		block.getCollisionSystem().addBoxCollider(-0.5,-0.3,1,1);
		block.setPosition(new Point2D(3,1));

		BreakableBlock block2 = new BreakableBlock();
		block2.setPosition(new Point2D(2,1));
		
		BreakableBlock block3 = new BreakableBlock();
		block3.setPosition(new Point2D(3,3));
		sample = block3;
		
		Mint coins = new Mint(Mint.Type.COIN_PILE_1);
		coins.setPosition(new Point2D(4,4));
		Mint coins2 = new Mint(Mint.Type.SINGLE_COIN);
		coins2.setPosition(new Point2D(4.5,5));
		Mint coins3 = new Mint(Mint.Type.COIN_PILE_0);
		coins3.setPosition(new Point2D(5.5, 5));
		Mint coins4 = new Mint(Mint.Type.SINGLE_COIN);
		coins4.setPosition(new Point2D(6,4));
		Mint coins5 = new Mint(Mint.Type.SINGLE_COIN);
		coins5.setPosition(new Point2D(7,5));
		coins.setScale(new Point2D(0.8,0.8));
		coins2.setScale(new Point2D(0.8,0.8));
		coins3.setScale(new Point2D(0.8,0.8));
		coins4.setScale(new Point2D(0.8,0.8));
		coins5.setScale(new Point2D(0.8,0.8));
		GameObject unknown = new GameObject() {
			@Override
			public void update(double deltaTime) {}
			@Override
			public void start() {}
		};
		for(int i = 0; i < getCellWidth(); i++) {
			for(int j = 0; j < getCellHeight(); j++) {
				Ground ground = new Ground(Ground.Style.GROUND);
				ground.setPosition(new Point2D(i, j));
				instantiate(ground);
			}
		}
		instantiate(block);
		instantiate(block2);
		instantiate(block3);
		instantiate(slime);
		instantiate(coins);
		instantiate(coins2);
		instantiate(coins3);
		instantiate(coins4);
		instantiate(coins5);
		instantiate(new Pointer());
		
		setPursueObject(slime);
		
		gameLoop = new AnimationTimer() {
			private double last = System.nanoTime();
			public void handle(long now) {
				setDeltaTime((now - last) / 1e9);
				//--------------------------------------------
				//GAMEOBJECTS
				clearScreen();
				proceedOverGameObjects();
				
				//GAMECANVAS
				if(pursueObject != null) pursue();
				if(SystemCache.getInstance().gameEvent.getSingleKeyUp(KeyCode.F8))
					toggleDebug();
				SystemCache.getInstance().gameEvent.clearSingleKeyBuffer();
				//--------------------------------------------
				last = now;
			}
		};
		gameLoop.start();
	}

	public void proceedOverGameObjects() {
		//translate graphics context for drawing object depends on camera position
		Point2D translatePos = pixeledPoint2D(getViewPosition()).subtract(getPixelScreenSize().multiply(0.5));
		gc.translate(-translatePos.getX(), -translatePos.getY());	

		//remove destroyed gameobject from set
		for(GameObject gameObject : getGameObjects())
			if(gameObject.isDestroyed())
				destroyQueue.add(gameObject);
		while(!destroyQueue.isEmpty())
			getGameObjects().remove(destroyQueue.poll());
		
		//instantiate objects in queue (for avoiding concurrent modification)
		while(!this.instantiationQueue.isEmpty()) {
			GameObject gameObject = this.instantiationQueue.poll();
			getGameObjects().add(gameObject);
			if(!gameObject.isStatic())
				gameObject.start();
		}
		
		//iterate over gameobject and render
		for(GameObject gameObject : getGameObjects()) {
			//reduce process of updating some gameobjects
			if(!gameObject.isStatic())
				gameObject.update(deltaTime);
			gameObject.renderOver(this);
		}
		//(if) debug (draw hitbox/pivot)
		if(this.debug) {
			for(GameObject gameObject : getGameObjects()) {
				gameObject.getCollisionSystem().renderOver(this);
			}
			for(GameObject gameObject : getGameObjects()) {
				gameObject.renderPivot(this);
			}
		}
		
		//translate graphics context back
		gc.translate(translatePos.getX(), translatePos.getY());
	}
	
	//general functions
	public void clearScreen() {
		this.gc.clearRect(0, 0, MainApp.WINDOW_WIDTH, MainApp.WINDOW_HEIGHT);
	}
	public void pursue() {
		setViewPosition(Utility.lerpPoint2D(getViewPosition(), pursueObject.getPosition(), CAMERA_LERP_FACTOR));
	}
	public static Point2D pixeledPoint2D(Point2D point) {
		return point.multiply(PIXEL_CELLSIZE);
	}
	public static Point2D scaledPoint2D(Point2D point) {
		return point.multiply(1.0/PIXEL_CELLSIZE);
	}
	public static Point2D getPixelScreenSize() {
		return new Point2D(MainApp.WINDOW_WIDTH, MainApp.WINDOW_HEIGHT);
	}
	public Point2D mouseToScaledPoint2D(Point2D mousePoint) {
		return scaledPoint2D(mousePoint).subtract(scaledPoint2D(getPixelScreenSize()).multiply(0.5)).add(getViewPosition());
	}
	public void toggleDebug() {
		this.debug = !this.debug;
	}
	public void instantiate(GameObject gameObject) {
		this.instantiationQueue.add(gameObject);
	}
	
	//getter and setter
	public Point2D getViewPosition() {
		return this.viewPosition;
	}
	public void setViewPosition(Point2D viewPosition) {
		Point2D topLeft = scaledPoint2D(getPixelScreenSize().multiply(0.5));
		Point2D bottomRight = new Point2D(this.cellsWidth, this.cellsHeight).subtract(topLeft);
		Point2D clampedPosition = new Point2D(
				Math.max(Math.min(viewPosition.getX(), bottomRight.getX()), topLeft.getX()),
				Math.max(Math.min(viewPosition.getY(), bottomRight.getY()), topLeft.getY()));
		this.viewPosition = clampedPosition;
	
	}
	public GameObject getPursueObject() {
		return this.pursueObject;
	}
	public void setPursueObject(GameObject pursueObject) {
		this.pursueObject = pursueObject;
	}
	public double getDeltaTime() {
		return this.deltaTime;
	}
	private void setDeltaTime(double deltaTime) {
		this.deltaTime = deltaTime;
	}
	public Set<GameObject> getGameObjects() {
		return this.gameObjects;
	}
	public void setCellDimension(int width, int height) {
		this.cellsWidth = Math.max(width, (int)Math.ceil(1.0*MainApp.WINDOW_WIDTH/PIXEL_CELLSIZE));
		this.cellsHeight = Math.max(height, (int)Math.ceil(1.0*MainApp.WINDOW_HEIGHT/PIXEL_CELLSIZE));
	}
	public int getCellWidth() {
		return this.cellsWidth;
	}
	public int getCellHeight() {
		return this.cellsHeight;
	}
}
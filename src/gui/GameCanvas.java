package gui;

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
import object.GameObject;
import object.block.BreakableBlock;
import object.block.UnbreakableBlock;
import object.entity.Player;
import object.overlay.Pointer;
import utility.Utility;

public class GameCanvas extends Canvas {
	public static final int PIXEL_CELLSIZE = 80;
	public static final double CAMERA_LERP_FACTOR = 0.03;
	
	private GraphicsContext gc;

	//camera
	private Point2D viewPosition;

	//gameobjects
	private GameObject pursueObject;
	private Set<GameObject> gameObjects;
	
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
		setWidth(MainApp.WINDOW_WIDTH);
		setHeight(MainApp.WINDOW_HEIGHT);
		setViewPosition(scaledPoint2D(getPixelScreenSize().multiply(0.5)));
		
		//wait for manage
		Player slime = new Player();

//		slime.setScale(new Point2D(3,2));
		UnbreakableBlock block = new UnbreakableBlock();
		block.getCollisionSystem().addBoxCollider(-0.5,-0.3,1,1);
		block.setPosition(new Point2D(3,1));

		BreakableBlock block2 = new BreakableBlock();
		block2.setPosition(new Point2D(2,1));
		block2.setPivot(new Point2D(1,1));
		
		BreakableBlock block3 = new BreakableBlock();
		block3.setPosition(new Point2D(3,3));
		
		UnbreakableBlock block4 = new UnbreakableBlock();
		block4.setPosition(new Point2D(3, 5));
		block4.setScale(new Point2D(3,3));
		block4.setRotation(new Rotate(135));

		GameObject unknown = new GameObject() {
			@Override
			public void update(double deltaTime) {}
			@Override
			public void start() {}
		};
		unknown.setPosition(new Point2D(6,6));
		unknown.getCollisionSystem().addBoxCollider(-0.5, -0.5,1, 1.25);
		unknown.setPivot(new Point2D(0.7, 0.7));
		unknown.setRotation(new Rotate(30));
		unknown.setScale(new Point2D(1.5,1));
		gameObjects.add(block);
		gameObjects.add(block2);
		gameObjects.add(block3);
		gameObjects.add(slime);
		gameObjects.add(block4);
		gameObjects.add(unknown);
		gameObjects.add(new Pointer());
		setPursueObject(slime);
		
		invokeStartOverGameObjects();
		
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

	public void invokeStartOverGameObjects() {
		for(GameObject gameObject : this.gameObjects) {
			if(!gameObject.isStatic) {
				gameObject.start();
			}
		}
	}
	public void proceedOverGameObjects() {
		//translate graphics context for drawing object depends on camera position
		Point2D translatePos = pixeledPoint2D(getViewPosition()).subtract(getPixelScreenSize().multiply(0.5));
		gc.translate(-translatePos.getX(), -translatePos.getY());	

		//iterate over gameobject and render
		for(GameObject gameObject : this.gameObjects) {
			//reduce process of updating some gameobject
			if(!gameObject.isStatic)
				gameObject.update(deltaTime);
			gameObject.renderOver(this);
		}
		//(if) debug (draw hitbox/pivot)
		if(this.debug) {
			for(GameObject gameObject : this.gameObjects) {
				gameObject.getCollisionSystem().renderOver(this);
			}
			for(GameObject gameObject : this.gameObjects) {
				gameObject.renderPivot(this);
			}
		}
		
		//translate graphics context back
		gc.translate(translatePos.getX(), translatePos.getY());
	}
	
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
	
	public Point2D getViewPosition() {
		return this.viewPosition;
	}
	public void setViewPosition(Point2D viewPosition) {
		this.viewPosition = viewPosition;
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
	
	public void toggleDebug() {
		this.debug = !this.debug;
	}
}
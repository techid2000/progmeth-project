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
	public Set<GameObject> gameObjects;
	
	//animation
	private double deltaTime;
	private AnimationTimer gameLoop;

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
		
		Player slime = new Player() {
			public void start() {}
		};
		slime.setMoveSpeed(2);
		slime.setAnimationClip(new AnimationClip(ImageHolder.getInstance().slime, 500));
		slime.setZOrder(0);
		slime.setPivot(new Point2D(0.5, 0.5));
		slime.setPosition(new Point2D(0,0));
		
		UnbreakableBlock block = new UnbreakableBlock();
		block.setPosition(new Point2D(3,1));
		block.setZOrder(1);

		BreakableBlock block2 = new BreakableBlock();
		block2.setPosition(new Point2D(2,1));
		block2.setPivot(new Point2D(1,1));
		block2.setZOrder(2);
		
		BreakableBlock block3 = new BreakableBlock();
		block3.setPosition(new Point2D(3,2));
		block3.setZOrder(2);

		gameObjects.add(block);
		gameObjects.add(block2);
		gameObjects.add(block3);
		gameObjects.add(slime);
		gameObjects.add(new Pointer());
		pursueObject = slime;
		
		
		
		
		invokeStartOverGameObjects();
		
		gameLoop = new AnimationTimer() {
			private double last = System.nanoTime();
			public void handle(long now) {
				setDeltaTime((now - last) / 1e9);
				//--------------------------------------------
				clearScreen();
				proceedOverGameObjects();
				if(pursueObject != null) pursue();
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
}
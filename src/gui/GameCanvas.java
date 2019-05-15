package gui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import com.sun.org.apache.bcel.internal.generic.LASTORE;

import animation.AnimationClip;
import app.MainApp;
import constants.ImageHolder;
import constants.SystemCache;
import javafx.animation.AnimationTimer;
import javafx.animation.Transition;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import javafx.util.Pair;
import logic.GameObjectTag;
import object.GameObject;
import object.block.Block;
import object.block.BreakableBlock;
import object.block.UnbreakableBlock;
import object.entity.Player;
import object.entity.Slime;
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

	// camera
	private Point2D viewPosition;

	// gameobjects
	private GameObject pursueObject;
	private List<GameObject> gameObjects; // todo: categorize gameobjects by tag
	private Queue<GameObject> instantiationQueue;

	// animation
	private AnimationTimer gameLoop;
	private double lastNanoTime = System.nanoTime();
	
	// debug
	private boolean debug;

	public GameCanvas() {
		setup();
		buildGame();
		loop();
	}

	public void setup() {
		SystemCache.getInstance().gameCanvas = this;

		this.gc = getGraphicsContext2D();
		this.gameObjects = new ArrayList<GameObject>();
		
		this.instantiationQueue = new LinkedList<GameObject>();

		setWidth(MainApp.WINDOW_WIDTH);
		setHeight(MainApp.WINDOW_HEIGHT);
	}

	private void buildGame() {
		setCellDimension(15, 13);
		setViewPosition(scaledPoint2D(getPixelScreenSize().multiply(0.5)));

		Player player = new Player();
		instantiate(player);
		setPursueObject(player);
		
		player.setPosition(new Point2D(7, 7));

		// border
		for (int i = 0; i < getCellWidth(); i++) {
			UnbreakableBlock topBlock = new UnbreakableBlock();
			topBlock.setPosition(new Point2D(i,0));
			instantiate(topBlock);
			
			UnbreakableBlock bottomBlock = new UnbreakableBlock();
			bottomBlock.setPosition(new Point2D(i,getCellHeight()-1));
			instantiate(bottomBlock);
		}
		
		for(int j = 1 ; j < getCellHeight() -1 ;j++) {
			UnbreakableBlock topBlock = new UnbreakableBlock();
			topBlock.setPosition(new Point2D(0,j));
			instantiate(topBlock);
			
			UnbreakableBlock bottomBlock = new UnbreakableBlock();
			bottomBlock.setPosition(new Point2D(getCellWidth()-1,j));
			instantiate(bottomBlock);
		}
		for(int i=1; i<=2; i++) {
			BreakableBlock block = new BreakableBlock();
			block.setPosition(new Point2D(i, 3));
			instantiate(block);
		}
		for(int i=3; i<=4; i++) {
			for(int j=3; j<=4; j++) {
				BreakableBlock block = new BreakableBlock();
				block.setPosition(new Point2D(j, i));
				instantiate(block);
			}
		}
		
		for(int i=3; i<=4; i++) {
			for(int j=10; j<=11; j++) {
				BreakableBlock block = new BreakableBlock();
				block.setPosition(new Point2D(j, i));
				instantiate(block);
			}
		}

		for(int i=8; i<=9; i++) {
			for(int j=3; j<=4; j++) {
				BreakableBlock block = new BreakableBlock();
				block.setPosition(new Point2D(j, i));
				instantiate(block);
			}
		}
		
		for(int i=8; i<=9; i++) {
			for(int j=10; j<=11; j++) {
				BreakableBlock block = new BreakableBlock();
				block.setPosition(new Point2D(j, i));
				instantiate(block);
			}
		}
		
		for (int i = 0; i < getCellWidth(); i++) {
			for (int j = 0; j < getCellHeight(); j++) {
				Ground ground = new Ground(Ground.Style.GROUND);
				ground.setPosition(new Point2D(i, j));
				instantiate(ground);
			}
		}

		for(int i=1; i<=15; i++) {
			Slime s = new Slime();
			s.setPosition(new Point2D(1,1));
			instantiate(s);
		}
		
		instantiate(new Pointer());

	}

	private void loop() {
		gameLoop = new AnimationTimer() {
			public void handle(long nowNanoTime) {
				// update deltatime
				setDeltaTime((nowNanoTime - lastNanoTime) / 1e9);
				// gameobjects management
				clearScreen();
				proceedOverGameObjects();

				// gamecanvas management
				if (pursueObject != null)
					pursue();
				if (SystemCache.getInstance().gameEvent.getSingleKeyUp(KeyCode.F8))
					toggleDebug();
				SystemCache.getInstance().gameEvent.clearSingleKeyBuffer();
				// ----------------------
				lastNanoTime = nowNanoTime;
			}
		};
		gameLoop.start();
	}

	public void proceedOverGameObjects() {
		// translate graphics context for drawing object depends on camera position
		Point2D translatePos = pixeledPoint2D(getViewPosition()).subtract(getPixelScreenSize().multiply(0.5));
		gc.translate(-translatePos.getX(), -translatePos.getY());

		// remove destroyed gameobject from set
		Iterator<GameObject> iterator = getGameObjects().iterator();
		while (iterator.hasNext()) {
			GameObject gameObject = iterator.next();
			if (gameObject.isDestroyed()) {
				iterator.remove();
			}
		}

		// instantiate objects in queue (for avoiding concurrent modification)
		while (!this.instantiationQueue.isEmpty()) {
			GameObject gameObject = this.instantiationQueue.poll();
			getGameObjects().add(gameObject);
			if (!gameObject.isStatic())
				gameObject.start();
		}
		
		// sort array list of gameobjects by z-order, y-position
		getGameObjects().sort((GameObject a, GameObject b) -> {
			if (a.getZOrder() == b.getZOrder()) {
				if(a.getPosition().getY() == b.getPosition().getY()) {
					return a.hashCode() - b.hashCode();
				}
				return a.getPosition().getY() < b.getPosition().getY() ? -1 : 1;
			}
			return a.getZOrder() - b.getZOrder();
		});

		// iterate over gameobject and render
		for (GameObject gameObject : getGameObjects()) {
			// reduce process of updating some gameobjects
			if (!gameObject.isStatic())
				gameObject.update();
			gameObject.renderOver(this);
		}
		// (if) debug (draw hitbox/pivot)
		if (this.debug) {
			for (GameObject gameObject : getGameObjects()) {
				gameObject.renderDebug(this);
			}
		}

		// translate graphics context back
		gc.translate(translatePos.getX(), translatePos.getY());
	}

	// general functions
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
		return point.multiply(1.0 / PIXEL_CELLSIZE);
	}

	public static Point2D getPixelScreenSize() {
		return new Point2D(MainApp.WINDOW_WIDTH, MainApp.WINDOW_HEIGHT);
	}

	public Point2D mouseToScaledPoint2D(Point2D mousePoint) {
		return scaledPoint2D(mousePoint).subtract(scaledPoint2D(getPixelScreenSize()).multiply(0.5))
				.add(getViewPosition());
	}

	public void toggleDebug() {
		this.debug = !this.debug;
	}

	public void instantiate(GameObject gameObject) {
		this.instantiationQueue.add(gameObject);
	}

	// getter and setter
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

	private void setDeltaTime(double deltaTime) {
		SystemCache.getInstance().deltaTime = deltaTime;
	}

	public List<GameObject> getGameObjects() {
		return this.gameObjects;
	}

	public void setCellDimension(int width, int height) {
		this.cellsWidth = Math.max(width, (int) Math.ceil(1.0 * MainApp.WINDOW_WIDTH / PIXEL_CELLSIZE));
		this.cellsHeight = Math.max(height, (int) Math.ceil(1.0 * MainApp.WINDOW_HEIGHT / PIXEL_CELLSIZE));
	}

	public int getCellWidth() {
		return this.cellsWidth;
	}

	public int getCellHeight() {
		return this.cellsHeight;
	}
	
	public void pause() {
		gameLoop.stop();
		setCursor(Cursor.DEFAULT);
	}
	
	public void resume() {
		gameLoop.start();
		lastNanoTime = System.nanoTime();
		setCursor(Cursor.NONE);
	}
	
	public boolean[][] getObstacleMap() {
		boolean[][] map = new boolean[getCellHeight()][getCellWidth()];
		Iterator<GameObject> iterator = getGameObjects().iterator();
		while(iterator.hasNext()) {
			GameObject object = iterator.next();
			if(object instanceof Block) {
				Block block = (Block)object;
				map[(int)block.getPosition().getY()][(int)block.getPosition().getX()] = true;
			}
		}
		return map;
	}
}
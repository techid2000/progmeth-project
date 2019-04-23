package gui;

import java.util.Set;
import java.util.TreeSet;

import animation.AnimationClip;
import app.MainApp;
import constants.ImageHolder;
import javafx.animation.AnimationTimer;
import javafx.animation.Transition;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.util.Duration;
import object.GameObject;
import object.entity.Player;

public class GameCanvas extends Canvas {
	public static final int PIXEL_CELLSIZE = 80;
	private double deltaTime;

	private Set<GameObject> gameObjects;
	private GraphicsContext gc;
	private AnimationTimer gameLoop;

	public GameCanvas() {
		this.gc = getGraphicsContext2D();
		this.gameObjects = new TreeSet<GameObject>((GameObject a, GameObject b) -> b.getZOrder() - a.getZOrder());
		this.setWidth(MainApp.WINDOW_WIDTH);
		this.setHeight(MainApp.WINDOW_HEIGHT);

		Player slime = new Player() {
			public void start() {}
		};
		slime.setScale(new Point2D(2,2));
		slime.setMoveSpeed(2);
		slime.setAnimationClip(new AnimationClip(ImageHolder.getInstance().slime,500));
		
		gameObjects.add(slime);
		
		gameLoop = new AnimationTimer() {
			private double last = System.nanoTime();
			public void handle(long now) {
				GameCanvas.this.deltaTime = (now - last) / 1e9;
				proceedOverGameObjects();
				last = now;
			}
		};
		gameLoop.start();
	}

	public void proceedOverGameObjects() {
		clearScreen();
		for(GameObject gameObject : this.gameObjects) {
			//reduce process of updating some gameobject
			if(gameObject.isStatic)
				continue;
			
			gameObject.update(deltaTime);
			gameObject.renderOver(this);
		}
	}
	
	public static Point2D pixeledPoint2D(Point2D point) {
		return point.multiply(PIXEL_CELLSIZE);
	}
	
	public void clearScreen() {
		this.gc.clearRect(0, 0, MainApp.WINDOW_WIDTH, MainApp.WINDOW_HEIGHT);
	}
}

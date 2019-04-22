package gui;

import java.util.Set;
import java.util.TreeSet;

import app.MainApp;
import constants.ResourceHolder;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import object.GameObject;
import scene.GameScene;

public class GameCanvas extends Canvas {
	
	public static final int PIXEL_CELLSIZE = 80;
	public Set<GameObject> gameObjects;
	private GraphicsContext gc;
	
	public GameCanvas() {
		this.gc = getGraphicsContext2D();
		this.gameObjects = new TreeSet<GameObject>((GameObject a, GameObject b) -> b.getZOrder() - a.getZOrder());
		
		this.setWidth(MainApp.WINDOW_WIDTH);
		this.setHeight(MainApp.WINDOW_HEIGHT);
		this.getGraphicsContext2D().setFont(new Font("Times New Roman",300));
		this.getGraphicsContext2D().setFill(Color.CADETBLUE);
		this.getGraphicsContext2D().fillRect(0, 0, MainApp.WINDOW_WIDTH, MainApp.WINDOW_HEIGHT);
		this.getGraphicsContext2D().setFill(Color.BURLYWOOD);
		this.getGraphicsContext2D().fillText("Hello World",100, 300);
		this.getGraphicsContext2D().fillRect(300,400, 80, 80);
		
		GameObject go = new GameObject(ResourceHolder.getInstance().block) {
			@Override
			public void render(GameCanvas gameGUI) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void update() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void start() {
				// TODO Auto-generated method stub
				
			}
		};
		go.setPivot(new Point2D(0.5,0.5));
		go.setPosition(new Point2D(1.5, 1.5));
		go.setScale(new Point2D(1.4,0.1));
		go.setRotation(new Rotate(30));

		GameObject go2 = new GameObject(ResourceHolder.getInstance().block) {
			@Override
			public void render(GameCanvas gameGUI) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void update() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void start() {
				// TODO Auto-generated method stub
				
			}
		};
		go2.setPivot(new Point2D(0.5,0.5));
		go2.setPosition(new Point2D(1, 0.5));
		go2.setScale(new Point2D(1,1));
		go2.setRotation(new Rotate(45));
		go2.setZOrder(1);
		gameObjects.add(go);
		gameObjects.add(go2);
		renderGameObjects();
		System.out.println(go.intersects(go2, gc));
	}
	public void renderGameObjects() {
		for(GameObject obj : gameObjects) {
			double scaledWidth = obj.getRenderSprite().getWidth() * obj.getScale().getX();
			double scaledHeight = obj.getRenderSprite().getHeight() * obj.getScale().getY();
			
			Point2D pixeledPivot = pixeledPoint2D(obj.getPivot());
			Point2D pixeledPosition = pixeledPoint2D(obj.getPosition());
			
			gc.translate(pixeledPosition.getX(), pixeledPosition.getY());
			gc.rotate(obj.getRotation().getAngle());
			gc.drawImage(obj.getRenderSprite(), -pixeledPivot.getX(), -pixeledPivot.getY(), scaledWidth, scaledHeight);
			gc.rotate(-obj.getRotation().getAngle());
			gc.translate(-pixeledPosition.getX(), -pixeledPosition.getY());
		}
	}
	public static Point2D pixeledPoint2D(Point2D point) {
		return point.multiply(PIXEL_CELLSIZE);
	}
}

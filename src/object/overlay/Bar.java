package object.overlay;

import gui.GameCanvas;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import object.GameObject;

public class Bar extends Overlay {
	
	public GameObject gameObject;
	
	public static final double width = 0.7;
	public static final double height = 0.15;
	public static final double offsetY = 1.2;
	
	public int maxValue = 10;
	public int value = 5;
	
	@Override
	public void start() { }
	@Override
	public void update(double deltaTime) {
		this.setPosition(gameObject.getPosition().subtract(new Point2D(0, offsetY)));
	}
	@Override
	public void renderOver(GameCanvas canvas) {
		GraphicsContext gc = canvas.getGraphicsContext2D();

		Point2D pixeledTopLeft = canvas.pixeledPoint2D(getPosition().subtract(new Point2D(width/2, height)));
		Point2D pixeledWidthHeight = canvas.pixeledPoint2D(new Point2D(width, height));
		
		gc.setFill(Color.RED);
		canvas.getGraphicsContext2D().fillRect(pixeledTopLeft.getX(), pixeledTopLeft.getY(),
				pixeledWidthHeight.getX(), pixeledWidthHeight.getY());
	
		pixeledWidthHeight = canvas.pixeledPoint2D(new Point2D((1.0*value/maxValue)*width, height));
		
		gc.setFill(Color.LIME);
		canvas.getGraphicsContext2D().fillRect(pixeledTopLeft.getX(), pixeledTopLeft.getY(),
				pixeledWidthHeight.getX(), pixeledWidthHeight.getY());
	}
}

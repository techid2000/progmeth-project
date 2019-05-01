package object.overlay;

import gui.GameCanvas;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import object.GameObject;

public class Bar extends Overlay {

	
	public static final double width = 0.7;
	public static final double height = 0.15;
	public static final double offsetY = 1.1;

	public GameObject gameObject;
	public Type type;
	public SimpleIntegerProperty maxValue;
	public SimpleIntegerProperty value;
	
	public Bar(GameObject gameObject, Type type, SimpleIntegerProperty maxValue, SimpleIntegerProperty value) {
		this.gameObject = gameObject;
		this.maxValue = maxValue;
		this.value = value;
		this.type = type;
	}
	
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
		
		gc.setFill(Color.BLACK);
		canvas.getGraphicsContext2D().fillRect(pixeledTopLeft.getX(), pixeledTopLeft.getY(),
				pixeledWidthHeight.getX(), pixeledWidthHeight.getY());
	
		pixeledWidthHeight = canvas.pixeledPoint2D(new Point2D((1.0*value.get()/maxValue.get())*width, height));
		
		if(type == Type.PLAYER_HEALTH) {
			if(getPercentage() >= 50)
				gc.setFill(type.getBarColor());
			else if(getPercentage() >= 20) 
				gc.setFill(Color.YELLOW);
			else
				gc.setFill(Color.RED);
		}
		
		canvas.getGraphicsContext2D().fillRect(pixeledTopLeft.getX(), pixeledTopLeft.getY(),
				pixeledWidthHeight.getX(), pixeledWidthHeight.getY());
	}
	public double getPercentage() {
		return 1.0*100*this.value.get()/this.maxValue.get();
	}
	
	//enum
	public enum Type {
		PLAYER_HEALTH,
		ENERMY_HEALTH;
		public Color getBarColor() {
			switch(this) {
			case PLAYER_HEALTH:
				return Color.LIME;
			case ENERMY_HEALTH:
				return Color.RED;
			}
			return Color.BLACK;
		}
	}
}

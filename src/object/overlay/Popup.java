package object.overlay;

import constants.FontHolder;
import constants.ImageHolder;
import gui.GameCanvas;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import object.GameObject;

public class Popup extends Overlay {
	
	public enum Type {
		HEALTH(Color.LIME, Color.RED, "%s%d"),
		CASH(Color.GOLD, Color.SILVER, "%s$%d");
		
		private Color gainColor, costColor;
		private String formatter;
		
		private Type(Color gainColor, Color costColor, String formatter) {
			this.gainColor = gainColor;
			this.costColor = costColor;
			this.formatter = formatter;
		}
		
		public Color getGainColor() { return this.gainColor; }
		public Color getCostColor() { return this.costColor; }
		public String getFormatter() { return this.formatter; }
		
	}
	
	public static final double width = 0.7;
	public static final double height = 0.15;
	public static final double offsetY = 1.2;
	
	public GameObject gameObject;
	public int value;
	public Type type;
	
	public Popup(GameObject gameObject, Type type, int value) {
		this.type = type;
		this.value = value;
		this.gameObject = gameObject;
	}
	
	@Override
	public void start() {
		Point2D startPosition = gameObject.getPosition().subtract(new Point2D(0, offsetY));
		Point2D endPosition = startPosition.subtract(new Point2D(0, 0.5));
		this.setPosition(startPosition);
		Transition floatUp = new Transition() {
			{
				setCycleCount(1);
				setCycleDuration(new Duration(800));
				setInterpolator(Interpolator.EASE_OUT);
			}
			@Override
			protected void interpolate(double frac) {
				setPosition(startPosition.multiply(1-frac).add(endPosition.multiply(frac)));
			}
		};
		floatUp.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) { destroy(); }
		});
		floatUp.play();
	}
	@Override
	public void update(double deltaTime) { }
	@Override
	public void renderOver(GameCanvas canvas) {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		Point2D pixeledPosition = canvas.pixeledPoint2D(getPosition());
		gc.setFont(FontHolder.getInstance().font28);
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setFill(value > 0 ? type.getGainColor() : type.getCostColor());
		gc.fillText(String.format(type.getFormatter(),value > 0 ? "+" : "-", (int)Math.abs(value)), pixeledPosition.getX(), pixeledPosition.getY());
	}
}

package object.overlay;

import constants.ImageHolder;
import constants.SystemCache;
import event.GameEvent;
import gui.GameCanvas;
import javafx.geometry.Point2D;
import object.GameObject;

public class Pointer extends Overlay {
	
	public Pointer() {
		super();
		setRenderSprite(ImageHolder.getInstance().pointer);
		setPivot(new Point2D(0.5,0.5));
		setZOrder(getZOrder() + 1);
	}
	
	@Override
	public void update() {
		GameEvent gameEvent = SystemCache.getInstance().gameEvent;
		GameCanvas gameCanvas= SystemCache.getInstance().gameCanvas;
		setPosition(gameCanvas.mouseToScaledPoint2D(gameEvent.getMousePosition()));
	}

	@Override
	public void start() { }
	
}

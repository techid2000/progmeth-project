package object.overlay;

import constants.ImageHolder;
import constants.SystemCache;
import event.GameEvent;
import gui.GameCanvas;
import javafx.geometry.Point2D;
import object.GameObject;

public class Pointer extends GameObject {
	
	public Pointer() {
		setRenderSprite(ImageHolder.getInstance().pointer);
		setZOrder(10);
		setPivot(new Point2D(0.5,0.5));
	}
	
	@Override
	public void update(double deltaTime) {
		// TODO Auto-generated method stub
		GameEvent gameEvent = SystemCache.getInstance().gameEvent;
		GameCanvas gameCanvas= SystemCache.getInstance().gameCanvas;
		setPosition(gameCanvas.mouseToScaledPoint2D(gameEvent.getMousePosition()));
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}
	
}

package object.entity;

import constants.SystemCache;
import event.GameEvent;
import gui.GameCanvas;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.transform.Rotate;
import logic.GoodsHolder;
import object.GameObject;
import object.overlay.Pointer;
import utility.Utility;

public class Player extends Entity {
	public GoodsHolder weaponHolder;
	
	public Player() {
		
	}
	
	public void moveControl(double deltaTime) {
		GameEvent gameEvent = SystemCache.getInstance().gameEvent;
		GameCanvas gameCanvas = SystemCache.getInstance().gameCanvas;
		
		double horizontal = gameEvent.getHorizontalFraction();
		double vertical = gameEvent.getVerticalFraction();
		Point2D fraction = new Point2D(horizontal, vertical);
		Point2D nextPosition = getPosition().add(fraction.multiply(getMoveSpeed() * deltaTime));
		
		Point2D nextPositionX = new Point2D(nextPosition.getX(), getPosition().getY());
		Point2D nextPositionY = new Point2D(getPosition().getX(), nextPosition.getY());
		
		Point2D tmp = getPosition();
		
		setPosition(nextPosition);
		boolean test = false;
		for(GameObject g : SystemCache.getInstance().gameCanvas.gameObjects) {
			if(g == this) continue;
			if(g instanceof Pointer) continue;
			if(intersects(g)) {
				setPosition(tmp);
				test = true;
				break;
			}
		}
		
		if(test) {
			setPosition(nextPositionX);
			
			for(GameObject g : SystemCache.getInstance().gameCanvas.gameObjects) {
				if(g == this) continue;
				if(g instanceof Pointer) continue;
				if(intersects(g)) {
					setPosition(tmp);
					test = false;
					break;
				}
			}
			
			if(!test) {
				setPosition(nextPositionY);
				
				for(GameObject g : SystemCache.getInstance().gameCanvas.gameObjects) {
					if(g == this) continue;
					if(g instanceof Pointer) continue;
					if(intersects(g)) {
						setPosition(tmp);
						break;
					}
				}
			}
		}
		
		if(gameEvent.getSingleKeyDown(KeyCode.E)) {
			System.out.println("test");
		}
		
		if(fraction.magnitude() == 0) {
			if(getAnimationClip().getFrameIndex() == 0)
			getAnimationClip().pause();
		} else {
			getAnimationClip().play();
		}
		
		Point2D dir = gameCanvas.mouseToScaledPoint2D(gameEvent.getMousePosition()).subtract(getPosition());
		Rotate rot = Utility.pointToRotate(dir);
		rot.getAngle();
	}
	
	public void start() {}
	@Override
	public void update(double deltaTime) {
		moveControl(deltaTime);
	}

	@Override
	public void getDamage(double damage) {}
	@Override
	public void doHeal(double heal) {}
}

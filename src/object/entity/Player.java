package object.entity;

import animation.AnimationClip;
import constants.ImageHolder;
import constants.SystemCache;
import event.GameEvent;
import gui.GameCanvas;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.transform.Rotate;
import logic.GameObjectTag;
import logic.GoodsHolder;
import object.GameObject;
import object.overlay.Pointer;
import utility.Utility;

public class Player extends Entity {
	public GoodsHolder weaponHolder;
	
	public Player() { 
		//config later
		setMoveSpeed(2);
		setAnimationClip(new AnimationClip(ImageHolder.getInstance().slime, 500));
		setPivot(new Point2D(0.5, 0.5));
		setPosition(new Point2D(0,0));
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
		
		setPositionByColliderCheck(nextPosition);
		
		//wait for manage
		if(gameEvent.getSingleKeyDown(KeyCode.E)) {
			System.out.println("test");
		}
		
		//wait for manage
		if(fraction.magnitude() == 0) {
			if(getAnimationClip().getFrameIndex() == 0)
			getAnimationClip().pause();
		} else {
			getAnimationClip().play();
		}
		
		//wait for manage
		Point2D dir = gameCanvas.mouseToScaledPoint2D(gameEvent.getMousePosition()).subtract(getPosition());
		Rotate rot = Utility.pointToRotate(dir);
		rot.getAngle();
	}
	
	private void setPositionByColliderCheck(Point2D nextPosition) {
		Point2D origin = getPosition();
		Point2D nextPosX = new Point2D(nextPosition.getX(), origin.getY());
		Point2D nextPosY = new Point2D(origin.getX(), nextPosition.getY());
		setPosition(nextPosition);
		if(getIntersectedObjects(GameObjectTag.BLOCK).size() == 0) return;
		setPosition(nextPosX);
		if(getIntersectedObjects(GameObjectTag.BLOCK).size() == 0) return;
		setPosition(nextPosY);
		if(getIntersectedObjects(GameObjectTag.BLOCK).size() == 0) return;
		setPosition(origin);
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

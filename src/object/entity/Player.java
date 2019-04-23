package object.entity;

import constants.SystemCache;
import event.GameEvent;
import javafx.geometry.Point2D;
import logic.WeaponHolder;

public class Player extends Entity {
	public WeaponHolder weaponHolder;
	
	public Player() {
		
	}
	
	public void moveControl(double deltaTime) {
		GameEvent gameEvent = SystemCache.getInstance().gameScene.getGameEvent();
		Point2D fraction = new Point2D(gameEvent.getHorizontalFraction(), gameEvent.getVerticalFraction());
		Point2D nextPosition = getPosition().add(fraction.multiply(getMoveSpeed() * deltaTime));
		setPosition(nextPosition);
		
		if(fraction.magnitude() == 0) {
			if(getAnimationClip().getFrameIndex() == 0)
			getAnimationClip().pause();
		} else {
			getAnimationClip().play();
		}
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

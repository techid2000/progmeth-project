package object.entity;

import java.util.List;

import animation.AnimationClip;
import constants.ImageHolder;
import constants.SystemCache;
import javafx.geometry.Point2D;
import logic.BoxCollider;
import logic.GameObjectTag;
import object.GameObject;
import object.loot.Mint;
import object.loot.Mint.Type;

public class Slime extends Enermy {
	// resources
	protected AnimationClip clipDeath;
	protected AnimationClip clipBounce;

	@Override
	public void start() {
		getHealth().set(50);
		getMaxHealth().set(50);

		setMoveSpeed(0.5+Math.random()*1);
		setPivot(new Point2D(0.5, 1));
		setZOrder(1);
		getCollisionSystem().setDefaultAvailable(false);
		getCollisionSystem().addBoxCollider(-0.35, -0.75, 0.7, 0.75);

		clipBounce = new AnimationClip(ImageHolder.getInstance().slime, 500);
		setAnimationClip(clipBounce);
		getAnimationClip().play();
	}

	double movementInterval = 0;
	double doDamageInterval = 0;
	double angle = 0;
	@Override
	public void update() {
		if (getHealth().get() <= 0) {
			this.destroy();
			//new coin
			double chance = Math.random();
			Mint loot;
			if(chance > 0.1) {				
				loot = new Mint(Type.SINGLE_COIN);
				loot.setPivot(new Point2D(0.25, 0.25));
			} else {
				loot = new Mint(Type.COIN_PILE_0);
				loot.setScale(new Point2D(0.8, 0.8));
				loot.setPivot(new Point2D(0.5, 0.5));
			}
			loot.setPosition(getPosition());
			SystemCache.getInstance().gameCanvas.instantiate(loot);
		}
		movementInterval += SystemCache.getInstance().deltaTime;
		if (movementInterval >= 1) {
			movementInterval = 0;
			angle = Math.random() * 360;
		}
		Player p = SystemCache.getInstance().player;
		Point2D direction = p.getPosition().subtract(getPosition()).normalize().multiply(getMoveSpeed() * SystemCache.getInstance().deltaTime);
		setPosition(getPosition().add(direction));
		BoxCollider mine = getCollisionSystem().getCollider(0);
		List<BoxCollider> list;
		doDamageInterval += SystemCache.getInstance().deltaTime;
		if(doDamageInterval >= 0.75) {
			doDamageInterval = 0;
			if((list=mine.getIntersectedCollider(GameObjectTag.PLAYER)).size() > 0) {
				p.getDamage(10);
			}
		}
		
	}

}

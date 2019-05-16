package object.entity;

import java.util.Iterator;
import java.util.List;

import animation.AnimationClip;
import constants.ImageHolder;
import constants.SystemCache;
import gui.GameCanvas;
import javafx.geometry.Point2D;
import javafx.util.Pair;
import logic.BoxCollider;
import logic.GameObjectTag;
import object.GameObject;
import object.loot.Mint;
import object.loot.Mint.Type;
import object.weapon.gun.AssultRifle;
import object.weapon.projectile.Beam;
import utility.Utility;

public class SlimeGunner extends Enermy {
	// resources
	protected AnimationClip clipDeath;
	protected AnimationClip clipBounce;
	
	@Override
	public void start() {
		getHealth().set(50);
		getMaxHealth().set(50);

		setMoveSpeed(0.5+Math.random()*0.5);
		setPivot(new Point2D(0.5, 1));
		setZOrder(0);
		getCollisionSystem().setDefaultAvailable(false);
		getCollisionSystem().addBoxCollider(-0.35, -0.75, 0.7, 0.75);

		clipBounce = new AnimationClip(ImageHolder.getInstance().slimeGunner, 500);
		setAnimationClip(clipBounce);
		getAnimationClip().play();
	}

	double movementInterval = 0;
	double doDamageInterval = 0;
	double angle = 0;
	
	double pathfindingTimer = 0;
	double shootTimer = 0;
	List<Pair<Integer,Integer>> list;
	Iterator<Pair<Integer,Integer>> iter;
	Point2D now;
	
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
				loot.setScale(new Point2D(0.8, 0.8));
			} else {
				loot = new Mint(Type.COIN_PILE_0);
				loot.setScale(new Point2D(0.8, 0.8));
				loot.setPivot(new Point2D(0.5, 0.5));
			}
			loot.setPosition(getPosition());
			SystemCache.getInstance().gameCanvas.instantiate(loot);
		}
		
		Point2D scaledPosition = getPosition();
		Pair<Integer,Integer> source = new Pair<Integer,Integer>((int)scaledPosition.getX(), (int)scaledPosition.getY());
		scaledPosition = SystemCache.getInstance().player.getPosition();
		Pair<Integer,Integer> target = new Pair<Integer,Integer>((int)scaledPosition.getX(), (int)scaledPosition.getY());
		if(pathfindingTimer <= 0) {
			pathfindingTimer = 1 + Math.random()*1;
			list = Utility.Pathfinding(SystemCache.getInstance().gameCanvas.getObstacleMap()
					, source, target);
			iter = list.iterator();
			if(iter.hasNext()) {
				Pair<Integer,Integer> tmp = iter.next();
				now = new Point2D(tmp.getKey(), tmp.getValue());
			}
		}
		if(now != null) {
			if(Math.floor(getPosition().getX()) == now.getX() && Math.floor(getPosition().getY()) == now.getY()) {
				if(iter.hasNext()) {
					Pair<Integer,Integer> tmp = iter.next();
					now = new Point2D(tmp.getKey(), tmp.getValue());
				}
			}
			setPosition(Utility.moveTowardsPoint2D(getPosition(), now.add(new Point2D(0.5,0.5)), getMoveSpeed()*SystemCache.getInstance().deltaTime));
		}
		pathfindingTimer -= SystemCache.getInstance().deltaTime;
		
		if(shootTimer <= 0) {
			shootTimer = 1.5+Math.random()*0.5;
			Beam beam = new Beam();
			Point2D eyePosition = getPosition().subtract(new Point2D(0,0.5));
			beam.setPosition(eyePosition);
			Point2D direction = SystemCache.getInstance().player.getPosition().subtract(eyePosition).normalize();
			beam.setRotation(Utility.pointToRotate(direction));
			SystemCache.getInstance().gameCanvas.instantiate(beam);
			System.out.println("test");
		}
		shootTimer -= SystemCache.getInstance().deltaTime;
//		movementInterval += SystemCache.getInstance().deltaTime;
//		if (movementInterval >= 1) {
//			movementInterval = 0;
//			angle = Math.random() * 360;
//		}
		Player p = SystemCache.getInstance().player;
//		Point2D direction = p.getPosition().subtract(getPosition()).normalize().multiply(getMoveSpeed() * SystemCache.getInstance().deltaTime);
//		setPosition(getPosition().add(direction));
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

	public void destroy() {
		super.destroy();
		SystemCache.getInstance().gameCanvas.waveSystem.monsterKilled();
	}
}

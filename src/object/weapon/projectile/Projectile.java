package object.weapon.projectile;

import java.util.List;

import constants.ImageHolder;
import constants.SystemCache;
import javafx.geometry.Point2D;
import javafx.scene.transform.Rotate;
import logic.BoxCollider;
import logic.GameObjectTag;
import object.GameObject;
import object.block.BreakableBlock;
import object.entity.Enermy;
import utility.Utility;

public class Projectile extends GameObject {
	private double speed;
	private double lifeTime;
	private int damage;
	
	public Projectile() {
		setRenderSprite(ImageHolder.getInstance().projectile);
	}
	
	@Override
	public void start() { 
		setScale(new Point2D(1,1));
		setPivot(new Point2D(0, 0.1));		
	}

	@Override
	public void update() {
		double deltaTime = SystemCache.getInstance().deltaTime;
		lifeTime -= deltaTime;
		if(lifeTime <= 0) {
			destroy();
		}
		setPosition(getPosition().add(Utility.rotateToDirection(getRotation()).multiply(deltaTime * getSpeed())));
		
		int maxx = SystemCache.getInstance().gameCanvas.getCellWidth();
		int maxy = SystemCache.getInstance().gameCanvas.getCellHeight();
		
		if(getPosition().getX() < 0 || getPosition().getX() > maxx || getPosition().getY() < 0 || getPosition().getY() > maxy) {
			destroy();
		}
		List<BoxCollider> list;
		if((list = BoxCollider.getDefaultBox(this).getIntersectedCollider(GameObjectTag.BLOCK | GameObjectTag.ENERMY)).size() > 0) {
			for(BoxCollider b : list) {
				if(b.gameObject instanceof BreakableBlock) {
					((BreakableBlock)b.gameObject).getDamage(this.getDamage());
				} else if(b.gameObject instanceof Enermy) {
					((Enermy)b.gameObject).getDamage(this.getDamage());
				}
			}
			destroy();
		}
	}
	
	
	public double getSpeed() {
		return speed;
	}
	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}
	public double getLifeTime() {
		return lifeTime;
	}
	public void setLifeTime(double lifeTime) {
		this.lifeTime = lifeTime;
	}
}

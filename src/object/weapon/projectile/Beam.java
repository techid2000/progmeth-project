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
import object.entity.Enemy;
import object.entity.Player;
import utility.Utility;

public class Beam extends GameObject {
	private double speed;
	private int damage;
	
	public Beam() {
		setRenderSprite(ImageHolder.getInstance().beam);
		setZOrder(2);
		setSpeed(5);
		setDamage(20);
	}
	
	@Override
	public void start() { 
		setScale(new Point2D(1,1));
		setPivot(new Point2D(0, 0.1));		
	}

	@Override
	public void update() {
		double deltaTime = SystemCache.getInstance().deltaTime;
		setPosition(getPosition().add(Utility.rotateToDirection(getRotation()).multiply(deltaTime * getSpeed())));
		
		int maxx = SystemCache.getInstance().gameCanvas.getCellWidth();
		int maxy = SystemCache.getInstance().gameCanvas.getCellHeight();
		
		if(getPosition().getX() < 0 || getPosition().getX() > maxx || getPosition().getY() < 0 || getPosition().getY() > maxy) {
			destroy();
		}
		List<BoxCollider> list;
		if((list = BoxCollider.getDefaultBox(this).getIntersectedCollider(GameObjectTag.BLOCK | GameObjectTag.PLAYER)).size() > 0) {
			for(BoxCollider b : list) {
				if(b.gameObject instanceof BreakableBlock) {
					((BreakableBlock)b.gameObject).getDamage(this.getDamage());
				} else if(b.gameObject instanceof Player) {
					((Player)b.gameObject).getDamage(this.getDamage());
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
}

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
import utility.Utility;

public class Projectile extends GameObject {
	public Projectile() {
		setRenderSprite(ImageHolder.getInstance().projectile);
		setScale(new Point2D(1,1));
		setPivot(new Point2D(0, 0.1));
	}
	
	@Override
	public void start() {
	}

	@Override
	public void update(double deltaTime) {
		setPosition(getPosition().add(Utility.rotateToDirection(getRotation()).multiply(deltaTime * 10)));
		
		int maxx = SystemCache.getInstance().gameCanvas.getCellWidth();
		int maxy = SystemCache.getInstance().gameCanvas.getCellHeight();
		
		if(getPosition().getX() < 0 || getPosition().getX() > maxx || getPosition().getY() < 0 || getPosition().getY() > maxy) {
			destroy();
		}
		List<BoxCollider> list;
		if((list = BoxCollider.getDefaultBox(this).getIntersectedCollider(GameObjectTag.BLOCK)).size() > 0) {
			for(BoxCollider b : list) {
				if(b.gameObject instanceof BreakableBlock) {
					((BreakableBlock)b.gameObject).getDamage(5);
				}
			}
			destroy();
		}
	}
	
}

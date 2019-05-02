package object.weapon.gun;

import constants.ImageHolder;
import constants.SystemCache;
import gui.GameCanvas;
import javafx.geometry.Point2D;
import object.weapon.projectile.Projectile;
import utility.Utility;

public class Shotgun extends Gun {

	public Shotgun() {
		setRenderSprite(ImageHolder.getInstance().shotgun);
		setMaxRound(6);
		setMaxAmmo(20);
		setRound(getMaxRound());
		setAmmo(getMaxAmmo());
	}
	
	@Override
	public void fire(Point2D aimOrigin, Point2D aimDirection) {
		// TODO Auto-generated method stub
		if(getRound() == 0) return;
		setRound(getRound() - 1);
		GameCanvas gameCanvas = SystemCache.getInstance().gameCanvas;
		for(int i = -10; i <= 10; i+= 5) {
			Projectile pjt = new Projectile();
			pjt.setPosition(aimOrigin);
			pjt.setRotation(Utility.pointToRotate(Utility.rotatePoint2D(aimDirection,i)));
			gameCanvas.instantiate(pjt);
		}
	}

	@Override
	public void reload() {
		// TODO Auto-generated method stub
		int tmp = getRound();
		setRound(Math.min(getMaxRound(), getRound() + getAmmo()));
		setAmmo(getAmmo() - (getRound() - tmp));
	}

}

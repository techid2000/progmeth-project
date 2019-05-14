package object.weapon.gun;

import constants.ImageHolder;
import constants.SystemCache;
import event.GameEvent;
import gui.GameCanvas;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseButton;
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
	public void update(Point2D aimOrigin, Point2D aimDirection) {
		// TODO Auto-generated method stub
		if (getInterval() < 0.7) {
			setInterval(getInterval() + SystemCache.getInstance().deltaTime);
		}
		if (getInterval() >= 0.7) {
			GameEvent gameEvent = SystemCache.getInstance().gameEvent;
			if (gameEvent.getSingleMouseDown(MouseButton.PRIMARY)) {
				setInterval(0);
				if (getRound() == 0)
					return;
				setRound(getRound() - 1);
				GameCanvas gameCanvas = SystemCache.getInstance().gameCanvas;
				for (int i = -10; i <= 10; i += 3) {
					Projectile pjt = new Projectile();
					pjt.setPosition(aimOrigin);
					pjt.setRotation(Utility.pointToRotate(Utility.rotatePoint2D(aimDirection, i)));
					pjt.setLifeTime(0.3);
					pjt.setSpeed(8);
					pjt.setDamage(4);
					gameCanvas.instantiate(pjt);
				}
			}
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

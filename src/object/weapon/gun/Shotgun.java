package object.weapon.gun;

import constants.ImageHolder;
import constants.SoundHolder;
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
		setPrice(400);
		setAmmoPrice(20);
	}

	@Override
	public void gunFireListener(Point2D aimOrigin, Point2D aimDirection) {
		// TODO Auto-generated method stub
		if (getInterval() < 0.5) {
			setInterval(getInterval() + SystemCache.getInstance().deltaTime);
		}
		if (getInterval() >= 0.5) {
			GameEvent gameEvent = SystemCache.getInstance().gameEvent;
			if (gameEvent.getSingleMouseDown(MouseButton.PRIMARY)) {
				setInterval(0);
				if (getRound() == 0) {
					SoundHolder.getInstance().empty.play();
					return;
				}
				setRound(getRound() - 1);
				GameCanvas gameCanvas = SystemCache.getInstance().gameCanvas;
				for (int i = -9; i <= 9; i += 3) {
					Projectile pjt = new Projectile();
					pjt.setPosition(aimOrigin);
					pjt.setRotation(Utility.pointToRotate(Utility.rotatePoint2D(aimDirection, i)));
					pjt.setLifeTime(0.5);
					pjt.setSpeed(8);
					pjt.setDamage(4);
					gameCanvas.instantiate(pjt);
				}
				SoundHolder.getInstance().shotgun.play();
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

package object.weapon.gun;

import constants.ImageHolder;
import constants.SystemCache;
import event.GameEvent;
import gui.GameCanvas;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseButton;
import object.weapon.projectile.Projectile;
import utility.Utility;

public class SniperRifle extends Gun {
	public SniperRifle() {
		setRenderSprite(ImageHolder.getInstance().sniperRifle);
		setMaxRound(7);
		setMaxAmmo(50);
		setRound(getMaxRound());
		setAmmo(getMaxAmmo());
	}

	@Override
	public void update(Point2D aimOrigin, Point2D aimDirection) {
		if (getInterval() < 0.7) {
			setInterval(getInterval() + SystemCache.getInstance().deltaTime);
		}
		if(getInterval() >= 0.7) {
			GameEvent gameEvent = SystemCache.getInstance().gameEvent;
			if (gameEvent.getSingleMouseDown(MouseButton.PRIMARY)) {
				setInterval(0);
				if (getRound() == 0)
					return;
				setRound(getRound() - 1);
				GameCanvas gameCanvas = SystemCache.getInstance().gameCanvas;
				Projectile pjt = new Projectile();
				pjt.setPosition(aimOrigin);
				pjt.setRotation(Utility.pointToRotate(aimDirection));
				pjt.setLifeTime(Integer.MAX_VALUE);
				pjt.setSpeed(20);
				pjt.setDamage(15);
				gameCanvas.instantiate(pjt);
			}
		}
	}

	@Override
	public void reload() {
		int tmp = getRound();
		setRound(Math.min(getMaxRound(), getRound() + getAmmo()));
		setAmmo(getAmmo() - (getRound() - tmp));
	}
}
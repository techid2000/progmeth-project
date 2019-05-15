package object.weapon.gun;

import constants.ImageHolder;
import constants.SystemCache;
import event.GameEvent;
import gui.GameCanvas;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseButton;
import object.weapon.projectile.Projectile;
import utility.Utility;

public class AssultRifle extends Gun {
	
	public AssultRifle() {
		setRenderSprite(ImageHolder.getInstance().assultRifle);
		setMaxRound(30);
		setMaxAmmo(200);
		setRound(getMaxRound());
		setAmmo(getMaxAmmo());
		setPrice(800);
		setAmmoPrice(70);
	}
	
	@Override
	public void gunFireListener(Point2D aimOrigin, Point2D aimDirection) {
		setInterval(getInterval()+SystemCache.getInstance().deltaTime);
		if(getInterval() >= 0.1) {
			setInterval(0);
			GameEvent gameEvent = SystemCache.getInstance().gameEvent;
			if(gameEvent.getMouseHolding(MouseButton.PRIMARY)) {
				if(getRound() == 0) return;
				setRound(getRound() - 1);
				GameCanvas gameCanvas = SystemCache.getInstance().gameCanvas;
				Projectile pjt = new Projectile();
				pjt.setPosition(aimOrigin);
				double rand = (Math.random()-0.5) * 10;
				pjt.setRotation(Utility.pointToRotate(Utility.rotatePoint2D(aimDirection,rand)));
				pjt.setLifeTime(Integer.MAX_VALUE);
				pjt.setSpeed(10);
				pjt.setDamage(4);
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

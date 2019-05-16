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

public class Pistol extends Gun {
	public Pistol() {
		setRenderSprite(ImageHolder.getInstance().pistol);
		setMaxRound(10);
		setMaxAmmo(0x3f3f3f3f);
		setRound(getMaxRound());
		setAmmo(getMaxAmmo());
	}

	@Override
	public void gunFireListener(Point2D aimOrigin, Point2D aimDirection) {
		// TODO Auto-generated method stub
		if (getInterval() < 0.3) {
			setInterval(getInterval() + SystemCache.getInstance().deltaTime);
		}
		if(getInterval() >= 0.3) {
			GameEvent gameEvent = SystemCache.getInstance().gameEvent;
			if (gameEvent.getSingleMouseDown(MouseButton.PRIMARY)) {
				setInterval(0);
				if (getRound() == 0) {
					SoundHolder.getInstance().empty.play();
					return;
				}
				setRound(getRound() - 1);
				GameCanvas gameCanvas = SystemCache.getInstance().gameCanvas;
				Projectile pjt = new Projectile();
				pjt.setPosition(aimOrigin);
				double rand = (Math.random()-0.5) * 7;
				pjt.setRotation(Utility.pointToRotate(Utility.rotatePoint2D(aimDirection,rand)));
				pjt.setLifeTime(Integer.MAX_VALUE);
				pjt.setSpeed(10);
				pjt.setDamage(5);
				gameCanvas.instantiate(pjt);
				
				SoundHolder.getInstance().pistol.play();
			}
		}
	}

	@Override
	public void reload() {
		// TODO Auto-generated method stub
		int tmp = getRound();
		setRound(Math.min(getMaxRound(), getRound() + getAmmo()));
		setAmmo(getAmmo() - (getRound() - tmp));
		SoundHolder.getInstance().pistol_reload.play();
	}
}

package object.entity;

import animation.AnimationClip;
import constants.ImageHolder;
import constants.SystemCache;
import javafx.geometry.Point2D;
import object.weapon.projectile.Beam;
import utility.Utility;

public class SlimeGunner extends Slime {
	
	@Override
	public void start() {
		super.start();
		setMoveSpeed(0.5+Math.random()*0.5);

		clipBounce = new AnimationClip(ImageHolder.getInstance().slimeGunner, 500);
		setAnimationClip(clipBounce);
		getAnimationClip().play();
	}

	double shootTimer = 0;
	
	@Override
	public void update() {
	super.update();
		if(shootTimer <= 0) {
			shootTimer = 1.5+Math.random()*0.5;
			Beam beam = new Beam();
			Point2D eyePosition = getPosition().subtract(new Point2D(0,0.5));
			beam.setPosition(eyePosition);
			Point2D direction = SystemCache.getInstance().player.getPosition().subtract(eyePosition).normalize();
			beam.setRotation(Utility.pointToRotate(direction));
			SystemCache.getInstance().gameCanvas.instantiate(beam);
		}
		shootTimer -= SystemCache.getInstance().deltaTime;

	}

	public void destroy() {
		super.destroy();
		SystemCache.getInstance().gameCanvas.getWaveSystem().monsterKilled();
	}
}

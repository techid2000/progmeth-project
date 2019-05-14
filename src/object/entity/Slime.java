package object.entity;

import java.util.ArrayList;
import java.util.List;

import animation.AnimationClip;
import constants.ImageHolder;
import constants.SystemCache;
import javafx.geometry.Point2D;
import logic.Accessories;

public class Slime extends Enermy {
	// resources
	protected AnimationClip clipDeath;
	protected AnimationClip clipBounce;

	@Override
	public void start() {
		getHealth().set(50);
		getMaxHealth().set(50);

		setMoveSpeed(2);
		setPivot(new Point2D(0.5, 1));
		setZOrder(1);
		
//		clips = new ArrayList<AnimationClip>();
//		clips.add(new AnimationClip(ImageHolder.getInstance().slime, 500));
		clipBounce = new AnimationClip(ImageHolder.getInstance().slime, 500);
		setAnimationClip(clipBounce);
		getAnimationClip().play();
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		if(getHealth().get() <= 0) {
			this.destroy();
		}
		double rand = Math.random() *360;
		double distance = SystemCache.getInstance().deltaTime * getMoveSpeed()*5;
		setPosition(getPosition().add(new Point2D(distance * Math.cos(Math.toRadians(rand)), distance * Math.sin(Math.toRadians(rand)))));
	}

}

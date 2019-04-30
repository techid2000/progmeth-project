package object.overlay;

import logic.GameObjectTag;
import object.GameObject;

public abstract class Overlay extends GameObject {
	public Overlay() {
		getTag().addTag(GameObjectTag.OVERLAY);
		getCollisionSystem().setDefaultAvailable(false);
		setZOrder(10);
	}
}

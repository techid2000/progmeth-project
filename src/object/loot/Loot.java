package object.loot;

import interfaces.ICollectable;
import logic.GameObjectTag;
import object.GameObject;

public abstract class Loot extends GameObject implements ICollectable {
	public Loot() {
		getTag().addTag(GameObjectTag.LOOT);
	}
}

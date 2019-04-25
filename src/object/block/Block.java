package object.block;

import logic.GameObjectTag;
import object.GameObject;

public abstract class Block extends GameObject {
	public Block() {
		getTag().addTag(GameObjectTag.BLOCK);
	}
}

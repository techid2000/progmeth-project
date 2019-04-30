package object.tile;

import object.GameObject;

public abstract class Tile extends GameObject {
	public Tile() {
		super();
		setStatic(false);
		setRenderDebug(false);
		setZOrder(-100);
	}
}

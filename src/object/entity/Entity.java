package object.entity;

import interfaces.IAlive;
import object.GameObject;

public abstract class Entity extends GameObject implements IAlive {
	public double moveSpeed;

	public double getMoveSpeed() {
		return moveSpeed;
	}
	public void setMoveSpeed(double moveSpeed) {
		this.moveSpeed = moveSpeed;
	}
}

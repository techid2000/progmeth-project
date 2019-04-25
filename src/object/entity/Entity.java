package object.entity;

import interfaces.IAlive;
import object.GameObject;

public abstract class Entity extends GameObject implements IAlive {
	private double moveSpeed;
	private int health;
	private int maxHealth;
	private int attack;
	private int defense;
	
	public double getMoveSpeed() {
		return moveSpeed;
	}
	public void setMoveSpeed(double moveSpeed) {
		this.moveSpeed = moveSpeed;
	}
}

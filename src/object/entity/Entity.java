package object.entity;

import constants.SystemCache;
import interfaces.IAlive;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import object.GameObject;
import object.overlay.Bar;
import object.overlay.Popup;

public abstract class Entity extends GameObject implements IAlive {
	//properties
	protected double moveSpeed;
	protected SimpleIntegerProperty health;
	protected SimpleIntegerProperty maxHealth;
	protected int attack;
	protected int defense;
	
	//gameobject
	protected Bar healthBar;
	
	//constructor
	public Entity() {
		this.health = new SimpleIntegerProperty();
		this.maxHealth = new SimpleIntegerProperty();
		this.healthBar = new Bar(this, Bar.Type.PLAYER_HEALTH, this.maxHealth, this.health);
		SystemCache.getInstance().gameCanvas.instantiate(healthBar);
	}
	
	//general function
	public void doHeal(int heal) {
		this.health.set(Math.min(100, this.health.get() + heal));
		Popup healPopup = new Popup(this, Popup.Type.HEALTH, heal);
		SystemCache.getInstance().gameCanvas.instantiate(healPopup);
	}
	public void getDamage(int damage) {
		this.health.set(Math.max(0,this.health.get() - damage));
		Popup damagePopup = new Popup(this, Popup.Type.HEALTH, -damage);
		SystemCache.getInstance().gameCanvas.instantiate(damagePopup);
	}
	public void destroy() {
		super.destroy();
		Entity.this.healthBar.destroy();		
	}

	//getter and setter
	public double getMoveSpeed() {
		return moveSpeed;
	}
	public void setMoveSpeed(double moveSpeed) {
		this.moveSpeed = moveSpeed;
	}
	public SimpleIntegerProperty getHealth() {
		return health;
	}
	public SimpleIntegerProperty getMaxHealth() {
		return maxHealth;
	}
	public int getAttack() {
		return attack;
	}
	public void setAttack(int attack) {
		this.attack = attack;
	}
	public int getDefense() {
		return defense;
	}
	public void setDefense(int defense) {
		this.defense = defense;
	}
	public Bar getHealthBar() {
		return healthBar;
	}
	public void setHealthBar(Bar healthBar) {
		this.healthBar = healthBar;
	}
}

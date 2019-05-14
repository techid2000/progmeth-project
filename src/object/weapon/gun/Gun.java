package object.weapon.gun;

import interfaces.IFireable;
import interfaces.IReloadable;
import object.weapon.Weapon;

public abstract class Gun extends Weapon implements IFireable, IReloadable {

	protected double fireRate;
	protected double reloadSpeed;
	protected int round;
	protected int maxRound;
	protected int ammo;
	protected int maxAmmo;
	protected int ammoPrice;
	
	protected double interval;
	
	public Gun() { }
	
	public double getFireRate() {
		return fireRate;
	}
	public void setFireRate(double fireRate) {
		this.fireRate = fireRate;
	}
	public double getReloadSpeed() {
		return reloadSpeed;
	}
	public void setReloadSpeed(double reloadSpeed) {
		this.reloadSpeed = reloadSpeed;
	}
	public int getRound() {
		return round;
	}
	public void setRound(int round) {
		this.round = round;
	}
	public int getMaxRound() {
		return maxRound;
	}
	public void setMaxRound(int maxRound) {
		this.maxRound = maxRound;
	}
	public int getAmmo() {
		return ammo;
	}
	public void setAmmo(int ammo) {
		this.ammo = ammo;
	}
	public int getMaxAmmo() {
		return maxAmmo;
	}
	public void setMaxAmmo(int maxAmmo) {
		this.maxAmmo = maxAmmo;
	}
	public int getAmmoPrice() {
		return ammoPrice;
	}
	public void setAmmoPrice(int ammoPrice) {
		this.ammoPrice = ammoPrice;
	}
	
	public double getInterval() {
		return interval;
	}

	public void setInterval(double interval) {
		this.interval = interval;
	}
	
	//not necessary
	public void start() {}
	public void update() {}
}

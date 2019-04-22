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
	
	public Gun() { }
}

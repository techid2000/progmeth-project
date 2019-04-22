package object.item.weapon.gun;

import interfaces.IFireable;
import interfaces.IReloadable;
import object.item.weapon.Weapon;

public abstract class Gun extends Weapon implements IFireable, IReloadable {
	protected double fireRate;
	protected int ammo;
	protected int maxAmmo;
	protected double reloadSpeed;
}

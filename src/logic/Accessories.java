package logic;

import constants.SystemCache;
import javafx.geometry.Point2D;
import object.weapon.gun.AssultRifle;
import object.weapon.gun.Gun;
import object.weapon.gun.Pistol;
import object.weapon.gun.Shotgun;
import object.weapon.gun.SniperRifle;

public class Accessories {
	public int cash;
	public Gun gunA;
	public Gun gunB;
	public int numberOfFragenade;
	
	private Gun currentGun;
	
	public Accessories() {
		gunA = new Pistol();
		gunB = new AssultRifle();
		currentGun = gunA;
	}
	
	public void gainCash(int gain) {
		this.cash += gain;
		SystemCache.getInstance().gameUI.setCash(cash);
	}
	
	public Gun getCurrentGun() {
		return this.currentGun;
	}
	
	public void swapGun() {
		currentGun = currentGun == gunA ? gunB : gunA;
		SystemCache.getInstance().accessoriesUI.updateInfo(this);
	}
	
	public void updateCurrentGun(Point2D aimOrigin, Point2D aimDirection) {
		this.currentGun.update(aimOrigin, aimDirection);
		SystemCache.getInstance().accessoriesUI.updateInfo(this);
	}
	
	public void reloadCurrentGun() {
		this.currentGun.reload();
		SystemCache.getInstance().accessoriesUI.updateInfo(this);
	}
}

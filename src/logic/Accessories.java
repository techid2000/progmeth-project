package logic;

import constants.SystemCache;
import javafx.geometry.Point2D;
import object.weapon.gun.Gun;
import object.weapon.gun.Pistol;

public class Accessories {
	private int cash;
	public Gun gunA;
	public Gun gunB;
//	private int numberOfFragenade;
	
	private Gun currentGun;
	
	public Accessories() {
		gunA = new Pistol();
		gunB = null;
		currentGun = gunA;
	}
	
	public void gainCash(int gain) {
		this.cash += gain;
		SystemCache.getInstance().gameUI.setCash(cash);
	}
	
	public boolean costCash(int cost) {
		if(this.cash >= cost) {
			gainCash(-cost);
			return true;
		} 
		return false;
	}
	
	public Gun getCurrentGun() {
		return this.currentGun;
	}
	
	public void swapGun() {
		if(gunB == null) return;
		currentGun = currentGun == gunA ? gunB : gunA;
		SystemCache.getInstance().accessoriesUI.updateInfo(this);
	}
	
	public void updateCurrentGun(Point2D aimOrigin, Point2D aimDirection) {
		this.currentGun.gunFireListener(aimOrigin, aimDirection);
		SystemCache.getInstance().accessoriesUI.updateInfo(this);
	}
	
	public void reloadCurrentGun() {
		this.currentGun.reload();
		SystemCache.getInstance().accessoriesUI.updateInfo(this);
	}
	
	public void changeGunA(Gun gun) {
		if(this.currentGun == this.gunA) this.currentGun = gun;
		this.gunA = gun;
	}

	public void changeGunB(Gun gun) {
		if(this.currentGun == this.gunB) this.currentGun = gun;
		this.gunB = gun;
	}
}

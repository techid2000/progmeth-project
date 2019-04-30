package logic;

import constants.SystemCache;
import object.weapon.gun.Gun;

public class Accessories {
	public int cash;
	public Gun primaryGun;
	public Gun secondaryGun;
	public int numberOfFragenade;
	
	public void gainCash(int gain) {
		this.cash += gain;
		System.out.println(this.cash);
		SystemCache.getInstance().gameUI.setCash(cash);
	}
}

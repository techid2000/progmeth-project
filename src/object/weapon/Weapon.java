package object.weapon;

import object.GameObject;

public abstract class Weapon extends GameObject {
	public int price;
	
	public Weapon() { }

	public int getPrice() { return this.price; }
	public void setPrice(int price) { this.price = price; }
}

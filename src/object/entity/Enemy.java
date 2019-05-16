package object.entity;

import constants.SystemCache;
import logic.GameObjectTag;
import object.overlay.Bar;

public abstract class Enemy extends Entity {
	public Enemy() {
		super();
		getTag().addTag(GameObjectTag.ENERMY);
		this.healthBar.destroy();
		this.healthBar = new Bar(this, Bar.Type.ENERMY_HEALTH, this.getMaxHealth(), this.getHealth());
		SystemCache.getInstance().gameCanvas.instantiate(this.healthBar);
	}
}

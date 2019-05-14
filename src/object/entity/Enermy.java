package object.entity;

import java.util.List;

import animation.AnimationClip;
import constants.SystemCache;
import interfaces.IAlive;
import javafx.geometry.Point2D;
import logic.Accessories;
import logic.GameObjectTag;
import object.overlay.Bar;

public abstract class Enermy extends Entity {
	public Enermy() {
		super();
		getTag().addTag(GameObjectTag.ENERMY);
		this.healthBar.destroy();
		this.healthBar = new Bar(this, Bar.Type.ENERMY_HEALTH, this.getMaxHealth(), this.getHealth());
		SystemCache.getInstance().gameCanvas.instantiate(this.healthBar);
	}
}

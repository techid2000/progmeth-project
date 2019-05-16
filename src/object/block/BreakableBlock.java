package object.block;

import constants.ImageHolder;
import interfaces.IDestroyable;

public class BreakableBlock extends Block implements IDestroyable {

	private int maxDurability;
	private int durability;

	public BreakableBlock() {
		super();
		this.durability = 50;
		setRenderSprite(ImageHolder.getInstance().breakableBlock.get(0));
	}

	@Override
	public void start() {
		maxDurability = durability;
	}

	@Override
	public void update() {
	}

	@Override
	public void getDamage(int damage) {

		durability = Math.max(0, durability - damage);
		if (durability == 0) {
			destroy();
		}
		double percentage = 100.0 * durability / maxDurability;
		if (percentage > 75)
			setRenderSprite(ImageHolder.getInstance().breakableBlock.get(0));
		else if (percentage > 50)
			setRenderSprite(ImageHolder.getInstance().breakableBlock.get(1));
		else if (percentage > 25)
			setRenderSprite(ImageHolder.getInstance().breakableBlock.get(2));
		else
			setRenderSprite(ImageHolder.getInstance().breakableBlock.get(3));
	}

}

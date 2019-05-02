package object.block;

import constants.ImageHolder;
import interfaces.IDestroyable;

public class BreakableBlock extends Block implements IDestroyable {

	public int maxDurability;
	public int durability = 25;
	
	public BreakableBlock() {
		super();
		setRenderSprite(ImageHolder.getInstance().breakableBlock);
	}
	
	@Override
	public void start() {
		maxDurability = durability;
	}

	@Override
	public void update(double deltaTime) {
		
	}

	@Override
	public void getDamage(int damage) {
		// TODO Auto-generated method stub
		durability = Math.max(0, durability - damage);
		if(100.0*durability/maxDurability < 50) {
			setRenderSprite(ImageHolder.getInstance().breakableBlockCracking);
		}
		if(durability == 0) {
			destroy();
		}
	}

}

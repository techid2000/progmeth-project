package object.block;

import constants.ImageHolder;
import interfaces.IDestroyable;

public class BreakableBlock extends Block implements IDestroyable {

	@Override
	public void start() {
		// TODO Auto-generated method stub
		setRenderSprite(ImageHolder.getInstance().destroyableBlock);
	}

	@Override
	public void update(double deltaTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getDamage() {
		// TODO Auto-generated method stub
		
	}

}

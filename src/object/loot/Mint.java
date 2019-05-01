package object.loot;

import java.util.List;

import animation.AnimationClip;
import constants.ImageHolder;
import constants.SystemCache;
import javafx.scene.image.Image;
import object.entity.Player;

public class Mint extends Loot {	
	public Type type;
	
	public Mint(Type type) {
		super();
		this.type = type;
		Object renderer = null;
		if((renderer = type.getRenderer()) instanceof AnimationClip) {
			setAnimationClip((AnimationClip)renderer);
			getAnimationClip().play();
		} else if(renderer instanceof Image) {
			setRenderSprite((Image)renderer);
		}
	}
	
	@Override
	public void pick(Player player) {
		player.accessories.gainCash(this.type.getValue());
		destroy();
	}

	@Override
	public void start() { }
	@Override
	public void update(double deltaTime) { }

	//enum
	public enum Type {
		SINGLE_COIN,
		COIN_PILE_0,
		COIN_PILE_1;
		
		public int getValue() {
			switch(this) {
			case SINGLE_COIN:
				return 10;
			case COIN_PILE_0:
				return 100;
			case COIN_PILE_1:
				return 100;
			}
			return 0;
		}
		
		public Object getRenderer() {
			switch(this) {
			case SINGLE_COIN:
				AnimationClip animation = new AnimationClip(ImageHolder.getInstance().singleCoin, 1000);
				animation.setRandomFrameIndexOffset();
				return animation;
			case COIN_PILE_0:
				return ImageHolder.getInstance().coinPile0;
			case COIN_PILE_1:
				return ImageHolder.getInstance().coinPile1;
			}
			return null;
		}
	}
}

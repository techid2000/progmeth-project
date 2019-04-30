package object.loot;

import constants.ImageHolder;
import constants.SystemCache;
import javafx.scene.image.Image;
import object.entity.Player;

public class Mint extends Loot {
	public enum Type {
		SINGLE_COIN(10, ImageHolder.getInstance().singleCoin),
		COIN_PILE_0(100, ImageHolder.getInstance().coinPile0),
		COIN_PILE_1(100, ImageHolder.getInstance().coinPile1);
		
		private int value;
		private Image sprite;
		
		private Type(int value, Image sprite) {
			this.value = value;
			this.sprite = sprite;
		}
		
		public int getValue() { return this.value; }
		public Image getSprite() { return this.sprite; }
	}
	
	private Type type;
	
	public Mint(Type type) {
		super();
		setRenderSprite(type.getSprite());
		this.type = type;
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
}

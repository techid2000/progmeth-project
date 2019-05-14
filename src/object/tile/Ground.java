package object.tile;

import constants.ImageHolder;
import javafx.scene.image.Image;

public class Ground extends Tile {

	public enum Style {
		GROUND(ImageHolder.getInstance().ground);
		
		private Image sprite;
		
		private Style(Image sprite) {
			this.sprite = sprite;
		}
		
		public Image getSprite() {
			return this.sprite;
		}
	}
	
	public Style style;
	
	public Ground(Style style) {
		setRenderSprite(style.getSprite());
	}
	
	@Override
	public void start() {}
	@Override
	public void update() {}
}

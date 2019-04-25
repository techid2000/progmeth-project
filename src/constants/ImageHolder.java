package constants;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;

public class ImageHolder {
	private static final ImageHolder instance = new ImageHolder(); 
	public static ImageHolder getInstance() {return instance;}
	
	public List<Image> slime;
	public Image pointer;
	public Image unbreakableBlock;
	public Image destroyableBlock;
	public Image coinPile0;
	public ImageHolder() {
		slime = loadImageList("slime/slime", 8);
		pointer = loadImage("overlay/pointer");
		unbreakableBlock = loadImage("block/unbreakable_block");
		destroyableBlock = loadImage("block/destroyable_block");
		coinPile0 = loadImage("loot/coinpile0");
	}
	
	public Image loadImage(String prefixName) {
		return new Image(ClassLoader.getSystemResourceAsStream("image/" + prefixName + ".png"));
	}
	public List<Image> loadImageList(String prefixName, int number) {
		List<Image> list = new ArrayList<Image>();
		for(int i=0; i<number; i++) {
			list.add(loadImage(prefixName + i));
		}
		return list;
	}
}

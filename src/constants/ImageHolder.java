package constants;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;

public class ImageHolder {
	
	private static String PNG = "png";
	
	private static final ImageHolder instance = new ImageHolder(); 
	public static ImageHolder getInstance() {return instance;}
	
	public List<Image> slime;
	public Image pointer;
	public Image unbreakableBlock;
	public Image destroyableBlock;
	public Image coinPile0;
	public ImageHolder() {
		slime = loadImageList("slime/slime", PNG, 8);
		pointer = loadImage("overlay/pointer",PNG);
		unbreakableBlock = loadImage("block/unbreakable_block", PNG);
		destroyableBlock = loadImage("block/destroyable_block", PNG);
		coinPile0 = loadImage("loot/coinpile0", PNG);
	}
	
	public Image loadImage(String prefixName, String fileType) {
		return new Image(ClassLoader.getSystemResourceAsStream("image/" + prefixName + "." + fileType));
	}
	public List<Image> loadImageList(String prefixName, String fileType, int number) {
		List<Image> list = new ArrayList<Image>();
		for(int i=0; i<number; i++) {
			list.add(loadImage(prefixName + i, fileType));
		}
		return list;
	}
}

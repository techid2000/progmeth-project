package constants;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;

public class ImageHolder {
	private static final ImageHolder instance = new ImageHolder(); 
	public static ImageHolder getInstance() {return instance;}
	
	public List<Image> slime;
	
	public ImageHolder() {
		slime = loadImageList("slime/slime", 8);
	}
	
	public Image loadImage(String name) {
		return new Image(ClassLoader.getSystemResourceAsStream(name + ".png"));
	}
	public List<Image> loadImageList(String prefixName, int number) {
		List<Image> list = new ArrayList<Image>();
		for(int i=0; i<number; i++) {
			list.add(loadImage(prefixName + i));
		}
		return list;
	}
}

package constants;

import javafx.scene.image.Image;

public class ResourceHolder {
	private static final ResourceHolder instance = new ResourceHolder(); 
	public static ResourceHolder getInstance() {
		return instance;
	}
	
	public Image block;
	
	public ResourceHolder() {
		block = new Image(ClassLoader.getSystemResourceAsStream("block.png"));
	}
}

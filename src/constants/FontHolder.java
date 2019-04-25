package constants;

import javafx.scene.text.Font;

public class FontHolder {
	private static final FontHolder instance = new FontHolder();
	public static FontHolder getInstance () { return instance; }
	
	public Font font1;
	
	public FontHolder() {
		font1 = loadFont("upheavtt.ttf");
	}
	
	public Font loadFont(String name) {
		return Font.loadFont(ClassLoader.getSystemResourceAsStream("font/" + name), 36);
	}
}

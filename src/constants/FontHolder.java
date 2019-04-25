package constants;

import javafx.scene.text.Font;

public class FontHolder {
	
	private static String TTF = "ttf";
	
	private static final FontHolder instance = new FontHolder();
	public static FontHolder getInstance () { return instance; }
	
	public Font font1;
	
	public FontHolder() {
		font1 = loadFont("upheavtt", TTF);
	}
	
	public Font loadFont(String name, String fontType) {
		return Font.loadFont(ClassLoader.getSystemResourceAsStream("font/" + name + "." + fontType), 36);
	}
}

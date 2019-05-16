package constants;

import javafx.scene.text.Font;

public class FontHolder {

	private static final String TTF = "ttf";

	private static final FontHolder instance = new FontHolder();

	public static FontHolder getInstance() {
		return instance;
	}

	public Font font18;
	public Font font24;
	public Font font28;
	public Font font36;
	public Font font48;
	public Font font64;

	public FontHolder() {
		font18 = loadFont("upheavtt", TTF, 18);
		font24 = loadFont("upheavtt", TTF, 24);
		font28 = loadFont("upheavtt", TTF, 28);
		font36 = loadFont("upheavtt", TTF, 36);
		font48 = loadFont("upheavtt", TTF, 48);
		font64 = loadFont("upheavtt", TTF, 64);
	}

	public Font loadFont(String name, String fontType, double size) {
		return Font.loadFont(ClassLoader.getSystemResourceAsStream("font/" + name + "." + fontType), size);
	}
}

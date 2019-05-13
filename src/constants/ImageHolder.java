package constants;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;

public class ImageHolder {
	
	private static String PNG = "png";
	
	private static final ImageHolder instance = new ImageHolder(); 
	public static ImageHolder getInstance() {return instance;}
	
	public Image nothing;
	
	public List<Image> slime;
	public Image pointer;
	public Image unbreakableBlock;
	public List<Image> breakableBlock;
	public Image ground;
	public Image coinPile0;
	public Image coinPile1;
	public Image projectile;
	public Image shotgun;
	public Image pistol;
	
	public List<Image> singleCoin;
	public List<Image> playerStandStill;
	public List<Image> playerRunUp;
	public List<Image> playerRunDown;
	public List<Image> playerRunLeft;
	public List<Image> playerRunRight;
	public List<Image> playerRunLeftUp;
	public List<Image> playerRunRightUp;
	public List<Image> playerRunLeftDown;
	public List<Image> playerRunRightDown;
	
	public ImageHolder() {
		nothing = loadImage("nothing", PNG);
		slime = loadImageList("slime/slime", PNG, 8);
		pointer = loadImage("overlay/pointer",PNG);
		unbreakableBlock = loadImage("block/unbreakableblock", PNG);
		breakableBlock = loadImageList("block/breakableblock",PNG,4);
		
		coinPile0 = loadImage("loot/coinpile0", PNG);
		coinPile1 = loadImage("loot/coinpile1",PNG);
		singleCoin = loadImageList("loot/singlecoin",PNG,8);
		ground = loadImage("block/ground", PNG);
		projectile = loadImage("weapon/projectile/pistol", PNG);
		shotgun = loadImage("weapon/Gun/shotgun", PNG);
		pistol = loadImage("weapon/Gun/pistol", PNG);
		playerStandStill = loadImageList("player/standstill_pistol/standstill",PNG,8);
		playerRunUp = loadImageList("player/run_up_pistol/run",PNG,6);
		playerRunDown = loadImageList("player/run_down_pistol/run",PNG,6);
		playerRunLeft = loadImageList("player/run_left_pistol/run",PNG,6);
		playerRunRight = loadImageList("player/run_right_pistol/run",PNG,6);
		playerRunLeftUp = loadImageList("player/run_leftup_pistol/run",PNG,6);
		playerRunRightUp = loadImageList("player/run_rightup_pistol/run",PNG,6);
		playerRunLeftDown = loadImageList("player/run_leftdown_pistol/run",PNG,6);
		playerRunRightDown = loadImageList("player/run_rightdown_pistol/run",PNG,6);
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

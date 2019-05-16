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
	public List<Image> slimeGunner;
	public Image pointer;
	public Image unbreakableBlock;
	public List<Image> breakableBlock;
	public Image ground;
	public Image coinPile0;
	public Image coinPile1;
	public Image projectile;
	public Image beam;
	public Image shotgun;
	public Image pistol;
	public Image assultRifle;
	public Image sniperRifle;
	public Image shopMenu;
	public Image gunSlot;
	public Image gunSlotHighlight;
	public Image gunSlotPressed;
	public Image coinWithBullet;
	public Image skull;
	
	public Image playButton;
	public Image resumeButton;
	public Image quitButton;
	public Image mainmenuButton;
	public Image playButtonHighlight;
	public Image resumeButtonHighlight;
	public Image quitButtonHighlight;
	public Image mainmenuButtonHighlight;
	
	public List<Image> singleCoin;

	public List<Image> playerRunUpPistol;
	public List<Image> playerRunDownPistol;
	public List<Image> playerRunLeftPistol;
	public List<Image> playerRunRightPistol;
	public List<Image> playerRunLeftUpPistol;
	public List<Image> playerRunRightUpPistol;
	public List<Image> playerRunLeftDownPistol;
	public List<Image> playerRunRightDownPistol;
	
	public List<Image> playerRunUpShotgun;
	public List<Image> playerRunDownShotgun;
	public List<Image> playerRunLeftShotgun;
	public List<Image> playerRunRightShotgun;
	public List<Image> playerRunLeftUpShotgun;
	public List<Image> playerRunRightUpShotgun;
	public List<Image> playerRunLeftDownShotgun;
	public List<Image> playerRunRightDownShotgun;
	
	public List<Image> playerRunUpAssultRifle;
	public List<Image> playerRunDownAssultRifle;
	public List<Image> playerRunLeftAssultRifle;
	public List<Image> playerRunRightAssultRifle;
	public List<Image> playerRunLeftUpAssultRifle;
	public List<Image> playerRunRightUpAssultRifle;
	public List<Image> playerRunLeftDownAssultRifle;
	public List<Image> playerRunRightDownAssultRifle;
	
	public List<Image> playerRunUpSniperRifle;
	public List<Image> playerRunDownSniperRifle;
	public List<Image> playerRunLeftSniperRifle;
	public List<Image> playerRunRightSniperRifle;
	public List<Image> playerRunLeftUpSniperRifle;
	public List<Image> playerRunRightUpSniperRifle;
	public List<Image> playerRunLeftDownSniperRifle;
	public List<Image> playerRunRightDownSniperRifle;
	
	public ImageHolder() {
		nothing = loadImage("nothing", PNG);
		slime = loadImageList("slime/slime", PNG, 8);
		slimeGunner = loadImageList("slime/slimegunner",PNG, 4);
		
		pointer = loadImage("overlay/pointer",PNG);
		unbreakableBlock = loadImage("block/unbreakableblock", PNG);
		breakableBlock = loadImageList("block/breakableblock",PNG,4);
		
		coinPile0 = loadImage("loot/coinpile0", PNG);
		coinPile1 = loadImage("loot/coinpile1",PNG);
		singleCoin = loadImageList("loot/singlecoin",PNG,8);
		ground = loadImage("block/ground", PNG);
		
		projectile = loadImage("weapon/projectile/pistol", PNG);
		beam = loadImage("slime/beam",PNG);
		shotgun = loadImage("weapon/Gun/shotgun", PNG);
		pistol = loadImage("weapon/Gun/pistol", PNG);
		assultRifle = loadImage("weapon/Gun/assultRifle",PNG);
		sniperRifle = loadImage("weapon/Gun/sniperRifle", PNG);
		
		shopMenu = loadImage("gui/shop/shopmenu",PNG);
		gunSlot = loadImage("gui/shop/gunslot",PNG);
		gunSlotHighlight = loadImage("gui/shop/gunslot_highlight",PNG);
		gunSlotPressed = loadImage("gui/shop/gunslot_pressed",PNG);
		coinWithBullet = loadImage("gui/shop/coinwithbullet",PNG);
		skull = loadImage("gui/skull",PNG);
		
		playButton = loadImage("gui/mainmenu/playbutton", PNG);
		resumeButton = loadImage("gui/mainmenu/resumebutton", PNG);
		quitButton = loadImage("gui/mainmenu/quitbutton", PNG);
		mainmenuButton = loadImage("gui/mainmenu/mainmenubutton", PNG);
		
		playButtonHighlight = loadImage("gui/mainmenu/playbutton_highlight", PNG);
		resumeButtonHighlight = loadImage("gui/mainmenu/resumebutton_highlight", PNG);
		quitButtonHighlight = loadImage("gui/mainmenu/quitbutton_highlight", PNG);
		mainmenuButtonHighlight = loadImage("gui/mainmenu/mainmenubutton_highlight", PNG);
		
		playerRunUpPistol = loadImageList("player/run_up_pistol/run",PNG,6);
		playerRunDownPistol = loadImageList("player/run_down_pistol/run",PNG,6);
		playerRunLeftPistol = loadImageList("player/run_left_pistol/run",PNG,6);
		playerRunRightPistol = loadImageList("player/run_right_pistol/run",PNG,6);
		playerRunLeftUpPistol = loadImageList("player/run_leftup_pistol/run",PNG,6);
		playerRunRightUpPistol = loadImageList("player/run_rightup_pistol/run",PNG,6);
		playerRunLeftDownPistol = loadImageList("player/run_leftdown_pistol/run",PNG,6);
		playerRunRightDownPistol = loadImageList("player/run_rightdown_pistol/run",PNG,6);
		
		playerRunUpShotgun = loadImageList("player/run_up_shotgun/run",PNG,6);
		playerRunDownShotgun = loadImageList("player/run_down_shotgun/run",PNG,6);
		playerRunLeftShotgun = loadImageList("player/run_left_shotgun/run",PNG,6);
		playerRunRightShotgun = loadImageList("player/run_right_shotgun/run",PNG,6);
		playerRunLeftUpShotgun = loadImageList("player/run_leftup_shotgun/run",PNG,6);
		playerRunRightUpShotgun = loadImageList("player/run_rightup_shotgun/run",PNG,6);
		playerRunLeftDownShotgun = loadImageList("player/run_leftdown_shotgun/run",PNG,6);
		playerRunRightDownShotgun = loadImageList("player/run_rightdown_shotgun/run",PNG,6);
		
		playerRunUpAssultRifle = loadImageList("player/run_up_assultrifle/run",PNG,6);
		playerRunDownAssultRifle = loadImageList("player/run_down_assultrifle/run",PNG,6);
		playerRunLeftAssultRifle = loadImageList("player/run_left_assultrifle/run",PNG,6);
		playerRunRightAssultRifle = loadImageList("player/run_right_assultrifle/run",PNG,6);
		playerRunLeftUpAssultRifle = loadImageList("player/run_leftup_assultrifle/run",PNG,6);
		playerRunRightUpAssultRifle = loadImageList("player/run_rightup_assultrifle/run",PNG,6);
		playerRunLeftDownAssultRifle = loadImageList("player/run_leftdown_assultrifle/run",PNG,6);
		playerRunRightDownAssultRifle = loadImageList("player/run_rightdown_assultrifle/run",PNG,6);
		
		playerRunUpSniperRifle = loadImageList("player/run_up_sniperrifle/run",PNG,6);
		playerRunDownSniperRifle = loadImageList("player/run_down_sniperrifle/run",PNG,6);
		playerRunLeftSniperRifle = loadImageList("player/run_left_sniperrifle/run",PNG,6);
		playerRunRightSniperRifle = loadImageList("player/run_right_sniperrifle/run",PNG,6);
		playerRunLeftUpSniperRifle = loadImageList("player/run_leftup_sniperrifle/run",PNG,6);
		playerRunRightUpSniperRifle = loadImageList("player/run_rightup_sniperrifle/run",PNG,6);
		playerRunLeftDownSniperRifle = loadImageList("player/run_leftdown_sniperrifle/run",PNG,6);
		playerRunRightDownSniperRifle = loadImageList("player/run_rightdown_sniperrifle/run",PNG,6);
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

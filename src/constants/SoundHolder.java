package constants;

import javafx.scene.media.AudioClip;

public class SoundHolder {
	private static String WAV = "wav";

	private static final SoundHolder instance = new SoundHolder();

	public static SoundHolder getInstance() {
		return instance;
	}

	public AudioClip pistol;
	public AudioClip pistol_reload;
	public AudioClip shotgun;
	public AudioClip shotgun_reload;
	public AudioClip assultrifle;
	public AudioClip assultrifle_reload;
	public AudioClip sniperrifle;
	public AudioClip sniperrifle_reload;
	public AudioClip pickupcoin;
	public AudioClip empty;

	public SoundHolder() {
		pistol = loadSound("gun/pistol", WAV);
		shotgun = loadSound("gun/shotgun", WAV);
		assultrifle = loadSound("gun/assultrifle", WAV);
		sniperrifle = loadSound("gun/sniperrifle", WAV);
		pistol_reload = loadSound("gun/pistol_reload", WAV);
		shotgun_reload = loadSound("gun/shotgun_reload", WAV);
		assultrifle_reload = loadSound("gun/assultrifle_reload", WAV);
		sniperrifle_reload = loadSound("gun/sniperrifle_reload", WAV);
		
		pickupcoin = loadSound("loot/pickupcoin", WAV);
		empty = loadSound("gun/empty", WAV);
		
		//set volume
//		assultrifle.setVolume();
	}

	public AudioClip loadSound(String prefixName, String fileType) {
		return new AudioClip(ClassLoader.getSystemResource("sound/" + prefixName + '.' + fileType).toString());
	}
}

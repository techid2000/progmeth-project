package constants;

import scene.GameScene;

public class SystemCache {
	public static SystemCache instance = new SystemCache();
	public static SystemCache getInstance() { return instance; }

	public GameScene gameScene;
}

package constants;

import java.util.HashMap;
import java.util.Map;

import event.GameEvent;
import gui.AccessoriesUI;
import gui.GameCanvas;
import gui.GameUI;
import gui.ShopUI;
import object.GameObject;
import object.entity.Player;
import scene.SceneHolder;

public class SystemCache {
	public static SystemCache instance = new SystemCache();
	public static SystemCache getInstance() { return instance; }

	public GameEvent gameEvent;
	public GameCanvas gameCanvas;
	public GameUI gameUI;
	public AccessoriesUI accessoriesUI;
	public ShopUI shopUI;
	public double deltaTime;
	public Player player;
	public SceneHolder sceneHolder;
}

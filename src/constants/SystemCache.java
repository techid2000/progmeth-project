package constants;

import java.util.HashMap;
import java.util.Map;

import event.GameEvent;
import gui.GameCanvas;
import gui.GameUI;
import object.GameObject;

public class SystemCache {
	public static SystemCache instance = new SystemCache();
	public static SystemCache getInstance() { return instance; }

	public GameEvent gameEvent;
	public GameCanvas gameCanvas;
	public GameUI gameUI;
}

package constants;

import event.GameEvent;
import gui.GameCanvas;

public class SystemCache {
	public static SystemCache instance = new SystemCache();
	public static SystemCache getInstance() { return instance; }

	public GameEvent gameEvent;
	public GameCanvas gameCanvas;
}

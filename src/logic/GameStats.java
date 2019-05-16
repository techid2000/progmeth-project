package logic;

import constants.SystemCache;

public class GameStats {
	
	public static int score = -1;
	public static int wave = -1;

	public static void reset() {
		score = wave = 0;
	}
	public static void increaseScore(int increased) {
		score += increased;
		SystemCache.getInstance().gameUI.setScore(score);
	}
}

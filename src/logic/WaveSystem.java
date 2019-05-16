package logic;

import constants.SystemCache;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.util.Duration;
import object.entity.Slime;

public class WaveSystem {
	public int waveCount;
	public int monsterCount;
	public void nextWave() {
		waveCount++;
		SystemCache.getInstance().gameUI.waveAlert();
		for(int i=1; i<=(int)Math.exp(1 + 0.1*waveCount); i++) {		
			Slime s = new Slime();
			s.setPosition(new Point2D(2,1));
			SystemCache.getInstance().gameCanvas.instantiate(s);
		}
		monsterCount = (int)Math.exp(1 + 0.1*waveCount);
	}
	
	public void monsterKilled() {
		monsterCount--;
		if(monsterCount == 0) {
			Timeline delayBeforeNext = new Timeline(
				new KeyFrame(new Duration(2000), (e) -> nextWave())
			);
			delayBeforeNext.play();
		}
	}
}

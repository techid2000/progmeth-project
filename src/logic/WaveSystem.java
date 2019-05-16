package logic;

import constants.SystemCache;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.util.Duration;
import object.entity.Slime;
import object.entity.SlimeGunner;

public class WaveSystem {
	private int waveCount;
	private int monsterCount;

	public void nextWave() {
		setWaveCount(getWaveCount()+1);
		SystemCache.getInstance().gameUI.waveAlert();
		for (int i = 1; i <= (int) Math.exp(1 + 0.1 * waveCount); i++) {
			double rand = Math.random();
			if (rand <= (waveCount / 20.0) * 0.5) {
				SlimeGunner slimeGunner = new SlimeGunner();
				slimeGunner.setPosition(new Point2D(2, 1));
				SystemCache.getInstance().gameCanvas.instantiate(slimeGunner);
			} else {
				Slime slime = new Slime();
				slime.setPosition(new Point2D(2, 1));
				SystemCache.getInstance().gameCanvas.instantiate(slime);
			}
		}
		monsterCount = (int) Math.exp(1 + 0.1 * waveCount);
		SystemCache.getInstance().gameUI.setMonsterRemain(monsterCount);
	}

	public void monsterKilled() {
		setMonsterCount(getMonsterCount() - 1);
		if (monsterCount == 0) {
			Timeline delayBeforeNext = new Timeline(new KeyFrame(new Duration(2000), (e) -> nextWave()));
			delayBeforeNext.play();
		}
		SystemCache.getInstance().gameUI.setMonsterRemain(monsterCount);
	}

	public int getWaveCount() {
		return waveCount;
	}

	private void setWaveCount(int waveCount) {
		this.waveCount = waveCount;
		SystemCache.getInstance().gameUI.setWaveCount(getWaveCount());
	}

	private int getMonsterCount() {
		return monsterCount;
	}

	private void setMonsterCount(int monsterCount) {
		this.monsterCount = monsterCount;
	}

}

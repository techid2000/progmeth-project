package event;

import constants.SystemCache;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import scene.GameScene;

public class GameEvent {
	private boolean holdW;
	private boolean holdA;
	private boolean holdS;
	private boolean holdD;
	
	private SimpleDoubleProperty WFrac;
	private SimpleDoubleProperty AFrac;
	private SimpleDoubleProperty SFrac;
	private SimpleDoubleProperty DFrac;
	
	public GameEvent() {
		WFrac = new SimpleDoubleProperty();
		AFrac = new SimpleDoubleProperty();
		SFrac = new SimpleDoubleProperty();
		DFrac = new SimpleDoubleProperty();
		assign();
	}
	
	public void assign() {
		Scene scene = SystemCache.getInstance().gameScene;
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				System.out.println("test");
				if(event.getCode() == KeyCode.W) {lerpValue(WFrac,1);}
				if(event.getCode() == KeyCode.A) {lerpValue(AFrac,1);}
				if(event.getCode() == KeyCode.S) {lerpValue(SFrac,1);}
				if(event.getCode() == KeyCode.D) {lerpValue(DFrac,1);}
			}
		});
		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if(event.getCode() == KeyCode.W) {lerpValue(WFrac,0);}
				if(event.getCode() == KeyCode.A) {lerpValue(AFrac,0);}
				if(event.getCode() == KeyCode.S) {lerpValue(SFrac,0);}
				if(event.getCode() == KeyCode.D) {lerpValue(DFrac,0);}
			}
		});
	}
	
	private void lerpValue(SimpleDoubleProperty value, double to) {
		double from = value.get();
		new Transition() {
			{ setCycleDuration(new Duration(200));
			  setInterpolator(Interpolator.LINEAR); }
			protected void interpolate(double frac) {
				// TODO Auto-generated method stub
				value.set((to - from)*frac + from);
			}
		}.play();
	}

	public boolean isHoldW() {return holdW;}
	public boolean isHoldA() {return holdA;}
	public boolean isHoldS() {return holdS;}
	public boolean isHoldD() {return holdD;}
	public double getHorizontalFraction() {return this.DFrac.get() - this.AFrac.get();}
	public double getVerticalFraction() {return this.SFrac.get() - this.WFrac.get();}
}

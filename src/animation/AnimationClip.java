package animation;

import java.util.List;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.util.Duration;
import object.GameObject;

public class AnimationClip extends Transition {

	private GameObject bindGameObject;
	private List<Image> spriteList;
	private int frameIndex;
	private boolean reverseFrame;
	
	public AnimationClip(List<Image> spriteList, double cycleDuration) {
		setSpriteList(spriteList);
		setCycleDuration(new Duration(cycleDuration));
		setInterpolator(Interpolator.LINEAR);
		setCycleCount(INDEFINITE);
		setFrameIndex(0);
	}
	public AnimationClip(List<Image> spriteList, double cycleDuration,  GameObject bindGameObject) {
		this(spriteList, cycleDuration);
		setBindGameObject(bindGameObject);
	}
	public AnimationClip(List<Image> spriteList, double cycleDuration,  GameObject bindGameObject, int cycleCount) {
		this(spriteList, cycleDuration, bindGameObject);
		setCycleCount(cycleCount);
	}

	@Override
	protected void interpolate(double arg0) {
		// TODO Auto-generated method stub
		if(arg0 == 1) return;
		setFrameIndex((int)(arg0 * getSpriteList().size()));
		bindGameObject.setRenderSprite(getSpriteList().get(getFrameIndex()));
	}
	
	public GameObject getBindGameObject() {
		return bindGameObject;
	}
	public void setBindGameObject(GameObject bindGameObject) {
		this.bindGameObject = bindGameObject;
	}
	public List<Image> getSpriteList() {
		return spriteList;
	}
	public void setSpriteList(List<Image> spriteList) {
		this.spriteList = spriteList;
	}
	public int getFrameIndex() {
		return frameIndex;
	}
	private void setFrameIndex(int frameIndex) {
		this.frameIndex = frameIndex;
	}
	public boolean isReverseFrame() {
		return this.reverseFrame;
	}
	public void setReverseFrame(boolean reverseFrame) {
		this.reverseFrame = reverseFrame;
	}
}

package object.entity;

import java.util.ArrayList;
import java.util.List;

import animation.AnimationClip;
import app.MainApp;
import constants.GameTaskManager;
import constants.ImageHolder;
import constants.SystemCache;
import event.GameEvent;
import gui.GameCanvas;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.transform.Rotate;
import logic.Accessories;
import logic.BoxCollider;
import logic.GameObjectTag;
import object.loot.Mint;
import object.overlay.Popup;
import object.weapon.gun.AssultRifle;
import object.weapon.gun.Gun;
import object.weapon.gun.Pistol;
import object.weapon.gun.Shotgun;
import object.weapon.gun.SniperRifle;
import scene.MainMenuScene;
import utility.Utility;

public class Player extends Entity {
	// logic
	private Accessories accessories;

	// resources
	private List<AnimationClip> clipsPistol;

	private List<AnimationClip> clipsShotgun;
	private List<AnimationClip> clipsAssultRifle;
	private List<AnimationClip> clipsSniperRifle;

	private List<AnimationClip> clips;

	private double regenCooldown;
	private double regenInterval;

	public Player() {
		getTag().addTag(GameObjectTag.PLAYER);
		SystemCache.getInstance().player = this;

		// config later
		getHealth().set(100);
		getMaxHealth().set(100);
		this.accessories = new Accessories();

		setMoveSpeed(2);
		setPivot(new Point2D(0.5, 1));
		getCollisionSystem().addBoxCollider(-0.25, -0.5, 0.5, 0.5);
		setZOrder(0);

		clipsPistol = new ArrayList<AnimationClip>();
		clipsPistol.add(new AnimationClip(ImageHolder.getInstance().playerRunRightPistol, 500));
		clipsPistol.add(new AnimationClip(ImageHolder.getInstance().playerRunRightDownPistol, 500));
		clipsPistol.add(new AnimationClip(ImageHolder.getInstance().playerRunDownPistol, 500));
		clipsPistol.add(new AnimationClip(ImageHolder.getInstance().playerRunLeftDownPistol, 500));
		clipsPistol.add(new AnimationClip(ImageHolder.getInstance().playerRunLeftPistol, 500));
		clipsPistol.add(new AnimationClip(ImageHolder.getInstance().playerRunLeftUpPistol, 500));
		clipsPistol.add(new AnimationClip(ImageHolder.getInstance().playerRunUpPistol, 500));
		clipsPistol.add(new AnimationClip(ImageHolder.getInstance().playerRunRightUpPistol, 500));

		clipsShotgun = new ArrayList<AnimationClip>();
		clipsShotgun.add(new AnimationClip(ImageHolder.getInstance().playerRunRightShotgun, 500));
		clipsShotgun.add(new AnimationClip(ImageHolder.getInstance().playerRunRightDownShotgun, 500));
		clipsShotgun.add(new AnimationClip(ImageHolder.getInstance().playerRunDownShotgun, 500));
		clipsShotgun.add(new AnimationClip(ImageHolder.getInstance().playerRunLeftDownShotgun, 500));
		clipsShotgun.add(new AnimationClip(ImageHolder.getInstance().playerRunLeftShotgun, 500));
		clipsShotgun.add(new AnimationClip(ImageHolder.getInstance().playerRunLeftUpShotgun, 500));
		clipsShotgun.add(new AnimationClip(ImageHolder.getInstance().playerRunUpShotgun, 500));
		clipsShotgun.add(new AnimationClip(ImageHolder.getInstance().playerRunRightUpShotgun, 500));

		clipsSniperRifle = new ArrayList<AnimationClip>();
		clipsSniperRifle.add(new AnimationClip(ImageHolder.getInstance().playerRunRightSniperRifle, 500));
		clipsSniperRifle.add(new AnimationClip(ImageHolder.getInstance().playerRunRightDownSniperRifle, 500));
		clipsSniperRifle.add(new AnimationClip(ImageHolder.getInstance().playerRunDownSniperRifle, 500));
		clipsSniperRifle.add(new AnimationClip(ImageHolder.getInstance().playerRunLeftDownSniperRifle, 500));
		clipsSniperRifle.add(new AnimationClip(ImageHolder.getInstance().playerRunLeftSniperRifle, 500));
		clipsSniperRifle.add(new AnimationClip(ImageHolder.getInstance().playerRunLeftUpSniperRifle, 500));
		clipsSniperRifle.add(new AnimationClip(ImageHolder.getInstance().playerRunUpSniperRifle, 500));
		clipsSniperRifle.add(new AnimationClip(ImageHolder.getInstance().playerRunRightUpSniperRifle, 500));

		clipsAssultRifle = new ArrayList<AnimationClip>();
		clipsAssultRifle.add(new AnimationClip(ImageHolder.getInstance().playerRunRightAssultRifle, 500));
		clipsAssultRifle.add(new AnimationClip(ImageHolder.getInstance().playerRunRightDownAssultRifle, 500));
		clipsAssultRifle.add(new AnimationClip(ImageHolder.getInstance().playerRunDownAssultRifle, 500));
		clipsAssultRifle.add(new AnimationClip(ImageHolder.getInstance().playerRunLeftDownAssultRifle, 500));
		clipsAssultRifle.add(new AnimationClip(ImageHolder.getInstance().playerRunLeftAssultRifle, 500));
		clipsAssultRifle.add(new AnimationClip(ImageHolder.getInstance().playerRunLeftUpAssultRifle, 500));
		clipsAssultRifle.add(new AnimationClip(ImageHolder.getInstance().playerRunUpAssultRifle, 500));
		clipsAssultRifle.add(new AnimationClip(ImageHolder.getInstance().playerRunRightUpAssultRifle, 500));

		clips = clipsPistol;
		setAnimationClip(clips.get(0));

	}

	public void playerControl() {
		GameEvent gameEvent = SystemCache.getInstance().gameEvent;
		GameCanvas gameCanvas = SystemCache.getInstance().gameCanvas;
		double deltaTime = SystemCache.getInstance().deltaTime;
		// movement
		double horizontal = gameEvent.getHorizontalFraction();
		double vertical = gameEvent.getVerticalFraction();
		Point2D fraction = new Point2D(horizontal, vertical);
		Point2D nextPosition = getPosition().add(fraction.multiply(getMoveSpeed() * deltaTime));
		setPositionByColliderCheck(nextPosition);

		// rotation
		Point2D direction = gameCanvas.mouseToScaledPoint2D(gameEvent.getMousePosition())
				.subtract(getPosition().subtract(new Point2D(0, 0.5)));
		Rotate rotation = Utility.pointToRotate(direction);
		double MIN = Double.MAX_VALUE;
		int index = 0;
		for (int angle = 0; angle <= 360; angle += 45) {
			if (Math.abs(angle - rotation.getAngle()) < MIN) {
				MIN = Math.abs(angle - rotation.getAngle());
				index = (angle % 360) / 45;
			}
		}
		setAnimationClip(clips.get(index));

		// animation control (walking-standing)
		if (fraction.magnitude() == 0) {
			if (getAnimationClip().getFrameIndex() == 0)
				getAnimationClip().pause();
		} else {
			getAnimationClip().play();
		}

		// mouse & keyboard events
//		if(gameEvent.getMouseHolding(MouseButton.PRIMARY)) {
		Point2D centre = getPosition().subtract(new Point2D(0, 0.5));
		Point2D bdir = gameCanvas.mouseToScaledPoint2D(gameEvent.getMousePosition()).subtract(centre);
		this.accessories.updateCurrentGun(centre, bdir.normalize());
		if (gameEvent.getMouseHolding(MouseButton.PRIMARY)) {
			regenCooldown = 3;
		}
		if (regenCooldown > 0) {
			regenCooldown -= SystemCache.getInstance().deltaTime;
		} else {
			if (regenInterval > 0) {
				regenInterval -= SystemCache.getInstance().deltaTime;
			} else {
				if (getHealth().get() < getMaxHealth().get()) {
					doHeal(5);
					regenInterval = 1;
				}
			}
		}
		if (gameEvent.getScrollChanged()) {
			this.accessories.swapGun();
			Gun curGun = this.accessories.getCurrentGun();
			if (curGun instanceof Pistol) {
				clips = clipsPistol;
			} else if (curGun instanceof Shotgun) {
				clips = clipsShotgun;
			} else if (curGun instanceof AssultRifle) {
				clips = clipsAssultRifle;
			} else if (curGun instanceof SniperRifle) {
				clips = clipsSniperRifle;
			}
		}
		if (gameEvent.getSingleKeyUp(KeyCode.R)) {
			this.accessories.reloadCurrentGun();
		}
//		if (gameEvent.getSingleKeyUp(KeyCode.C)) {
//			this.accessories.gainCash(1000);
//		}
	}

	public void lootCheck() {
		BoxCollider walkingCollider = getCollisionSystem().getBoxColliders().get(0);
		List<BoxCollider> loots = walkingCollider.getIntersectedCollider(GameObjectTag.LOOT);
		for (BoxCollider loot : loots) {
			((Mint) loot.gameObject).pick(this);

			Popup ok = new Popup(this, Popup.Type.CASH, ((Mint) loot.gameObject).type.getValue());
			SystemCache.getInstance().gameCanvas.instantiate(ok);
		}
	}

	private void setPositionByColliderCheck(Point2D nextPosition) {
		Point2D origin = getPosition();
		Point2D nextPosX = new Point2D(nextPosition.getX(), origin.getY());
		Point2D nextPosY = new Point2D(origin.getX(), nextPosition.getY());
		setPosition(nextPosition);
		BoxCollider walkingCollider = getCollisionSystem().getBoxColliders().get(0);
		if (walkingCollider.getIntersectedCollider(GameObjectTag.BLOCK).size() == 0)
			return;
		setPosition(nextPosX);
		if (walkingCollider.getIntersectedCollider(GameObjectTag.BLOCK).size() == 0)
			return;
		setPosition(nextPosY);
		if (walkingCollider.getIntersectedCollider(GameObjectTag.BLOCK).size() == 0)
			return;
		setPosition(origin);
	}

	public void start() {
		SystemCache.getInstance().accessoriesUI.updateInfo(this.accessories);
	}

	@Override
	public void update() {
		playerControl();
		lootCheck();
	}

	public Accessories getAccessories() {
		return accessories;
	}

	public void getDamage(int damage) {
		super.getDamage(damage);
		regenCooldown = 3;
		if (getHealth().get() <= 0) {
			GameTaskManager.clearBackgroundTasks();
			MainApp.sceneHolder.switchScene(new MainMenuScene());
		}
	}
}

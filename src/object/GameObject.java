package object;

import animation.AnimationClip;
import constants.ImageHolder;
import gui.GameCanvas;
import interfaces.IBehaviour;
import interfaces.IRenderable;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import logic.CollisionSystem;
import logic.GameObjectTag;
import utility.Utility;

public abstract class GameObject implements IBehaviour, IRenderable {
	//fields
	
	//config
	private boolean isRenderDebug = true;
	
	//status
	private boolean isStatic;
	private boolean isDestroyed;
	private GameObjectTag tag = new GameObjectTag();

	//visual
	private Image renderSprite = ImageHolder.getInstance().nothing;
	private AnimationClip clip;
	private Point2D position = new Point2D(0, 0);
	private Rotate rotation = new Rotate(0);
	private Point2D scale = new Point2D(1, 1);
	private Point2D pivot = new Point2D(0, 0);
	private int zOrder = 0;
	public CollisionSystem collisionSystem = new CollisionSystem(this);
	
	//constructor
	public GameObject() { }
	
	//general functions
	public Point2D getScaledSize() {
		return GameCanvas.scaledPoint2D(new Point2D(getRenderSprite().getWidth(), getRenderSprite().getHeight()));
	}
	public void renderOver(GameCanvas canvas) {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		double scaledWidth = getRenderSprite().getWidth() * getScale().getX();
		double scaledHeight = getRenderSprite().getHeight() * getScale().getY();
		
		Point2D pixeledPivot = Utility.timesAxis(GameCanvas.pixeledPoint2D(getPivot()), getScale());
		Point2D pixeledPosition = GameCanvas.pixeledPoint2D(getPosition());
		
		gc.translate(pixeledPosition.getX(), pixeledPosition.getY());
		gc.rotate(getRotation().getAngle());
		gc.drawImage(getRenderSprite(), -pixeledPivot.getX(), -pixeledPivot.getY(), scaledWidth, scaledHeight);
		gc.rotate(-getRotation().getAngle());
		gc.translate(-pixeledPosition.getX(), -pixeledPosition.getY());
	}
	public void renderPivot(GameCanvas canvas) {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		Point2D pixeledPosition = GameCanvas.pixeledPoint2D(getPosition());
		
		gc.translate(pixeledPosition.getX(), pixeledPosition.getY());
		gc.setFill(Color.LIME);
		gc.fillRect(-3, -3, 6, 6);
		gc.translate(-pixeledPosition.getX(), -pixeledPosition.getY());
	}
	
	public void destroy() { 
		this.isDestroyed  = true;
	}

	//getter setter
	public Image getRenderSprite() {
		return renderSprite;
	}
	public void setRenderSprite(Image renderSprite) {
		this.renderSprite = renderSprite;
	}
	public void setAnimationClip(AnimationClip clip) {
		this.clip = clip;
		setRenderSprite(clip.getSpriteList().get(clip.getFrameIndex()));
		clip.setBindGameObject(this);
	}
	public AnimationClip getAnimationClip() {
		return this.clip;
	}
	public Point2D getPosition() {
		return position;
	}
	public void setPosition(Point2D position) {
		this.position = position;
	}
	public Point2D getScale() {
		return scale;
	}
	public void setScale(Point2D scale) {
		this.scale = scale;
	}
	public Rotate getRotation() {
		return rotation;
	}
	public void setRotation(Rotate rotation) {
		this.rotation = rotation;
	}
	public int getZOrder() {
		return zOrder;
	}
	public Point2D getPivot() {
		return pivot;
	}
	public void setPivot(Point2D pivot) {
		Point2D offset = pivot.subtract(getPivot());
		setPosition(getPosition().add(offset));
		getCollisionSystem().translateAll(offset);
		this.pivot = pivot;
	}	
	public void setZOrder(int zOrder) {
		this.zOrder = zOrder;
	}
	public CollisionSystem getCollisionSystem() { 
		return this.collisionSystem; 
	}
	public GameObjectTag getTag() { 
		return this.tag; 
	}
	public boolean isDestroyed() { 
		return this.isDestroyed; 
	}
	public boolean isStatic() { 
		return this.isStatic; 
	}
	public void setStatic(boolean isStatic) { 
		this.isStatic = isStatic; 
	}
	public boolean isRenderDebug() { 
		return this.isRenderDebug; 
	}
	public void setRenderDebug(boolean render) { 
		this.isRenderDebug = render; 
	}
	public void renderDebug(GameCanvas gameCanvas) {
		if(!isRenderDebug()) return;
		getCollisionSystem().renderOver(gameCanvas);
		renderPivot(gameCanvas);
	}
}

package object;

import javafx.geometry.BoundingBox;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import utility.Utility;
import animation.AnimationClip;
import gui.GameCanvas;
import interfaces.IBehaviour;
import interfaces.IRenderable;

public abstract class GameObject implements IBehaviour, IRenderable {
	//fields
	public boolean isStatic;

	private Image renderSprite;
	private AnimationClip clip;
	private Point2D position = new Point2D(0, 0);
	private Rotate rotation = new Rotate(0);
	private Point2D scale = new Point2D(1, 1);
	private Point2D pivot = new Point2D(0, 0);
	private int zOrder = 0;
	
	//constructor
	public GameObject() { }

	//fields getter-setter method
	public Image getRenderSprite() {
		return renderSprite;
	}
	public void setRenderSprite(Image renderSprite) {
		this.renderSprite = renderSprite;
	}
	public void setAnimationClip(AnimationClip clip) {
		this.clip = clip;
		this.renderSprite = clip.getSpriteList().get(0);
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
		this.pivot = pivot;
	}	
	public void setZOrder(int zOrder) {
		this.zOrder = zOrder;
	}
	
	//general functions
	public Point2D getScaledSize() {
		return new Point2D(getRenderSprite().getWidth(), getRenderSprite().getHeight()).multiply(1.0/GameCanvas.PIXEL_CELLSIZE);
	}
	public boolean overlapped(Point2D point) {
		return noRotatedBound().contains(Utility.rotatePoint2D(point.subtract(getPosition()), -getRotation().getAngle()).add(getPosition()));
	}
	public boolean intersects(GameObject other, GraphicsContext gg) {
		return this.cornerOverlapped(other) || other.cornerOverlapped(this);
	}
	public void renderOver(GameCanvas canvas) {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		double scaledWidth = getRenderSprite().getWidth() * getScale().getX();
		double scaledHeight = getRenderSprite().getHeight() * getScale().getY();
		
		Point2D pixeledPivot = GameCanvas.pixeledPoint2D(getPivot());
		Point2D pixeledPosition = GameCanvas.pixeledPoint2D(getPosition());
		
		gc.translate(pixeledPosition.getX(), pixeledPosition.getY());
		gc.rotate(getRotation().getAngle());
		gc.drawImage(getRenderSprite(), -pixeledPivot.getX(), -pixeledPivot.getY(), scaledWidth, scaledHeight);
		gc.rotate(-getRotation().getAngle());
		gc.translate(-pixeledPosition.getX(), -pixeledPosition.getY());
	}
	private BoundingBox noRotatedBound() {
		Point2D topLeft = position.subtract(pivot);
		return new BoundingBox(topLeft.getX(), topLeft.getY(), 
				getScaledSize().getX()*getScale().getX(), getScaledSize().getY()*getScale().getY());
	}
	private boolean cornerOverlapped(GameObject other) {
		BoundingBox bound = noRotatedBound();
		
		Point2D p1 = new Point2D(bound.getMinX(), bound.getMinY());
		Point2D p2 = new Point2D(bound.getMaxX(), bound.getMinY());
		Point2D p3 = new Point2D(bound.getMinX(), bound.getMaxY());
		Point2D p4 = new Point2D(bound.getMaxX(), bound.getMaxY());
		
		p1 = Utility.rotatePoint2D(p1.subtract(getPosition()), getRotation().getAngle()).add(getPosition());
		p2 = Utility.rotatePoint2D(p2.subtract(getPosition()), getRotation().getAngle()).add(getPosition());
		p3 = Utility.rotatePoint2D(p3.subtract(getPosition()), getRotation().getAngle()).add(getPosition());
		p4 = Utility.rotatePoint2D(p4.subtract(getPosition()), getRotation().getAngle()).add(getPosition());
		
		return other.overlapped(p1) || other.overlapped(p2) || other.overlapped(p3) || other.overlapped(p4);
	}
}

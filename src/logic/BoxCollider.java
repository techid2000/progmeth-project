package logic;

import java.util.ArrayList;
import java.util.List;

import constants.SystemCache;
import gui.GameCanvas;
import javafx.geometry.BoundingBox;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import object.GameObject;
import utility.Utility;

public class BoxCollider extends BoundingBox {

	public GameObject gameObject;
	
	public BoxCollider(double minX, double minY, double width, double height, GameObject gameObject) {
		super(minX, minY, width, height);
		this.gameObject = gameObject;
	}
	
	public boolean boxContains(Point2D point) {
		return getGlobalBoundingBox().contains(Utility.rotatePoint2D(point.subtract(gameObject.getPosition()),-gameObject.getRotation().getAngle()).add(gameObject.getPosition()));
	}
	
	public boolean intersects(BoxCollider other) {
		return this.cornerInside(other) || other.cornerInside(this);
	}
	
	private BoundingBox getGlobalBoundingBox() {
		Point2D topLeft = Utility.timesAxis(new Point2D(getMinX(), getMinY()), gameObject.getScale()).add(gameObject.getPosition());
		Point2D widthHeight = Utility.timesAxis(new Point2D(getWidth(), getHeight()), gameObject.getScale());
		return new BoundingBox(topLeft.getX(),topLeft.getY(),widthHeight.getX(),widthHeight.getY());
	}
	
	private boolean cornerInside(BoxCollider other) {
		BoundingBox globally = getGlobalBoundingBox();
		
		Point2D p1 = new Point2D(globally.getMinX(), globally.getMinY());
		Point2D p2 = new Point2D(globally.getMaxX(), globally.getMinY());
		Point2D p3 = new Point2D(globally.getMinX(), globally.getMaxY());
		Point2D p4 = new Point2D(globally.getMaxX(), globally.getMaxY());
		
		p1 = Utility.rotatePoint2D(p1.subtract(gameObject.getPosition()), gameObject.getRotation().getAngle()).add(gameObject.getPosition());
		p2 = Utility.rotatePoint2D(p2.subtract(gameObject.getPosition()), gameObject.getRotation().getAngle()).add(gameObject.getPosition());
		p3 = Utility.rotatePoint2D(p3.subtract(gameObject.getPosition()), gameObject.getRotation().getAngle()).add(gameObject.getPosition());
		p4 = Utility.rotatePoint2D(p4.subtract(gameObject.getPosition()), gameObject.getRotation().getAngle()).add(gameObject.getPosition());
		
		return other.boxContains(p1) || other.boxContains(p2) || other.boxContains(p3) || other.boxContains(p4);
	}
	
	public static BoxCollider getDefaultBox(GameObject gameObject) {
		return new BoxCollider(-gameObject.getPivot().getX(),
				-gameObject.getPivot().getY(),
				gameObject.getScaledSize().getX(),
				gameObject.getScaledSize().getY(),
				gameObject);
	}
	
	public List<BoxCollider> getIntersectedCollider(long tagBitmask) {
		List<BoxCollider> colliders = new ArrayList<BoxCollider>();
		for(GameObject object : SystemCache.getInstance().gameCanvas.getGameObjects()) {
			if(object == gameObject) continue;
			if(!(object.getTag().contains(tagBitmask))) continue;
			
			if(object.getCollisionSystem().isDefaultAvailable()) {
				BoxCollider defaultOfObject = BoxCollider.getDefaultBox(object);
				if(intersects(defaultOfObject))
					colliders.add(defaultOfObject);
			}
			for(BoxCollider collider : object.getCollisionSystem().getBoxColliders()) {
				if(intersects(collider)) {
					colliders.add(collider);
				}
			}
		}
		return colliders;
	}
	
	public void renderOver(GameCanvas canvas, Color renderColor) {
		GraphicsContext gc = canvas.getGraphicsContext2D();

		Point2D pixeledPosition = GameCanvas.pixeledPoint2D(gameObject.getPosition());
		
		gc.translate(pixeledPosition.getX(), pixeledPosition.getY());
		gc.rotate(gameObject.getRotation().getAngle());
		
		gc.setStroke(renderColor);
		Point2D topLeft = GameCanvas.pixeledPoint2D(Utility.timesAxis(new Point2D(getMinX(),getMinY()),gameObject.getScale()));
		Point2D widthHeight = GameCanvas.pixeledPoint2D(Utility.timesAxis(new Point2D(getWidth(),getHeight()), gameObject.getScale()));
		gc.strokeRect(topLeft.getX(), topLeft.getY(), widthHeight.getX(), widthHeight.getY());
		
		gc.rotate(-gameObject.getRotation().getAngle());
		gc.translate(-pixeledPosition.getX(), -pixeledPosition.getY());
	}
}

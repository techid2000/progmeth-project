package logic;

import java.util.ArrayList;
import java.util.List;

import gui.GameCanvas;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import object.GameObject;

public class CollisionSystem {
	
	private GameObject gameObject;
	private boolean defaultAvailable;
	
	public List<BoxCollider> boxColliders;
	
	public CollisionSystem(GameObject gameObject) {
		this.gameObject = gameObject;
		boxColliders = new ArrayList<BoxCollider>();
		setDefaultAvailable(true);
	}
	
	public List<BoxCollider> getBoxColliders() { return this.boxColliders; }
	
	public void setDefaultAvailable(boolean available) { defaultAvailable = available; }
	
	public boolean isDefaultAvailable() { return defaultAvailable; }

	public BoxCollider getCollider(int index) { return boxColliders.get(index); }

	public void addBoxCollider(double minX, double minY, double width, double height) {
		getBoxColliders().add(new BoxCollider(minX, minY, width, height, gameObject));
	}
	

	public void renderOver(GameCanvas canvas) {
		if(isDefaultAvailable())
			BoxCollider.getDefaultBox(gameObject).renderOver(canvas, Color.CYAN);
		for(BoxCollider collider : boxColliders) {
			collider.renderOver(canvas, Color.ORANGE);
		}
	}
	
	public void translateAll(Point2D offset) {
		for(int i = 0; i < getBoxColliders().size(); i++) {
			BoxCollider collider = getBoxColliders().get(i);
			Point2D topLeft = new Point2D(collider.getMinX(), collider.getMinY()).subtract(offset);
			getBoxColliders().set(i,new BoxCollider(topLeft.getX(), topLeft.getY(), collider.getWidth(), collider.getHeight(), gameObject));
		}
	}
}

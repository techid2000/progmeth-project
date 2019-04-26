package object;

import java.util.ArrayList;
import java.util.List;

import gui.GameCanvas;

public class ColliderSystem {
	
	private GameObject gameObject;
	
	public List<BoxCollider> boxColliders;
	
	public ColliderSystem(GameObject gameObject) {
		this.gameObject = gameObject;
		boxColliders = new ArrayList<BoxCollider>();
		boxColliders.add(BoxCollider.getDefaultBox(gameObject));
	}
	
	public List<BoxCollider> getBoxColliders() { return this.boxColliders; }
	public BoxCollider getCollider(int index) { return boxColliders.get(index); }

	public void renderOver(GameCanvas canvas) {
		for(BoxCollider collider : boxColliders) {
			collider.renderOver(canvas);
		}
	}
}

package scene;

import gui.MainMenuGUI;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class MainMenuScene extends Scene {
	
	private MainMenuScene(Parent root) {
		super(root);
	}
	public MainMenuScene() {
		this(new Group());
		setRoot(new MainMenuGUI());
	}
}

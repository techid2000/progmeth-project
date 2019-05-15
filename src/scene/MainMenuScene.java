package scene;

import gui.MainMenuGUI;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class MainMenuScene extends Scene {
	
	private MainMenuScene(Parent root) {
		super(root);
		// TODO Auto-generated constructor stub
	}
	public MainMenuScene() {
		this(new Group());
		setRoot(new MainMenuGUI());
	}
}

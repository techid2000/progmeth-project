package scene;

import app.MainApp;
import gui.LoadingGUI;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class LoadingScene extends Scene {

	private LoadingGUI loadingGUI;

	private LoadingScene(Parent root) {
		super(root);
	}

	public LoadingScene() {
		this(new Group());
		initialize();
	}

	private void initialize() {
		setRoot(loadingGUI = new LoadingGUI());
		loadingGUI.setPrefSize(MainApp.WINDOW_WIDTH, MainApp.WINDOW_HEIGHT);
	}
}
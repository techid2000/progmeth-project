package scene;

import app.MainApp;
import gui.GameCanvas;
import gui.GameUI;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class GameScene extends Scene {
	private StackPane stackPane;
	private GameCanvas gameGUI;

	private GameScene(Parent root) {
		super(root);
	}

	public GameScene() {
		this(new StackPane());
		initialize();
	}
	
	private void initialize() {
		setRoot(stackPane = new StackPane());
		stackPane.setPrefSize(MainApp.WINDOW_WIDTH, MainApp.WINDOW_HEIGHT);
		stackPane.getChildren().addAll(new GameCanvas(), new GameUI());
	}
}

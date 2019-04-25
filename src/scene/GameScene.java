package scene;

import app.MainApp;
import constants.SystemCache;
import event.GameEvent;
import gui.GameCanvas;
import gui.GameUI;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

public class GameScene extends Scene {
	private StackPane stackPane;
	private GameCanvas gameCanvas;
	private GameEvent gameEvent;
	
	private GameScene(Parent root) {
		super(root);
	}

	public GameScene() {
		this(new StackPane());
		initialize();
	}
	
	private void initialize() {
		setCursor(Cursor.NONE);
		setRoot(stackPane = new StackPane());
		stackPane.setPrefSize(MainApp.WINDOW_WIDTH, MainApp.WINDOW_HEIGHT);
		stackPane.getChildren().addAll(new GameCanvas(), new GameUI());
		setGameEvent(new GameEvent(this));
	}
	
	public GameEvent getGameEvent() {
		return this.gameEvent;
	}
	private void setGameEvent(GameEvent gameEvent) {
		this.gameEvent = gameEvent;
	}
}

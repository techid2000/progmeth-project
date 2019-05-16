package scene;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneHolder {
	
	public Stage bindStage;
	
	public MainMenuScene mainMenuScene;
	public GameScene gameScene;
	
	public SceneHolder(Stage bindStage) {
		this.bindStage = bindStage;
	}
	
	public void initialize() {
//		this.mainMenuScene = new MainMenuScene();
		this.gameScene = new GameScene();
		switchScene(gameScene);
	}
	
	private void switchScene(Scene nextScene) {
		bindStage.setResizable(false);
		bindStage.setScene(nextScene);
		bindStage.sizeToScene();
	}
}

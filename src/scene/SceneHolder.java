package scene;

import constants.SystemCache;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneHolder {
	
	public Stage bindStage;
	
	public MainMenuScene mainMenuScene;
	public GameScene gameScene;
	public LoadingScene loadingScene;
	
	public SceneHolder(Stage bindStage) {
		this.bindStage = bindStage;
		SystemCache.getInstance().sceneHolder = this;
	}
	
	public void initialize() {
////		this.mainMenuScene = new MainMenuScene();
//		this.gameScene = new GameScene();
		this.loadingScene = new LoadingScene();
		switchScene(loadingScene);
	}
	
	public void switchScene(Scene nextScene) {
		bindStage.setResizable(false);
		bindStage.setScene(nextScene);
		bindStage.sizeToScene();
	}
}

package scene;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneHolder {
	
	private Stage bindStage;
	
	public LoadingScene loadingScene;
	
	public SceneHolder(Stage bindStage) {
		this.bindStage = bindStage;
	}
	
	public void initialize() {
		this.loadingScene = new LoadingScene();
		switchScene(loadingScene);
	}
	
	public void switchScene(Scene nextScene) {
		bindStage.setResizable(false);
		bindStage.setScene(nextScene);
		bindStage.sizeToScene();
	}
}

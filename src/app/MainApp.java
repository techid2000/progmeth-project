package app;

import javafx.application.Application;
import javafx.stage.Stage;
import scene.SceneHolder;

public class MainApp extends Application {
	public static final int WINDOW_WIDTH = 1024;
	public static final int WINDOW_HEIGHT = 768;
	
	public static SceneHolder sceneHolder;
	@Override
	public void start(Stage primaryStage) throws Exception {
		sceneHolder = new SceneHolder(primaryStage);
		sceneHolder.initialize();
		primaryStage.setTitle("pre-alpha 0.4.1");
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}

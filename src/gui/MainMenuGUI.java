package gui;

import app.MainApp;
import constants.SystemCache;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class MainMenuGUI extends StackPane{
	private Pane backgroundVideoPane;
	
	public MainMenuGUI() {
//		SystemCache.getInstance().mainMenuGUI = this;
		
		setPrefSize(MainApp.WINDOW_WIDTH, MainApp.WINDOW_HEIGHT);
		Media media = new Media(ClassLoader.getSystemResource("video/test.mp4").toString());
		
		MediaPlayer mediaPlayer = new MediaPlayer(media);
		MediaView mediaView = new MediaView(mediaPlayer);
		
		BoxBlur bb = new BoxBlur();
		bb.setHeight(10);
		bb.setWidth(10);
		bb.setIterations(3);
		
		mediaView.setEffect(bb);
		
		mediaPlayer.setAutoPlay(true);
		mediaPlayer.setVolume(0.03);
		mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
		
		backgroundVideoPane = new Pane();
		backgroundVideoPane.getChildren().add(mediaView);
		this.getChildren().add(backgroundVideoPane);
	}
}

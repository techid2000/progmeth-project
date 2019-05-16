package gui;

import app.MainApp;
import constants.ImageHolder;
import constants.SystemCache;
import javafx.geometry.Pos;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import scene.GameScene;
import scene.MainMenuScene;

public class MainMenuGUI extends StackPane{
	private Pane backgroundVideoPane;
	private VBox menuVBox;
	
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
		mediaPlayer.setVolume(0.5);
		mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
		
		backgroundVideoPane = new Pane();
		backgroundVideoPane.getChildren().add(mediaView);
		this.getChildren().add(backgroundVideoPane);
		
		menuVBox = new VBox();
		ImageView play = new ImageView(ImageHolder.getInstance().playButton);
		ImageView quit = new ImageView(ImageHolder.getInstance().quitButton);
		play.setOnMouseClicked(e->{
			mediaPlayer.stop();
			SystemCache.getInstance().sceneHolder.switchScene(SystemCache.getInstance().sceneHolder.gameScene = new GameScene());
		});
		play.setOnMouseEntered(e->{
			play.setImage(ImageHolder.getInstance().playButtonHighlight);
		});
		play.setOnMouseExited(e->{
			play.setImage(ImageHolder.getInstance().playButton);
		});
		
		quit.setOnMouseClicked(e->{
			System.exit(0);
		});
		quit.setOnMouseEntered(e->{
			quit.setImage(ImageHolder.getInstance().quitButtonHighlight);
		});
		quit.setOnMouseExited(e->{
			quit.setImage(ImageHolder.getInstance().quitButton);
		});
		menuVBox.getChildren().addAll(play,quit);
		menuVBox.setAlignment(Pos.CENTER);
		menuVBox.setSpacing(20);
		this.getChildren().add(menuVBox);
		
	}
}

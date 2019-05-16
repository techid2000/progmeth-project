package gui;

import app.MainApp;
import constants.ImageHolder;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import scene.GameScene;

public class MainMenuGUI extends StackPane {
	private Pane backgroundVideoPane;
	private HBox menuHBox;
	private BorderPane menuBorderPane;

	public MainMenuGUI() {

		setPrefSize(MainApp.WINDOW_WIDTH, MainApp.WINDOW_HEIGHT);
		Media media = new Media(ClassLoader.getSystemResource("video/mainmenu_video.mp4").toString());

		MediaPlayer mediaPlayer = new MediaPlayer(media);
		MediaView mediaView = new MediaView(mediaPlayer);

		BoxBlur boxBlur = new BoxBlur();
		boxBlur.setHeight(10);
		boxBlur.setWidth(10);
		boxBlur.setIterations(3);

		mediaView.setEffect(boxBlur);

		mediaPlayer.setAutoPlay(true);
		mediaPlayer.setVolume(0.3);
		mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);

		backgroundVideoPane = new Pane();
		backgroundVideoPane.getChildren().add(mediaView);
		this.getChildren().add(backgroundVideoPane);

		menuHBox = new HBox();
		ImageView play = new ImageView(ImageHolder.getInstance().playButton);
		ImageView quit = new ImageView(ImageHolder.getInstance().quitButton);
		play.setOnMouseClicked(e -> {
			mediaPlayer.stop();
			MainApp.sceneHolder.switchScene(new GameScene());
		});
		play.setOnMouseEntered(e -> {
			play.setImage(ImageHolder.getInstance().playButtonHighlight);
		});
		play.setOnMouseExited(e -> {
			play.setImage(ImageHolder.getInstance().playButton);
		});

		quit.setOnMouseClicked(e -> {
			System.exit(0);
		});
		quit.setOnMouseEntered(e -> {
			quit.setImage(ImageHolder.getInstance().quitButtonHighlight);
		});
		quit.setOnMouseExited(e -> {
			quit.setImage(ImageHolder.getInstance().quitButton);
		});

		menuHBox.getChildren().addAll(play, quit);
		menuHBox.setAlignment(Pos.BOTTOM_CENTER);
		menuHBox.setSpacing(20);
		menuHBox.setPadding(new Insets(0, 0, 30, 0));
		menuBorderPane = new BorderPane();
		menuBorderPane.setBottom(menuHBox);

		this.getChildren().add(menuBorderPane);

	}
}

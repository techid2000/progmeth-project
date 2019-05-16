package gui;

import app.MainApp;
import constants.FontHolder;
import constants.ImageHolder;
import constants.SystemCache;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class GameUI extends StackPane {
	
	private BorderPane UIBorderPane;
	private Label scoreLabel;
	private Label cashLabel;
	private ImageView cashIcon;
	
	private Label waveLabel;
	
	public GameUI() {
		SystemCache.getInstance().gameUI = this;
		
		this.setMouseTransparent(true);
		this.setWidth(MainApp.WINDOW_WIDTH);
		this.setHeight(MainApp.WINDOW_HEIGHT);
		this.setPadding(new Insets(10));
		
		scoreLabel = new Label("Score 000000");
		cashLabel = new Label("0");
		cashIcon = new ImageView(ImageHolder.getInstance().coinPile1);
		
		scoreLabel.setFont(FontHolder.getInstance().font36);
		cashLabel.setFont(FontHolder.getInstance().font36);
		scoreLabel.setTextFill(Color.WHITE);
		cashLabel.setTextFill(Color.WHITE);
		BorderPane top = new BorderPane();
		top.setLeft(scoreLabel);
		top.setRight(new Button("Enermy Info"));
		
		BorderPane bottom = new BorderPane();
		HBox hbox = new HBox(cashIcon, cashLabel);
		hbox.setSpacing(15);
		hbox.setAlignment(Pos.BOTTOM_LEFT);
		bottom.setLeft(hbox);
		bottom.setRight(new AccessoriesUI());

		waveLabel = new Label("Wave 15!");
		waveLabel.setTextFill(Color.RED);
		waveLabel.setEffect(new DropShadow());
		waveLabel.setOpacity(0);
		waveLabel.setFont(FontHolder.getInstance().font64);
		
		this.UIBorderPane = new BorderPane();
		this.UIBorderPane.setTop(top);
		this.UIBorderPane.setBottom(bottom);
		
		getChildren().addAll(UIBorderPane, waveLabel);
	}
	
	public void setScore(int score) {
		scoreLabel.setText(String.format("Score 06%d", score));
	}
	
	public void setCash(int cash) {
		cashLabel.setText(String.format("%d", cash));
	}
	
	public void notEnoughMoneyAlert() {
		Timeline redTextAlert = new Timeline(
			new KeyFrame(new Duration(100), e -> cashLabel.setTextFill(Color.RED)),
			new KeyFrame(new Duration(200), e -> cashLabel.setTextFill(Color.WHITE)),
			new KeyFrame(new Duration(300), e -> cashLabel.setTextFill(Color.RED)),
			new KeyFrame(new Duration(400), e -> cashLabel.setTextFill(Color.WHITE)),
			new KeyFrame(new Duration(500), e -> cashLabel.setTextFill(Color.RED)),
			new KeyFrame(new Duration(600), e -> cashLabel.setTextFill(Color.WHITE))
		);
		redTextAlert.play();
	}
	
	public void waveAlert() {
		waveLabel.setText(String.format("Wave %d!", SystemCache.getInstance().gameCanvas.waveSystem.waveCount));
		Timeline stomp = new Timeline(
				new KeyFrame(new Duration(1), new KeyValue(waveLabel.opacityProperty(), 1)),
				new KeyFrame(new Duration(0), new KeyValue(waveLabel.scaleXProperty(), 5)),
				new KeyFrame(new Duration(0), new KeyValue(waveLabel.scaleYProperty(), 5)),
				new KeyFrame(new Duration(200), new KeyValue(waveLabel.scaleXProperty(), 1, Interpolator.EASE_IN)),
				new KeyFrame(new Duration(200), new KeyValue(waveLabel.scaleYProperty(), 1, Interpolator.EASE_IN)),
				new KeyFrame(new Duration(1500), (e) -> {
					Timeline fade = new Timeline(
						new KeyFrame(new Duration(0), new KeyValue(waveLabel.opacityProperty(), 1)),
						new KeyFrame(new Duration(1000), new KeyValue(waveLabel.opacityProperty(), 0))
					);
					fade.play();
				})
		);
		stomp.play();
	}
}

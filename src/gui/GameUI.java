package gui;

import app.MainApp;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class GameUI extends BorderPane{
	public GameUI() {
		this.setWidth(MainApp.WINDOW_WIDTH);
		this.setHeight(MainApp.WINDOW_HEIGHT);
		this.setPadding(new Insets(10));
		
		BorderPane topBP = new BorderPane();
		topBP.setLeft(new Button("Score"));
		topBP.setCenter(new Button("Pause"));
		topBP.setRight(new Button("Enermy Info"));
		
		FlowPane fp = new FlowPane();
		fp.setAlignment(Pos.BOTTOM_RIGHT);
		fp.getChildren().add(new Button("Weapon Info"));
		this.setTop(topBP);
		this.setBottom(fp);
	}
}

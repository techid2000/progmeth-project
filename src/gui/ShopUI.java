package gui;

import constants.FontHolder;
import constants.SystemCache;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class ShopUI extends VBox {
	public boolean showing;
	public ShopUI() {
		SystemCache.getInstance().shopUI = this;
		setVisible(false);
		showing = false;
		setAlignment(Pos.CENTER);
		setBackground(new Background(new BackgroundFill(Color.AQUA, CornerRadii.EMPTY, Insets.EMPTY)));
		Label header = new Label("Shop");
		header.setFont(FontHolder.getInstance().font36);
		HBox gunSlots = new HBox();
		gunSlots.setAlignment(Pos.CENTER);
		getChildren().add(header);
		getChildren().add(gunSlots);
		setPadding(new Insets(20));
		gunSlots.setSpacing(10);
		for(int i=1; i<=4; i++) {
			Button btn = new Button("1");
			btn.setPrefSize(160, 200);
			gunSlots.getChildren().add(btn);
		}
	}
	
	public void show() {
		showing = true;
		SystemCache.getInstance().gameCanvas.pause();
		setCursor(Cursor.DEFAULT);
		setVisible(true);
	}
	
	public void hide() {
		showing = false;
		SystemCache.getInstance().gameCanvas.resume();
		setVisible(false);
	}
	
	public void toggle() {
		if(showing) { hide(); } else { show(); } 
	}
}

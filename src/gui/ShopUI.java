package gui;

import constants.SystemCache;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class ShopUI extends VBox {
	public ShopUI() {
		SystemCache.getInstance().shopUI = this;
		setAlignment(Pos.CENTER);
		setBackground(new Background(new BackgroundFill(Color.AQUA, CornerRadii.EMPTY, Insets.EMPTY)));
		Label header = new Label("Shop");
		HBox gunSlots = new HBox();
		gunSlots.setAlignment(Pos.CENTER);
		getChildren().add(header);
		getChildren().add(gunSlots);
		setPadding(new Insets(20));
		for(int i=1; i<=3; i++) {
			Button btn = new Button("1");
			btn.setPrefSize(160, 200);
			gunSlots.getChildren().add(btn);
		}
	}
}

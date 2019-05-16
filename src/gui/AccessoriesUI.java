package gui;

import constants.FontHolder;
import constants.SystemCache;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import logic.Accessories;
import object.weapon.gun.Gun;
import object.weapon.gun.Pistol;

public class AccessoriesUI extends HBox {
	public GunsBox gunsBox;
	public AccessoriesUI() {
		gunsBox = new GunsBox();
		getChildren().add(gunsBox);
		SystemCache.getInstance().accessoriesUI = this;
	}
	
	public void updateInfo(Accessories accessories) {
		Gun current = accessories.getCurrentGun();
		Gun notCurrent = accessories.gunA;
		if(notCurrent == current)
			notCurrent = accessories.gunB;
		gunsBox.updateInfo(current, notCurrent);
	}
}

class GunsBox extends VBox {
	public ImageView notCurrentGunImage;
	public ImageView currentGunImage;
	public Label roundLabel;
	public Label ammoLabel;
	
	public GunsBox() {
		setSpacing(10);
		notCurrentGunImage = new ImageView();
		currentGunImage = new ImageView();
		roundLabel = new Label();
		ammoLabel = new Label();
		notCurrentGunImage.setOpacity(0.2);
		roundLabel.setFont(FontHolder.getInstance().font28);
		roundLabel.setTextFill(Color.WHITE);
		ammoLabel.setFont(FontHolder.getInstance().font28);
		ammoLabel.setTextFill(Color.WHITE);
		ammoLabel.setOpacity(0.5);
		HBox labelBox = new HBox(roundLabel,ammoLabel);
		labelBox.setAlignment(Pos.CENTER);
		Label BToShop = new Label("[TAB] Shop");
		BToShop.setFont(FontHolder.getInstance().font18);
		BToShop.setTextFill(Color.WHITE);
		BToShop.setOpacity(0.7);
		getChildren().addAll(BToShop, notCurrentGunImage, currentGunImage, labelBox);
		setAlignment(Pos.CENTER_RIGHT);
	}
	
	public void updateInfo(Gun current, Gun notCurrent) {
		currentGunImage.setImage(current.getRenderSprite());
		if(notCurrent != null) 
			notCurrentGunImage.setImage(notCurrent.getRenderSprite());
		roundLabel.setText(String.format("%d",current.getRound()));
		ammoLabel.setText(String.format("/%s", (current instanceof Pistol) ? "Unlimit" : current.getAmmo()));
	}
}
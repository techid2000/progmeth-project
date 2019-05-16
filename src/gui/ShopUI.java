package gui;

import constants.FontHolder;
import constants.ImageHolder;
import constants.SystemCache;
import gui.GunSlot.Status;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import object.weapon.gun.AssultRifle;
import object.weapon.gun.Shotgun;
import object.weapon.gun.SniperRifle;

public class ShopUI extends StackPane {
	private boolean showing;
	private VBox shopVBox;
	
	public GunSlot gunSlotA;
	public GunSlot gunSlotB;
	
	public ShopUI() {

		//then you set to your node
		SystemCache.getInstance().shopUI = this;
		shopVBox = new VBox();
		setVisible(false);
		showing = false;
		shopVBox.setAlignment(Pos.CENTER);
		Label header = new Label("Shop");
		header.setTextFill(Color.WHITE);
		header.setEffect(new DropShadow());
		header.setFont(FontHolder.getInstance().font64);
		HBox gunSlots = new HBox();
		gunSlots.setAlignment(Pos.CENTER);
		shopVBox.getChildren().add(header);
		shopVBox.getChildren().add(gunSlots);
		shopVBox.setPadding(new Insets(20));
		shopVBox.setSpacing(10);
		gunSlots.setSpacing(10);
		
		GunSlot pistol = new GunSlot(SystemCache.getInstance().player.getAccessories().gunA);
		pistol.updateStatus(Status.EQUIPPED);
		gunSlotA = pistol;
		
		gunSlots.getChildren().addAll(
			pistol,
			new GunSlot(new Shotgun()),
			new GunSlot(new AssultRifle()),
			new GunSlot(new SniperRifle())
		);
		ImageView bg = new ImageView(ImageHolder.getInstance().shopMenu);
		bg.setFitWidth(740);
		bg.setFitHeight(350);
		getChildren().add(bg);
		getChildren().add(shopVBox);
	}
	
	public void show() {
		if(showing) return;
		if(gunSlotA != null) gunSlotA.updateGunInfo();
		if(gunSlotB != null) gunSlotB.updateGunInfo();
		showing = true;
		SystemCache.getInstance().gameCanvas.pause();
		setCursor(Cursor.DEFAULT);
		setVisible(true);
	}
	
	public void hide() {
		if(!showing) return;
		showing = false;
		SystemCache.getInstance().gameCanvas.resume();
		setVisible(false);
	}
	
	public void toggle() {
		if(showing) { hide(); } else { show(); } 
	}
}

package gui;

import constants.FontHolder;
import constants.ImageHolder;
import constants.SystemCache;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import logic.Accessories;
import object.weapon.gun.AssultRifle;
import object.weapon.gun.Gun;
import object.weapon.gun.Pistol;
import object.weapon.gun.Shotgun;
import object.weapon.gun.SniperRifle;

public class GunSlot extends StackPane {

	private ImageView slotBackground;
	private Label gunName;

	private VBox topComponents;
	
	private StackPane bottomComponents;
	private HBox sellComponents;
	private VBox soldComponents; 
	private Label roundAmmoLabel;
	
	private ImageView gunImageView;
	private Gun gun;
	
	public GunSlot(Gun gun) {
		this.gun = gun;
		slotBackground = new ImageView(ImageHolder.getInstance().gunSlot);
		
		gunName = new Label();
		gunName.setFont(FontHolder.getInstance().font18);		
		gunImageView = new ImageView(gun.getRenderSprite());
		topComponents = new VBox(gunName, gunImageView);
		topComponents.setSpacing(20);
		topComponents.setAlignment(Pos.CENTER);
		topComponents.setPadding(new Insets(25,0,0,0));
		
		Label gunPriceLabel = new Label(Integer.toString(gun.getPrice()));
		ImageView coinIcon = new ImageView(ImageHolder.getInstance().singleCoin.get(0));
		gunPriceLabel.setFont(FontHolder.getInstance().font28);
		coinIcon.setFitWidth(24);
		coinIcon.setFitHeight(24);
		sellComponents = new HBox(coinIcon, gunPriceLabel);
		sellComponents.setPadding(new Insets(0,0,20,0));
		sellComponents.setSpacing(10);
		sellComponents.setAlignment(Pos.CENTER);
		
		ImageView coinBulletIcon = new ImageView(ImageHolder.getInstance().coinWithBullet);
		coinBulletIcon.setFitWidth(32);
		coinBulletIcon.setFitHeight(24);
		Label ammoPriceLabel = new Label(Integer.toString(gun.getAmmoPrice()));
		ammoPriceLabel.setFont(FontHolder.getInstance().font28);
		roundAmmoLabel = new Label(String.format("%d/%s",gun.getRound(),gun.getAmmo()));
		roundAmmoLabel.setFont(FontHolder.getInstance().font18);
		soldComponents = new VBox();
		soldComponents.setPadding(new Insets(0,0,20,0));
		soldComponents.setSpacing(10);
		HBox line1 = new HBox(roundAmmoLabel);
		HBox line2 = new HBox(coinBulletIcon, ammoPriceLabel);
		line1.setAlignment(Pos.CENTER);
		line2.setAlignment(Pos.CENTER);
		line2.setSpacing(10);
		soldComponents.getChildren().addAll(line1, line2);
		
		bottomComponents = new StackPane(sellComponents, soldComponents);
		
		getChildren().addAll(slotBackground, new BorderPane(null,topComponents,null,bottomComponents,null));
		
		if(gun instanceof Pistol) {
			gunName.setText("Pistol");
			line2.setVisible(false);
			roundAmmoLabel.setText(String.format("%d/Unlimit", gun.getRound()));
			soldComponents.getChildren().remove(line2);
		}
		else if(gun instanceof Shotgun) gunName.setText("Shotgun");
		else if(gun instanceof AssultRifle) gunName.setText("Assult Rifle");
		else if(gun instanceof SniperRifle) gunName.setText("Sniper Rifle");
		
		setCursor(Cursor.HAND);

		updateStatus(Status.SELL);
	}
	
	public void updateStatus(Status status) {
		switch(status) {
		case SELL:
			soldComponents.setVisible(false);
			gunImageView.setOpacity(0.3);
			setOnMouseEntered((e) -> {
				gunImageView.setOpacity(0.7);
				slotBackground.setImage(ImageHolder.getInstance().gunSlotHighlight);
			});
			setOnMouseExited((e) -> { 
				gunImageView.setOpacity(0.3);
				slotBackground.setImage(ImageHolder.getInstance().gunSlot);
			});
			setOnMouseClicked((e) -> {
				if(e.getButton() == MouseButton.SECONDARY) {
					if(SystemCache.getInstance().player.getAccessories().costCash(gun.getPrice())) {
						Timeline fade = new Timeline(
								new KeyFrame(Duration.ZERO, new KeyValue(gunImageView.opacityProperty(),0)),
								new KeyFrame(new Duration(1000), new KeyValue(gunImageView.opacityProperty(),1)));
						fade.play();
						updateStatus(Status.SOLD);
					} else {
						SystemCache.getInstance().gameUI.notEnoughMoneyAlert();
					}
				}
			});
			return;
		case SOLD: 
			gunImageView.setOpacity(1);
			sellComponents.setVisible(false);
			soldComponents.setVisible(true);
			slotBackground.setImage(ImageHolder.getInstance().gunSlot);
			setOnMouseEntered((e) -> slotBackground.setImage(ImageHolder.getInstance().gunSlotHighlight));
			setOnMouseExited((e) -> slotBackground.setImage(ImageHolder.getInstance().gunSlot));
			setOnMousePressed((e) -> {
				if(e.getButton() == MouseButton.PRIMARY)
					slotBackground.setImage(ImageHolder.getInstance().gunSlotPressed);
			});
			setOnMouseClicked((e) -> {
				if(e.getButton() == MouseButton.PRIMARY)
					updateStatus(Status.EQUIPPED);	
				if(!(gun instanceof Pistol)) {
					if(e.getButton() == MouseButton.SECONDARY) {
						purchaseAmmo();
					}
				}
			});
			return;
		case EQUIPPED:
			setOnMouseEntered(null);
			setOnMouseExited(null);
			setOnMousePressed(null); 
			if(!(gun instanceof Pistol)) {
				setOnMouseClicked((e) -> {
					if(e.getButton() == MouseButton.SECONDARY) {
						purchaseAmmo();
					}
				});
			}
			slotBackground.setImage(ImageHolder.getInstance().gunSlotPressed);
			gunImageView.setOpacity(1);
			sellComponents.setVisible(false);
			soldComponents.setVisible(true);
			
			ShopUI shopUI = SystemCache.getInstance().shopUI;
			
			if(shopUI.gunSlotB != null) {
				shopUI.gunSlotB.updateStatus(Status.SOLD);
			}
			shopUI.gunSlotB = shopUI.gunSlotA;
			shopUI.gunSlotA = this;
			
			Accessories accessories = SystemCache.getInstance().player.getAccessories();
			accessories.changeGunA(shopUI.gunSlotA.gun);
			
			if(shopUI.gunSlotB != null)
			accessories.changeGunB(shopUI.gunSlotB.gun);
		}
	}
	
	public void purchaseAmmo() {
		if(gun.getAmmo() < gun.getMaxAmmo()) {
			if(SystemCache.getInstance().player.getAccessories().costCash(gun.getAmmoPrice())) {
				Timeline popUp = new Timeline(
					new KeyFrame(new Duration(100), new KeyValue(gunImageView.scaleXProperty(),1.1)),
					new KeyFrame(new Duration(100),
					(actionEvent) -> {
						Timeline popDown = new Timeline(
							new KeyFrame(new Duration(100), new KeyValue(gunImageView.scaleXProperty(),1)),
							new KeyFrame(new Duration(100), new KeyValue(gunImageView.scaleYProperty(),1))
						);
						popDown.play();
					},
					new KeyValue(gunImageView.scaleYProperty(),1.1))
				);
				popUp.play();
				gun.setAmmo(gun.getAmmo() + gun.getMaxRound());
				updateGunInfo();
			} else {
				SystemCache.getInstance().gameUI.notEnoughMoneyAlert();
			}
		}
	}
	
	public void updateGunInfo() {
		roundAmmoLabel.setText(String.format("%d/%s",gun.getRound(),
				(gun instanceof Pistol) ? "Unlimit" : gun.getAmmo()));
	}
	
	public enum Status {
		SELL,SOLD,EQUIPPED
	}
}
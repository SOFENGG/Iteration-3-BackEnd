package view.cashier;

import controller.CashierViewController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class HelpPopup extends Popup {

	public static final String TITLE = "Help";
	
	private CashierViewController cvc;
	
	private VBox layout;
	
	private VBox itemPurchaseVBox;
		private Label itemPurchaseLabel;
		private Label addCartLabel;
		private Label holdCartLabel;
		private Label clearItemLabel;
		private Label overridePriceLabel;
		private Label checkoutLabel;
		
	private VBox returnItemVBox;
		private Label returnItemLabel;
		private Label returnLabel;
		
	private VBox serviceWorkerVBox;
		private Label serviceWorkerLabel;
		private Label serviceLabel;
		
	private VBox endOfDayVBox;
		private Label endOfDayLabel;
		private Label eODLabel;
	
	private VBox logoutVBox;
		private Label logoutLabel;
		private Label logoutDescriptionLabel;
	
	private HBox buttonsHBox;
		private Button okayButton;
	
	public HelpPopup(CashierViewController cvc) {
		super(TITLE);
		
		this.cvc = cvc;
		
		initScene();
		initHandlers ();
		
		setScene(layout);
	}

	private void initScene() {
		layout = new VBox (20);
		layout.setId("Popup");
		
			itemPurchaseVBox = new VBox (10);
			itemPurchaseVBox.setId("PopupDetails");
			
				itemPurchaseLabel = new Label ("Item Purchase");
				itemPurchaseLabel.setId("LabelTitle");
				
				addCartLabel = new Label ("Add item or service using [Add to cart].");
				addCartLabel.setId("DefaultLabel");
				
				holdCartLabel = new Label ("If another customer needs to be attended, press [Hold cart], the hold tab contains all held carts.");
				holdCartLabel.setId("DefaultLabel");
				
				clearItemLabel = new Label ("If an item was mistakenly added, press [Clear Item] or [Clear Cart].");
				clearItemLabel.setId("DefaultLabel");
				
				overridePriceLabel = new Label ("In the event of a discount or price hike, press [Override Price].");
				overridePriceLabel.setId("DefaultLabel");
				
				checkoutLabel = new Label ("To finish transaction, press checkout.");
				checkoutLabel.setId("DefaultLabel");
			
			itemPurchaseVBox.getChildren().addAll(itemPurchaseLabel, addCartLabel, holdCartLabel, clearItemLabel, overridePriceLabel, checkoutLabel);
			
			returnItemVBox = new VBox (10);
			returnItemVBox.setId("PopupDetails");
			
				returnItemLabel = new Label ("Return Item");
				returnItemLabel.setId("LabelTitle");
				
				returnLabel = new Label ("Allows Items to be returned by a customer.");
				returnLabel.setId("DefaultLabel");
			
			returnItemVBox.getChildren().addAll(returnItemLabel, returnLabel);
			
			serviceWorkerVBox = new VBox (10);
			serviceWorkerVBox.setId("PopupDetails");
			
				serviceWorkerLabel = new Label ("Service Worker");
				serviceWorkerLabel.setId("LabelTitle");
				
				serviceLabel = new Label ("List of active workers that can do manual labor.");
				serviceLabel.setId("DefaultLabel");
			
			serviceWorkerVBox.getChildren().addAll(serviceWorkerLabel, serviceLabel);
			
			endOfDayVBox = new VBox (10);
			endOfDayVBox.setId("PopupDetails");
			
				endOfDayLabel = new Label ("End of Day");
				endOfDayLabel.setId("LabelTitle");
				
				eODLabel = new Label ("Once a business day is done, click to log earnings and log out.");
				eODLabel.setId("DefaultLabel");
			
			endOfDayVBox.getChildren().addAll(endOfDayLabel, eODLabel);
			
			logoutVBox = new VBox (10);       
			logoutVBox.setId("PopupDetails");
			
				logoutLabel = new Label ("Logout");
				logoutLabel.setId("LabelTitle");
				
				logoutDescriptionLabel = new Label ("Will remove current user, and allows another to log in.");
				logoutDescriptionLabel.setId("DefaultLabel");
			
			logoutVBox.getChildren().addAll(logoutLabel, logoutDescriptionLabel);
			
			buttonsHBox = new HBox (20);
			buttonsHBox.setAlignment(Pos.CENTER_RIGHT);
			
				okayButton = new Button ("Okay");
				okayButton.getStyleClass().add("GreenButton");
			
			buttonsHBox.getChildren().addAll (okayButton);
		
		layout.getChildren().addAll(itemPurchaseVBox, returnItemVBox, serviceWorkerVBox, endOfDayVBox, logoutVBox, buttonsHBox);
	}

	private void initHandlers() {
		okayButton.setOnAction(e -> {
			closePopup();
		});
	}

}

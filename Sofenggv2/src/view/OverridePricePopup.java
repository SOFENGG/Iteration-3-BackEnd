package view;

import controller.CashierViewController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class OverridePricePopup extends Popup{

	public static final String TITLE = "Override Price";
	
	private CashierViewController cvc;
	
	private VBox layout;
		private Label overrideLabel;
		private CartView cv;
		private HBox priceHBox;
			private Label priceLabel;
			private TextField priceTextField;
		private HBox buttonsHBox;
			private Button okayButton;
			private Button cancelButton;
	
	public OverridePricePopup(CashierViewController cvc) {
		super(TITLE);
		
		this.cvc = cvc;
		
		initScene();
		initHandlers ();
		
		setScene(layout);
	}

	private void initScene() {
		layout = new VBox (20);
		layout.setId("Popup");
		
			overrideLabel = new Label ("Select item to change price:");
			overrideLabel.setId("DefaultLabel");
			
			cv = new CartView (cvc);
			
			priceHBox = new HBox (20);
				
				priceLabel = new Label ("New price:");
				priceLabel.setId("DefaultLabel");
				
				priceTextField = new TextField ();
				priceTextField.setId("TextField");
				priceTextField.setPromptText("price");
				
			priceHBox.getChildren().addAll(priceLabel, priceTextField);
		
			buttonsHBox = new HBox (20);
			buttonsHBox.setAlignment(Pos.CENTER_RIGHT);
			
				okayButton = new Button ("Okay");
				okayButton.getStyleClass().add("GreenButton");
				
				cancelButton = new Button ("Cancel");
				cancelButton.getStyleClass().add("Button");
			
			buttonsHBox.getChildren().addAll (cancelButton, okayButton);
			
		layout.getChildren().addAll(overrideLabel, cv, priceHBox, buttonsHBox);
	}

	private void initHandlers() {
		okayButton.setOnAction(e -> {
			//tim
		});
		
		cancelButton.setOnAction(e -> {
			closePopup();
		});
	}

}

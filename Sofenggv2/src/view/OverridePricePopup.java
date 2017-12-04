package view;

import java.math.BigDecimal;

import controller.CashierViewController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.CartItem;

public class OverridePricePopup extends Popup{

	public static final String TITLE = "Override Price";
	
	private CashierViewController cvc;
	private CartItem item;
	
	private VBox layout;
		private Label overrideLabel;
		private HBox priceHBox;
			private Label priceLabel;
			private TextField priceTextField;
		private HBox buttonsHBox;
			private Button okayButton;
			private Button cancelButton;
	
	public OverridePricePopup(CashierViewController cvc, String itemCode) {
		super(TITLE);
		
		this.cvc = cvc;

		initCartItem(itemCode);
		
		initScene();
		initHandlers ();
		
		setScene(layout);
	}
	
	public void initCartItem(String itemCode){
		for(CartItem i : cvc.getCartItems()){
			if(i.getItemCode().equals(itemCode)){
				item = i;
				break;
			}
		}
	}
	
	public void initCartItem(int serviceId){
		for(CartItem i : cvc.getCartItems()){
			if(i.getServiceId() == serviceId){
				item = i;
				break;
			}
		}
	}

	private void initScene() {
		layout = new VBox (20);
		layout.setId("Popup");
		
			overrideLabel = new Label (item.getName() + " (" + item.getOriginalPrice().toString() + "php)");
			overrideLabel.setId("DefaultLabel");
			
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
			
		layout.getChildren().addAll(overrideLabel, priceHBox, buttonsHBox);
	}

	private void initHandlers() {
		okayButton.setOnAction(e -> {
			double inputPrice = Double.parseDouble(priceTextField.getText());
			BigDecimal newPrice = BigDecimal.valueOf(inputPrice);
			
			cvc.overridePrice(item.getItemCode(),
					newPrice);
			closePopup();
		});
		
		cancelButton.setOnAction(e -> {
			closePopup();
		});
	}

}

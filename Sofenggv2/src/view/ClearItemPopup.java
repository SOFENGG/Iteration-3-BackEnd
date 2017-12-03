package view;

import java.math.BigDecimal;

import controller.CashierViewController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.CartItem;

public class ClearItemPopup extends Popup{

	public static final String TITLE = "Clear Item";
	
	private CashierViewController cvc;
	private CartItem item;
	
	private VBox layout;
		private Label itemLabel;
		private HBox quantityHBox;
			private Label quantityLabel;
			private TextField quantityTextField;
		private HBox buttonsHBox;
			private Button okayButton;
			private Button cancelButton;
	
	public ClearItemPopup(CashierViewController cvc, String itemCode) {
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

	private void initScene() {
		layout = new VBox (20);
		layout.setId("Popup");
		
			itemLabel = new Label (item.getName() + " (x" + item.getQuantity() + ")");
			itemLabel.setId("DefaultLabel");
			
			quantityHBox = new HBox (20);
				
				quantityLabel = new Label ("New quantity:");
				quantityLabel.setId("DefaultLabel");
				
				quantityTextField = new TextField ();
				quantityTextField.setId("TextField");
				quantityTextField.setPromptText("quantity");
				
			quantityHBox.getChildren().addAll(quantityLabel, quantityTextField);
		
			buttonsHBox = new HBox (20);
			buttonsHBox.setAlignment(Pos.CENTER_RIGHT);
			
				okayButton = new Button ("Okay");
				okayButton.getStyleClass().add("GreenButton");
				
				cancelButton = new Button ("Cancel");
				cancelButton.getStyleClass().add("Button");
			
			buttonsHBox.getChildren().addAll (cancelButton, okayButton);
			
		layout.getChildren().addAll(itemLabel, quantityHBox, buttonsHBox);
	}

	private void initHandlers() {
		okayButton.setOnAction(e -> {
			int quantity = Integer.parseInt(quantityTextField.getText());
			if(quantity <= item.getQuantity()){
				cvc.removeCartItem(item.getItemCode(),quantity);
				closePopup();
			}else{
				closePopup();
				new AlertBoxPopup("Failed", "Failed");
			}
		});
		
		cancelButton.setOnAction(e -> {
			closePopup();
		});
	}

}

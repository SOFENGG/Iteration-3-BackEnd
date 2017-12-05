package view;

import java.math.BigDecimal;

import controller.CashierViewController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.CartItemType;

public class CartPopup extends Popup{

	public static final String TITLE = "Add";
	
	private CashierViewController cvc;
	
	ObservableList<String> row;
	
	private VBox layout;
		private Label itemCodeLabel;
		private Label descriptionLabel;
		private Label unitPriceLabel;
		private HBox quantityHBox;
			private Label quantityLabel;
			private Spinner<Integer> integerSpin;
			private SpinnerValueFactory<Integer> valueFactory;
			private int qty;
			private double price;
			private Label totalLabel;
		private HBox buttonsHBox;
			private Button okayButton;
			private Button cancelButton;
	
	public CartPopup(CashierViewController cvc, ObservableList<String> row) {
		super(TITLE);
		
		this.cvc = cvc;
		this.row = row;
		qty = 1;
		
		initScene();
		initHandlers ();
		
		setScene(layout);
	}

	private void initScene() {
		layout = new VBox (20);
		layout.setId("Popup");
		
			itemCodeLabel = new Label ("Item Code: " + row.get(0));
			itemCodeLabel.setId("DefaultLabel");
			
			descriptionLabel = new Label ("Description: " + row.get(3));
			descriptionLabel.setId("DefaultLabel");
			
			unitPriceLabel = new Label ("Unit Price: " + row.get(6));
			unitPriceLabel.setId("DefaultLabel");
			
			quantityHBox = new HBox (10);
			
				quantityLabel = new Label ("Quantity:");
				quantityLabel.setId("DefaultLabel");
				
				qty = Integer.parseInt(row.get(1));
				price = Double.parseDouble(row.get(6));
				
				valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, qty, 1);
				
				integerSpin = new Spinner<Integer>();
				integerSpin.setValueFactory(valueFactory);
				integerSpin.valueProperty().addListener(new ChangeListener<Integer>(){
					@Override
					public void changed(ObservableValue<? extends Integer> arg0, Integer oldVal, Integer newVal) {
						totalLabel.setText("Total Price: " + Double.toString(price * newVal));
						qty = newVal;
						resizeScene();
					}
				});
				//sets intinial qty to 1
				qty = 1;
				
				totalLabel = new Label ("Total Price:");
				totalLabel.setId("DefaultLabel");
			
			quantityHBox.getChildren().addAll(quantityLabel, integerSpin, totalLabel);
		
			buttonsHBox = new HBox (20);
			buttonsHBox.setAlignment(Pos.CENTER_RIGHT);
			
				okayButton = new Button ("Okay");
				okayButton.getStyleClass().add("GreenButton");
				
				cancelButton = new Button ("Cancel");
				cancelButton.getStyleClass().add("RedButton");
			
			buttonsHBox.getChildren().addAll (cancelButton, okayButton);
		
		layout.getChildren().addAll(itemCodeLabel, descriptionLabel, unitPriceLabel, quantityHBox, buttonsHBox);
	}

	private void initHandlers() {
		okayButton.setOnAction(e -> {
			boolean success = cvc.addToCart(CartItemType.ITEM,
								row.get(InventoryView.ITEM_CODE),
								row.get(InventoryView.NAME),
								BigDecimal.valueOf(Double.parseDouble(row.get(InventoryView.PRICE))),
								qty);	
			if(!success){
				closePopup();
				new AlertBoxPopup("Stock", "Not enough stock");
			}else{
				closePopup();
			}
		});
		
		cancelButton.setOnAction(e -> {
			closePopup();
		});
	}

	
	
}

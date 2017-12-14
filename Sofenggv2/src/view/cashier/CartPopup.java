package view.cashier;

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
		private VBox itemDetailsVBox;
			private Label itemDetailsLabel;
			private HBox itemCodeHBox;
				private Label itemCodeLabel;
				private Label itemCode;
			private HBox nameHBox;
				private Label nameLabel;
				private Label name;
			private HBox descriptionHBox;
				private Label descriptionLabel;
				private Label description;
			private HBox unitPriceHBox;
				private Label unitPriceLabel;
				private Label unitPrice;
		private VBox priceDetailsVBox;
			private Label priceDetailsLabel;
			private HBox quantityHBox;
				private Label quantityLabel;
				private Spinner<Integer> integerSpin;
				private SpinnerValueFactory<Integer> valueFactory;
				private int qty;
				private double price;
			private HBox totalHBox;
				private Label totalLabel;
				private Label total;
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
		
			itemDetailsVBox = new VBox (10);
			itemDetailsVBox.setId("PopupDetails");
				
				itemDetailsLabel = new Label ("Item Details");
				itemDetailsLabel.setId("LabelTitle");
				
				itemCodeHBox = new HBox (20);
				
					itemCodeLabel = new Label ("Item Code:");
					itemCodeLabel.setId("LabelGray");
					
					itemCode = new Label (row.get(0));
					itemCode.setId("DefaultLabel");
				
				itemCodeHBox.getChildren().addAll(itemCodeLabel, itemCode);
				
				nameHBox = new HBox (20);
				
					nameLabel = new Label ("Name:");
					nameLabel.setId("LabelGray");
					
					name = new Label (row.get(2));
					name.setId("DefaultLabel");
			
				nameHBox.getChildren().addAll(nameLabel, name);
				
				descriptionHBox = new HBox (20);
				
					descriptionLabel = new Label ("Description:");
					descriptionLabel.setId("LabelGray");
					
					description = new Label (row.get(3));
					description.setId("DefaultLabel");
			
				descriptionHBox.getChildren().addAll(descriptionLabel, description);
				
				unitPriceHBox = new HBox (20);
				
					unitPriceLabel = new Label ("Unit Price:");
					unitPriceLabel.setId("LabelGray");
					
					unitPrice = new Label ("PHP " + row.get(6));
					unitPrice.setId("DefaultLabel");
			
				unitPriceHBox.getChildren().addAll(unitPriceLabel, unitPrice);
			
			itemDetailsVBox.getChildren().addAll(itemDetailsLabel, itemCodeHBox, nameHBox, descriptionHBox, unitPriceHBox);

			priceDetailsVBox = new VBox (10);
			priceDetailsVBox.setId("PopupDetails");
			
				quantityHBox = new HBox (20);
					
					priceDetailsLabel = new Label ("Price Details");
					priceDetailsLabel.setId("LabelTitle");
					
					quantityLabel = new Label ("Quantity:");
					quantityLabel.setId("LabelGray");
					
					qty = Integer.parseInt(row.get(1));
					price = Double.parseDouble(row.get(6));
					
					valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, qty, 1);
					
					integerSpin = new Spinner<Integer>();
					integerSpin.setValueFactory(valueFactory);
					integerSpin.valueProperty().addListener(new ChangeListener<Integer>(){
						@Override
						public void changed(ObservableValue<? extends Integer> arg0, Integer oldVal, Integer newVal) {
							total.setText("PHP " + Double.toString(price * newVal));
							qty = newVal;
							resizeScene();
						}
					});
					//sets intinial qty to 1
					qty = 1;
					
				
				quantityHBox.getChildren().addAll(quantityLabel, integerSpin);
				
				totalHBox = new HBox (20);
				
					totalLabel = new Label ("Total Price:");
					totalLabel.setId("LabelGray");
					
					total = new Label ("PHP 0");
					total.setId("DefaultLabel");
				
				totalHBox.getChildren().addAll(totalLabel, total);
			
			priceDetailsVBox.getChildren().addAll(priceDetailsLabel, quantityHBox, totalHBox);
		
			buttonsHBox = new HBox (20);
			buttonsHBox.setAlignment(Pos.CENTER_RIGHT);
			
				okayButton = new Button ("Okay");
				okayButton.getStyleClass().add("GreenButton");
				
				cancelButton = new Button ("Cancel");
				cancelButton.getStyleClass().add("RedButton");
			
			buttonsHBox.getChildren().addAll (cancelButton, okayButton);
		
		layout.getChildren().addAll(itemDetailsVBox, priceDetailsVBox, buttonsHBox);
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
	
	public int getQuantity(){
		return qty;
	}

	
	
}

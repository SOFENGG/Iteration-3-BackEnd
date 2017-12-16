package view.manager.ui;

import java.math.BigDecimal;
import java.sql.Date;

import controller.ManagerViewController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import view.cashier.AlertBoxPopup;
import view.manager.final_values.Values;

public class InventoryBanner extends Banner{
	private ManagerViewController mvc;

	/* Left Section */
	private TextField itemCodeField;
	private TextField categoryField;
	private TextField supplierField;
	private TextField stockField;
	private TextField unitPriceSupField;
	
	/* Right Section */	
	private TextField nameField;
	private TextField ItemDescriptionField;
	private TextField manufacturerField;
	private DatePicker datePurchasedField;
	private TextField unitPriceCustField;
	
	/* Bottom Buttons */
	private Button editConfirmBtn;
		
	public InventoryBanner(ManagerViewController mvc) {
		super();
		this.mvc = mvc;
		updateToInventoryBanner();
		//setPositions();
		initHandler();
		setGreenButton();
	}
	
	public void initHandler(){
		editConfirmBtn.setOnAction(e -> {
			if(!itemCodeField.getText().equals("") && !nameField.getText().equals("") && !ItemDescriptionField.getText().equals("") &&
			   !categoryField.getText().equals("") && !manufacturerField.getText().equals("") && !supplierField.getText().equals("") &&
			   !stockField.getText().equals("") && datePurchasedField.getValue() != null && !unitPriceSupField.getText().equals("") && !unitPriceCustField.getText().equals("")){
				String itemCode = itemCodeField.getText();
				String name = nameField.getText();
				String description = ItemDescriptionField.getText();
				String category = categoryField.getText();
				String manufacturer = manufacturerField.getText();
				String supplierCode = supplierField.getText();
				int stock = Integer.parseInt(stockField.getText());
				Date datePurchase = java.sql.Date.valueOf(datePurchasedField.getValue());
				BigDecimal priceSupplier = BigDecimal.valueOf(Double.parseDouble(unitPriceSupField.getText()));
				BigDecimal priceCustomer = BigDecimal.valueOf(Double.parseDouble(unitPriceCustField.getText()));
			
				try{
					mvc.editItem(itemCode, name, description, category, manufacturer, supplierCode, stock, datePurchase, priceSupplier, priceCustomer);
					new AlertBoxPopup("Success", "Item updated.");
					itemCodeField.setText("");
					nameField.setText("");
					ItemDescriptionField.setText("");
					categoryField.setText("");
					manufacturerField.setText("");
					supplierField.setText("");
					stockField.setText("");
					datePurchasedField.getEditor().clear();
					datePurchasedField.setValue(null);
					unitPriceSupField.setText("");
					unitPriceCustField.setText("");
				}catch(NumberFormatException nfe){
					new AlertBoxPopup("Input Error", "Enter a valid number.");
				}
			} else
				new AlertBoxPopup("Input Error", "Some fields are left blank.");
			mvc.getAllItems(new String[] {InventoryView.KEY});
	
		});
	}
	
	private void updateToInventoryBanner() {
		/* Banner Title */
		bannerTitle.setText(Values.BANNER_INVENTORY);
		initInventoryEdit();
	}

	private void initInventoryEdit() {	
		/*Contains the label and textfield combinations for left box*/
		VBox[] leftCombos = new VBox[5];
		
		/*item Code Combination*/
		leftCombos[0] = new VBox();
		itemCodeField = new TextField();
		leftCombos[0].getChildren().addAll(new Label("Item Code:"), itemCodeField);
		
		/*item category Combination*/
		leftCombos[1] = new VBox();
		categoryField = new TextField();
		leftCombos[1].getChildren().addAll(new Label("Category:"), categoryField);
		
		/*item supplier Combination*/
		leftCombos[2] = new VBox();
		supplierField = new TextField();
		leftCombos[2].getChildren().addAll(new Label("Supplier Code:"), supplierField);
		
		/*item stock Combination*/
		leftCombos[3] = new VBox();
		stockField = new TextField();
		leftCombos[3].getChildren().addAll(new Label("Stock:"), stockField);
		
		/*item price bought Combination*/
		leftCombos[4] = new VBox();
		unitPriceSupField = new TextField();
		leftCombos[4].getChildren().addAll(new Label("Price Bought:"), unitPriceSupField);
		
		
		/*Contains the label and textfield combinations for right box*/
		VBox[] rightCombos = new VBox[5];
		
		/*item name Combination*/
		rightCombos[0] = new VBox();
		nameField = new TextField();
		rightCombos[0].getChildren().addAll(new Label("Name:"), nameField);
		
		/*Descriptions Combination*/
		rightCombos[1] = new VBox();
		ItemDescriptionField = new TextField();
		rightCombos[1].getChildren().addAll(new Label("Description:"), ItemDescriptionField);
		
		/*Manufacturer Combination*/
		rightCombos[2] = new VBox();
		manufacturerField = new TextField();
		rightCombos[2].getChildren().addAll(new Label("Manufacturer:"), manufacturerField);
		
		/*Date Purchased Combination*/
		rightCombos[3] = new VBox();
		datePurchasedField = new DatePicker();
		rightCombos[3].getChildren().addAll(new Label("Date Purchased:"), datePurchasedField);
		datePurchasedField.setValue(null);
		
		/*Price selling Combination*/
		rightCombos[4] = new VBox();
		unitPriceCustField = new TextField();
		rightCombos[4].getChildren().addAll(new Label("Price To Customer:"), unitPriceCustField);
		
		/*Edit Button initialization*/
		editConfirmBtn = new Button("Confirm Edit");
		editConfirmBtn.getStyleClass().add("GreenButton");
		
		/* Assembly */
		leftColumn.getChildren().addAll(leftCombos[0], leftCombos[1], leftCombos[2] , leftCombos[3] , leftCombos[4], editConfirmBtn);
		rightColumn.getChildren().addAll(rightCombos[0], rightCombos[1], rightCombos[2], rightCombos[3], rightCombos[4]);
		//bottom.getChildren().addAll(editConfirmBtn);
	}
	
	@SuppressWarnings("unused")
	private void setPositions() {
		setAlignment(editConfirmBtn, Pos.CENTER_RIGHT);
		setMargin(editConfirmBtn, new Insets(0, 20, 20, 0));
	}
	
}

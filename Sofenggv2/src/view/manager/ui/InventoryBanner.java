package view.manager.ui;

import java.math.BigDecimal;

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
	}
	
	public void initHandler(){
		editConfirmBtn.setOnAction(e -> {
			String itemCode = itemCodeField.getText();
			String supplierCode = supplierField.getText();
			String description = ItemDescriptionField.getText();
			BigDecimal unitPrice;
			
			try{
				//XXX
				//unitPrice = BigDecimal.valueOf(Double.parseDouble(unitPriceField.getText()));
				
				if(!itemCode.equals("") && !supplierCode.equals("") && !description.equals("")){
					//mvc.editItem(itemCode, description, supplierCode, unitPrice);
					new AlertBoxPopup("Success", "Item updated.");
					itemCodeField.setText("");
					supplierField.setText("");
					ItemDescriptionField.setText("");
					//unitPriceField.setText("");
				}else{
					new AlertBoxPopup("Input Error", "Some fields are left blank.");
				}
				
			}catch(NumberFormatException nfe){
				new AlertBoxPopup("Input Error", "Enter a valid number.");
			}
	
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
		
		
		/*Price selling Combination*/
		rightCombos[4] = new VBox();
		unitPriceCustField = new TextField();
		rightCombos[4].getChildren().addAll(new Label("Price To Customer:"), unitPriceCustField);
		
		/*Edit Button initialization*/
		editConfirmBtn = new Button("Confirm Edit");
		
		
		/* Assembly */
		leftColumn.getChildren().addAll(leftCombos[0], leftCombos[1], leftCombos[2] , leftCombos[3] , leftCombos[4]);
		rightColumn.getChildren().addAll(rightCombos[0], rightCombos[1], rightCombos[2], rightCombos[3], rightCombos[4]);
		bottom.getChildren().addAll(editConfirmBtn);
	}
	
	private void setPositions() {
		setAlignment(editConfirmBtn, Pos.CENTER_RIGHT);
		setMargin(editConfirmBtn, new Insets(0, 20, 20, 0));
	}
	
}

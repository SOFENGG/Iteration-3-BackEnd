package view.manager.ui;

import java.math.BigDecimal;

import controller.ManagerViewController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import view.cashier.AlertBoxPopup;
import view.manager.final_values.Values;

public class InventoryBanner extends Banner{
	private ManagerViewController mvc;

	/* Left Section */
	private TextField itemCodeField;
	private TextField supplierField;
	private TextField requisitionerField;
	
	/* Right Section */	
	private TextField ItemDescriptionField;
	private TextField unitPriceField;
	
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
				unitPrice = BigDecimal.valueOf(Double.parseDouble(unitPriceField.getText()));
				
				if(!itemCode.equals("") && !supplierCode.equals("") && !description.equals("")){
					mvc.editItem(itemCode, description, supplierCode, unitPrice);
					new AlertBoxPopup("Success", "Item updated.");
					itemCodeField.setText("");
					supplierField.setText("");
					ItemDescriptionField.setText("");
					unitPriceField.setText("");
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
		VBox[] leftCombos = new VBox[3];
		
		/*item Code Combination*/
		leftCombos[0] = new VBox();
		itemCodeField = new TextField();
		leftCombos[0].getChildren().addAll(new Label("Item Code:"), itemCodeField);

		
		/*item descritption Combination*/
		leftCombos[1] = new VBox();
		supplierField = new TextField();
		leftCombos[1].getChildren().addAll(new Label("Supplier:"), supplierField);
		
		leftCombos[2] = new VBox();
		requisitionerField = new TextField();
		leftCombos[2].getChildren().addAll(new Label("Requisitioner:"), requisitionerField);
		
		
		/*Contains the label and textfield combinations for right box*/
		VBox[] rightCombos = new VBox[2];
		
		rightCombos[0] = new VBox();
		ItemDescriptionField = new TextField();
		rightCombos[0].getChildren().addAll(new Label("Item Description:"), ItemDescriptionField);
		
		/*Requisitioner Combination*/
		rightCombos[1] = new VBox();
		unitPriceField = new TextField();
		rightCombos[1].getChildren().addAll(new Label("Unit Price:"), unitPriceField);
		
		/*Edit Button initialization*/
		editConfirmBtn = new Button("Confirm Edit");
		
		
		/* Assembly */
		leftColumn.getChildren().addAll(leftCombos[0], leftCombos[1], leftCombos[2]);
		rightColumn.getChildren().addAll(rightCombos[0], rightCombos[1]);
		bottom.getChildren().addAll(editConfirmBtn);
	}
	
	private void setPositions() {
		setAlignment(editConfirmBtn, Pos.CENTER_RIGHT);
		setMargin(editConfirmBtn, new Insets(0, 20, 20, 0));
	}
	
}

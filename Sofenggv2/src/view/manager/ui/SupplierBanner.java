package view.manager.ui;

import controller.ManagerViewController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import model.Supplier;
import view.cashier.AlertBoxPopup;
import view.manager.final_values.Values;

public class SupplierBanner extends Banner{
	private ManagerViewController mvc;
	
	/* Left Section */
	private TextField supplierCodeField;
	private TextField contactPersonField;
	private TextField taxIdField;
	
	/* Right Section */
	private TextField supplierNameField;
	private TextField contactNumberField;

	
	/* Bottom Buttons */
	private Button addConfirmBtn;
				
	public SupplierBanner(ManagerViewController mvc){
		super();
		this.mvc = mvc;
		updateToSupplierBanner();
		//setPositions();
		initHandler();
	}
	
	public void initHandler(){
		addConfirmBtn.setOnAction(e -> {
			String supplierCode = supplierCodeField.getText();
			String contactPerson = contactPersonField.getText();
			String taxId = taxIdField.getText();
			String supplierName = supplierNameField.getText();
			String contactNumber = contactNumberField.getText();
			
			if(!supplierCode.equals("") 
					&& !contactPerson.equals("")
					&& !taxId.equals("")
					&& !supplierName.equals("")
					&& !contactNumber.equals("")){
				
				mvc.addSupplier(new Supplier(supplierCode, supplierName, contactPerson, contactNumber, taxId));
				
				new AlertBoxPopup("Success", "Supplier added.");
				supplierCodeField.setText("");
				contactPersonField.setText("");
				taxIdField.setText("");
				supplierNameField.setText("");
				contactNumberField.setText("");
			}else{
				new AlertBoxPopup("Input Error", "Some fields are left blank.");
			}
			
		});
	}

	private void updateToSupplierBanner() {
		/* Banner Title */
		bannerTitle.setText(Values.BANNER_SUPPLIER);
		initSupplierAdd();
	}

	private void initSupplierAdd() {
		/*Contains the label and textfield combinations for left box*/
		VBox[] leftCombos = new VBox[3];
		
		/*item Code Combination*/
		leftCombos[0] = new VBox();
		supplierCodeField = new TextField();
		leftCombos[0].getChildren().addAll(new Label("Supplier Code:"), supplierCodeField);
		
		/*item descritption Combination*/
		leftCombos[1] = new VBox();
		contactPersonField = new TextField();
		leftCombos[1].getChildren().addAll(new Label("Contact Person:"), contactPersonField);
		
		leftCombos[2] = new VBox();
		taxIdField = new TextField();
		leftCombos[2].getChildren().addAll(new Label("Tax ID:"), taxIdField);
		
		/*Contains the label and textfield combinations for right box*/
		VBox[] rightCombos = new VBox[2];
		
		/*Supplier Combination*/
		rightCombos[0] = new VBox();
		supplierNameField = new TextField();
		rightCombos[0].getChildren().addAll(new Label("Supplier Name:"), supplierNameField);
		
		/*Unit price Combination*/
		rightCombos[1] = new VBox();
		contactNumberField = new TextField();
		rightCombos[1].getChildren().addAll(new Label("Contact Number:"), contactNumberField);
		
		/*Edit Button initialization*/
		addConfirmBtn = new Button("Add Supplier");
		
		/* Assembly */
		leftColumn.getChildren().addAll(leftCombos[0], leftCombos[1], leftCombos[2]);
		rightColumn.getChildren().addAll(rightCombos[0], rightCombos[1]);
		bottomButtons.getChildren().addAll(addConfirmBtn);
	}
	
	private void setPositions() {
		setAlignment(addConfirmBtn, Pos.CENTER_RIGHT);
		setMargin(addConfirmBtn, new Insets(0, 20, 20, 0));
	}
	
	
}

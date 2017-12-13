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
public class CustomerBanner extends Banner {
	private ManagerViewController mvc;
	/* Left Column */
	private TextField custNameField;
	private TextField custAddressField;
	
	/* Right Column */
	private TextField debtLimitField;
	private TextField contactNumberField;
	
	/* Bottom Buttons */
	private Button addCustomerBtn;
	
	public CustomerBanner(ManagerViewController mvc) {
		super();
		this.mvc = mvc;
		updateToCustomerDebts();
		setPositions();
		initButtonHandlers();
	}
	
	/* For Back End Developers */
	private void initButtonHandlers() {
		addCustomerBtn.setOnAction(e -> {
			String name = custNameField.getText();
			String address = custAddressField.getText();
			String contactNumber = contactNumberField.getText();
			BigDecimal debtLimit;
			
			try{
				debtLimit = BigDecimal.valueOf(Double.parseDouble(debtLimitField.getText()));
				
				if(!name.equals("") && !address.equals("") && !contactNumber.equals("")){
					mvc.addCustomer(name, contactNumber, address, debtLimit);
					new AlertBoxPopup("Success", "Customer added.");
					custNameField.setText("");
					custAddressField.setText("");
					contactNumberField.setText("");
					debtLimitField.setText("");
				}else{
					new AlertBoxPopup("Input Error", "Some fields are left blank.");
				}
				
			}catch(NumberFormatException nfe){
				new AlertBoxPopup("Input Error", "Enter a valid number.");
			}
		});
	}
	
	private void updateToCustomerDebts() {
		
		/* Banner Title */
		bannerTitle.setText(Values.BANNER_CUSTOMER);
		
		/* Left Column */
		VBox[] leftCombos = new VBox[2];
		
		leftCombos[0] = new VBox();
		custNameField = new TextField();
		leftCombos[0].getChildren().addAll(new Label("Customer Name:"), custNameField);
		
		leftCombos[1] = new VBox();
		custAddressField = new TextField();
		leftCombos[1].getChildren().addAll(new Label("Address:"), custAddressField);
			
			
		
		/* Right Column */	
		VBox[] rightCombos = new VBox[2];
		
		rightCombos[0] = new VBox();
		contactNumberField = new TextField();
		rightCombos[0].getChildren().addAll(new Label("Contact Number:"), contactNumberField);
		
		rightCombos[1] = new VBox();
		debtLimitField = new TextField();
		rightCombos[1].getChildren().addAll(new Label("Debt Limit:"), debtLimitField);
		
		/* Bottom Buttons */
		addCustomerBtn = new Button("Add Customer");
		
		/* Assembly */
		leftColumn.getChildren().addAll(leftCombos[0], leftCombos[1]);
		rightColumn.getChildren().addAll(rightCombos[0], rightCombos[1]);
		setBottom(addCustomerBtn);
	}
	
	private void setPositions() {
		setAlignment(addCustomerBtn, Pos.CENTER_RIGHT);
		setMargin(addCustomerBtn, new Insets(0, 20, 20, 0));
	}
	
}

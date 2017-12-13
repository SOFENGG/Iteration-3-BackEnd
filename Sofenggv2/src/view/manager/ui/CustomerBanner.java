package view.manager.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import view.manager.final_values.Values;
public class CustomerBanner extends Banner {
	
	/* Left Column */
	private TextField custNameField;
	private TextField custAddressField;
	
	/* Right Column */
	private TextField debtLimitField;
	
	/* Bottom Buttons */
	private Button addCustomerBtn;
	
	public CustomerBanner() {
		super();
		updateToCustomerDebts();
		setPositions();
		initButtonHandlers();
	}
	
	/* For Back End Developers */
	private void initButtonHandlers() {
		addCustomerBtn.setOnAction(e -> {
			/* Add Customer Button */
			
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
		VBox rightCombos = new VBox();
		debtLimitField = new TextField();
		rightCombos.getChildren().addAll(new Label("Debt Limit:"), debtLimitField);
		
		/* Bottom Buttons */
		addCustomerBtn = new Button("Add Customer");
		
		/* Assembly */
		leftColumn.getChildren().addAll(leftCombos[0], leftCombos[1]);
		rightColumn.getChildren().addAll(rightCombos);
		setBottom(addCustomerBtn);
	}
	
	private void setPositions() {
		setAlignment(addCustomerBtn, Pos.CENTER_RIGHT);
		setMargin(addCustomerBtn, new Insets(0, 20, 20, 0));
	}
	
}

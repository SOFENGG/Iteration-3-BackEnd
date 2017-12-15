package view.manager.ui;

import java.math.BigDecimal;

import controller.ManagerViewController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
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
	
	/*Bottom Half*/
	private VBox BottomBannerDetails;
		private Label BottomBannerTitle;
		private HBox BottomBannerContents;
			private VBox BottomLeftColumn;
				private TextField accountIDField;
				private TextField bCustNameField;
				private TextField bCustAddressField;
			private VBox BottomRightColumn;
				private TextField debtField;
				private TextField contactInfoField;
				private TextField bDebtLimitField;
		private Button editCustomerBtn;		
	
	
	public CustomerBanner(ManagerViewController mvc) {
		super();
		this.mvc = mvc;
		updateToCustomerDebts();
		//setPositions();
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
		addCustomerBtn.getStyleClass().add("GreenButton");
		
		/* Assembly */
		leftColumn.getChildren().addAll(leftCombos[0], leftCombos[1], addCustomerBtn);
		rightColumn.getChildren().addAll(rightCombos[0], rightCombos[1]);
		
		initBottomBanner();
		
		/*Bottom Left Column*/
		VBox[] BottomLeftCombos = new VBox[3];
		
		BottomLeftCombos[0] = new VBox();
		accountIDField = new TextField();
		BottomLeftCombos[0].getChildren().addAll(new Label("Account ID:"), accountIDField);
		
		BottomLeftCombos[1] = new VBox();
		bCustNameField = new TextField();
		BottomLeftCombos[1].getChildren().addAll(new Label("Customer Name:"), bCustNameField);
		
		BottomLeftCombos[2] = new VBox();
		bCustAddressField = new TextField();
		BottomLeftCombos[2].getChildren().addAll(new Label("Address:"), bCustAddressField);
		
		VBox[] BottomRightCombos = new VBox[3];
		
		BottomRightCombos[0] = new VBox();
		debtField = new TextField();
		BottomRightCombos[0].getChildren().addAll(new Label("Debt:"), debtField);
		
		BottomRightCombos[1] = new VBox();
		contactInfoField = new TextField();
		BottomRightCombos[1].getChildren().addAll(new Label("Contact Number:"), contactInfoField);
		
		BottomRightCombos[2] = new VBox();
		bDebtLimitField = new TextField();
		BottomRightCombos[2].getChildren().addAll(new Label("Debt Limit:"), bDebtLimitField);
		
		
		editCustomerBtn = new Button("Edit Customer");
		editCustomerBtn.getStyleClass().add("GreenButton");
		
		BottomLeftColumn.getChildren().addAll(BottomLeftCombos[0], BottomLeftCombos[1], BottomLeftCombos[2], editCustomerBtn);
		BottomRightColumn.getChildren().addAll(BottomRightCombos[0], BottomRightCombos[1], BottomRightCombos[2]);
	}
	
	private void initBottomBanner() {
		/*Bottom Half*/
		/* Bottom Banner Details Initialization */
		BottomBannerDetails = new VBox(Values.BANNER_DETAILS_ITEM_SPACING);
		
		/* Banner Title Initialization */
		BottomBannerTitle = new Label("Edit Customer");
		BottomBannerTitle.setPadding(new Insets(Values.BANNER_TITLE_TOP_PADDING, Values.BANNER_TITLE_RIGHT_PADDING, Values.BANNER_TITLE_BOTTOM_PADDING, Values.BANNER_TITLE_LEFT_PADDING));
		
		/* Banner Contents Initialization */
		BottomBannerContents = new HBox(Values.BANNER_CONTENTS_ITEM_SPACING);
		BottomBannerContents.setPadding(new Insets(Values.BANNER_CONTENTS_TOP_PADDING, Values.BANNER_CONTENTS_RIGHT_PADDING, Values.BANNER_CONTENTS_BOTTOM_PADDING, Values.BANNER_CONTENTS_LEFT_PADDING));
		
		/* Banner Contents Sections Initialization */
		BottomLeftColumn = new VBox(Values.LEFT_SPACING);
		BottomRightColumn = new VBox(Values.RIGHT_ITEM_SPACING);
		
		BottomBannerContents.getChildren().addAll(BottomLeftColumn, BottomRightColumn);
		BottomBannerDetails.getChildren().addAll(BottomBannerTitle, BottomBannerContents);
		
		//bottom.getChildren().addAll(BottomBannerDetails);
		setCenter(BottomBannerDetails);
		setMargin(BottomBannerDetails, new Insets(20, 0, 0, 0));
	}

	private void setPositions() {
		setAlignment(addCustomerBtn, Pos.CENTER_RIGHT);
		setMargin(addCustomerBtn, new Insets(0, 20, 20, 0));
	}
	
}

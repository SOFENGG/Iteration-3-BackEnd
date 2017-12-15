package view.manager.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import view.manager.final_values.Values;

public class PurchaseOrderBanner extends Banner {
	
	/* Left Section */
	private TextField supplierNameField;
	private TextField invoiceField;
	private TextField requisitionerField;
	
	/* Right Section */
	private Button suppliersBtn;
	private TextField documentTitleField;
	private DatePicker receiveDatePicker;
	
	/* Bottom Buttons */
	private Button receiveOrderBtn;
	private Button confirmOrderBtn; 
	
	public PurchaseOrderBanner() {
		super();
		updateToPurchaseOrders();
	}

	private void updateToPurchaseOrders() {
		/* Banner Title */
		bannerTitle.setText(Values.BANNER_PURCHASE_ORDER);
		initPurchaseOrders();
		initHandlers();
		//setPositions();
	}
	
	private void initHandlers() {
		suppliersBtn.setOnMouseClicked(e -> {
			SupplierPopup sp = new SupplierPopup(Values.SUPPLIER_POPUP_TITLE);
			sp.show();
		});
		
		receiveOrderBtn.setOnAction(e -> {
			ReceiveOrderPopup roP = new ReceiveOrderPopup("Receive Orders");
			roP.show();
		});
	}

	private void initPurchaseOrders() {
		/*Contains the label and textfield combinations for left box*/
		VBox[] leftCombos = new VBox[3];
		
		/*Supplier Name Combination*/
		leftCombos[0] = new VBox();
		supplierNameField = new TextField();
		supplierNameField.setEditable(false);
		leftCombos[0].getChildren().addAll(new Label("Supplier Name:"), supplierNameField);
		
		/*Invoice Combination*/
		leftCombos[1] = new VBox();
		invoiceField = new TextField();
		leftCombos[1].getChildren().addAll(new Label("Invoice No:"), invoiceField);
		
		leftCombos[2] = new VBox();
		requisitionerField = new TextField();
		leftCombos[2].getChildren().addAll(new Label("Requisitioner: "), requisitionerField);
		
		/*Contains the label and textfield combinations for right box*/
		VBox[] rightCombos = new VBox[2];
		
		/*Supplier Combination*/
		rightCombos[0] = new VBox();
		documentTitleField = new TextField();
		rightCombos[0].getChildren().addAll(new Label("Document Title:"), documentTitleField);
		
		rightCombos[1] = new VBox();
		receiveDatePicker = new DatePicker();
		rightCombos[1].getChildren().addAll(new Label("Date To Receive"), receiveDatePicker);
		
		/*Edit Button initialization*/
		suppliersBtn = new Button("Suppliers");

		/* Align Right Side */
		rightColumn.setPadding(new Insets(20, 0, 0, 0));
		
		/* Bottom Buttons */
		receiveOrderBtn = new Button("Receive Order");
		
		confirmOrderBtn = new Button("Confirm Order");
		
		/* Assembly */
		leftColumn.getChildren().addAll(leftCombos[0], leftCombos[1], leftCombos[2]);
		rightColumn.getChildren().addAll(suppliersBtn, rightCombos[0], rightCombos[1]);
		bottomButtons.getChildren().addAll(receiveOrderBtn, confirmOrderBtn);
	}
	
	private void setPositions() {
		setAlignment(confirmOrderBtn, Pos.CENTER_RIGHT);
		setMargin(confirmOrderBtn, new Insets(0, 20, 20, 0));
	}

}

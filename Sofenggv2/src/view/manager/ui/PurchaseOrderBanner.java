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
	private TextField orderIdField;
	private TextField totalPriceField;
	private DatePicker dateOrderedField;
	
	/* Right Section */
	//private Button suppliersBtn;
	private TextField supplierCodeField;
	private TextField isPendingField;
	private DatePicker receiveDatePicker;
	
	/* Bottom Buttons */
		private Button addItemBtn;
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
		setGreenButton();
		//setPositions();
	}
	
	private void initHandlers() {
		/*
		suppliersBtn.setOnMouseClicked(e -> {
			SupplierPopup sp = new SupplierPopup(Values.SUPPLIER_POPUP_TITLE);
			sp.show();
		});
		*/
		addItemBtn.setOnMouseClicked(e -> {
			//AddItemPopupView ap = new AddItemPopupView(Values.ADD_ITEM_POPUP_TITLE, mvc, 0, "supplierCode");
			//ap.show();
		});
		
	}

	private void initPurchaseOrders() {
		/*Contains the label and textfield combinations for left box*/
		VBox[] leftCombos = new VBox[3];
		
		/*Supplier Code Combination*/
		leftCombos[0] = new VBox();
		orderIdField = new TextField();
		//orderIdField.setEditable(false);
		leftCombos[0].getChildren().addAll(new Label("Order ID:"), orderIdField);
		
		/*Invoice Combination*/
		leftCombos[1] = new VBox();
		totalPriceField = new TextField();
		leftCombos[1].getChildren().addAll(new Label("Total Price:"), totalPriceField);
		
		leftCombos[2] = new VBox();
		dateOrderedField = new DatePicker();
		leftCombos[2].getChildren().addAll(new Label("Date Ordered: "), dateOrderedField);
		
		/*Contains the label and textfield combinations for right box*/
		VBox[] rightCombos = new VBox[3];
		
		rightCombos[0] = new VBox();
		supplierCodeField = new TextField();
		//supplierCodeField.setEditable(false);
		rightCombos[0].getChildren().addAll(new Label("Supplier Code:"), supplierCodeField);
		
		/*Supplier Combination*/
		rightCombos[1] = new VBox();
		isPendingField = new TextField();
		rightCombos[1].getChildren().addAll(new Label("Is Pending:"), isPendingField);
		rightCombos[1].setVisible(false);
		
		rightCombos[2] = new VBox();
		receiveDatePicker = new DatePicker();
		rightCombos[2].getChildren().addAll(new Label("Date To Receive"), receiveDatePicker);
		
		/*Edit Button initialization*/
		//suppliersBtn = new Button("Suppliers");

		/* Align Right Side */
		//rightColumn.setPadding(new Insets(20, 0, 0, 0));
		
		/* Bottom Buttons */
		addItemBtn = new Button("Add Item");
		confirmOrderBtn = new Button("Confirm Order");
		
		/* Assembly */
		leftColumn.getChildren().addAll(leftCombos[0], leftCombos[1], leftCombos[2]);
		rightColumn.getChildren().addAll(rightCombos[0], rightCombos[1],  rightCombos[2]);
		bottom.getChildren().addAll(addItemBtn, confirmOrderBtn);
	}
	
	private void setPositions() {
		setAlignment(confirmOrderBtn, Pos.CENTER_RIGHT);
		setMargin(confirmOrderBtn, new Insets(0, 20, 20, 0));
	}

}

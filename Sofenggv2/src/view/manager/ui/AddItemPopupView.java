package view.manager.ui;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import controller.ManagerViewController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import model.Database;
import model.Item;
import model.ItemOrder;
import view.View;
import view.cashier.AlertBoxPopup;

public class AddItemPopupView extends Popup implements View{
	
	public static final String KEY = "additemviewkey";
	
	@SuppressWarnings("rawtypes")
	private ObservableList<ObservableList> data;
	@SuppressWarnings("rawtypes")
	private ArrayList<TableColumn> col;
	private ObservableList<String> row;
	
	private ResultSet rs = null;
	
	private VBox layout;
		private HBox itemTypeHBox;
			private ToggleButton oldItemToggleButton;
			private ToggleButton newItemToggleButton;
			private ToggleGroup itemGroup;
		private HBox searchHBox;
			private ComboBox<String> searchColumns;
			private TextField searchField;
			private Button searchButton;
		private HBox buttonsHBox;
			private Label qty;
			private Spinner<Integer> qtySpinner;
			private Button addButton;
		private HBox newItemHBox;
			protected VBox leftColumn;
				private TextField itemCodeField;
				private TextField categoryField;
				private TextField supplierField;
				private TextField stockField;
				private TextField unitPriceSupField;
			protected VBox rightColumn;
				private TextField nameField;
				private TextField ItemDescriptionField;
				private TextField manufacturerField;
				private DatePicker datePurchasedField;
				private TextField unitPriceCustField;
			
		@SuppressWarnings("rawtypes")
		private TableView tableView;
	
	private ManagerViewController mvc;
	private int orderID;
	private String supplierCode;
	
	public AddItemPopupView(String title, ManagerViewController mvc, int orderID, String supplierCode) {
		super(title);
		this.mvc = mvc;
		this.orderID = orderID;
		this.supplierCode = supplierCode;
		
		init();
		initScene();
		initHandlers();
		
		//tries to detach view first (if there are any previous transaction view)
		Database.getInstance().detach(KEY);
		
		//attach view to database
		Database.getInstance().attach(KEY, this);
		
		//puts the inital contents of the table
		mvc.getSupplierItems(new String[]{KEY}, supplierCode);

		setScene(layout);
	}
	
	@SuppressWarnings("rawtypes")
	public void init(){
		col = new ArrayList<TableColumn>();
		data = FXCollections.observableArrayList();
	}
	
	public void initScene() {
		layout = new VBox (10);
		layout.setId("Popup");
		layout.setPadding(new Insets(10, 10, 10, 10));
		
			itemTypeHBox = new HBox(20);
		
				itemGroup = new ToggleGroup();
				
				oldItemToggleButton = new ToggleButton("Old Item");
				oldItemToggleButton.setToggleGroup(itemGroup);
				oldItemToggleButton.setSelected(true);
				
				newItemToggleButton = new ToggleButton("New Item");
				newItemToggleButton.setToggleGroup(itemGroup);
			
			itemTypeHBox.getChildren().addAll(oldItemToggleButton, newItemToggleButton);
			
			// old item
			searchHBox = new HBox (20);
			searchHBox.setAlignment(Pos.CENTER);
			
				searchColumns = new ComboBox<String> ();
				searchColumns.getStyleClass().add("ComboBox");
				searchColumns.getItems().addAll(
						"Item Code",
						"Name",
						"Description",
						"Category",
						"Manufacturer",
						"Supplier Code",
						"Stock",
						"Date Purchased",
						"Price Supplier",
						"Price Customer");
				
				searchColumns.getSelectionModel().selectFirst();
				
				searchField = new TextField ();
				searchField.setId ("TextField");
				
				searchButton = new Button ();
				searchButton.getStyleClass ().add("SearchButton");
				searchButton.setMinSize(40, 40);	
				
			searchHBox.getChildren().addAll(searchColumns, searchField, searchButton);
			
			buttonsHBox = new HBox (20);
			buttonsHBox.setAlignment(Pos.CENTER);
				
				qty = new Label("Quantity:");
				
				qtySpinner = new Spinner<Integer>(0, 999, 0, 1);
				qtySpinner.setEditable(true);
				
				addButton = new Button("Add Item");
				addButton.getStyleClass().add("GreenButton");
				
			buttonsHBox.getChildren().addAll(qty, qtySpinner, addButton);
			
			tableView = new Table();
		
			// new item
			newItemHBox = new HBox(10);
			newItemHBox.setAlignment(Pos.CENTER);
			
			leftColumn = new VBox(10);
			rightColumn = new VBox(10);
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
			
			leftColumn.getChildren().addAll(leftCombos[0], leftCombos[1], leftCombos[2] , leftCombos[3] , leftCombos[4]);
			rightColumn.getChildren().addAll(rightCombos[0], rightCombos[1], rightCombos[2], rightCombos[3], rightCombos[4]);
			
			
			newItemHBox.getChildren().addAll(leftColumn, rightColumn);
			
			
		layout.getChildren().addAll(itemTypeHBox, searchHBox, tableView, buttonsHBox);
			
		VBox.setVgrow (layout, Priority.ALWAYS);	
		
		
	}
	
	// BACKEND STUFF
	@SuppressWarnings("unchecked")
	private void initHandlers() {
		oldItemToggleButton.setOnAction(e -> {
			oldItemToggleButton.setSelected(true);
			
			if (!layout.getChildren().isEmpty())
				layout.getChildren().removeAll(layout.getChildren());
			
			layout.getChildren().addAll(itemTypeHBox, searchHBox, tableView, buttonsHBox);
			
			resizeScene();
		});
		
		newItemToggleButton.setOnAction(e -> {
			newItemToggleButton.setSelected(true);
			
			if (!layout.getChildren().isEmpty())
				layout.getChildren().removeAll(layout.getChildren());
			
			layout.getChildren().addAll(itemTypeHBox, newItemHBox, buttonsHBox);
		});
		searchButton.setOnAction(e -> {
			try{
				switch(searchColumns.getValue()){
					case "Item Code":
						mvc.searchItemsByItemCode(new String[] {KEY}, searchField.getText());
						break;
					case "Name":
						mvc.searchItemsByName(new String[] {KEY}, searchField.getText());
						break;
					case "Description":
						mvc.searchItemsByDescription(new String[] {KEY}, searchField.getText());
						break;
					case "Category":
						mvc.searchItemsByCategory(new String[] {KEY}, searchField.getText());
						break;
					case "Manufacturer":
						mvc.searchItemsByManufacturer(new String[] {KEY}, searchField.getText());
						break;
					case "Supplier Code":
						mvc.searchItemsBySupplierCode(new String[] {KEY}, searchField.getText());
						break;
					case "Stock":
						mvc.searchItemsByStock(new String[] {KEY}, Integer.parseInt(searchField.getText()));
						break;
					case "Date Purchased":
						mvc.searchItemsByDatePurchase(new String[] {KEY}, searchField.getText());
						break;
					case "Price Supplier":
						mvc.searchItemsByPriceSupplier(new String[] {KEY}, BigDecimal.valueOf(Double.parseDouble(searchField.getText())));
						break;
					case "Price Customer":
						mvc.searchItemsByPriceCustomer(new String[] {KEY}, BigDecimal.valueOf(Double.parseDouble(searchField.getText())));
						break;
				}
			}catch(NumberFormatException nfe){
				if (searchField.getText().equals(""))
					mvc.getSupplierItems(new String[]{KEY}, supplierCode);
 				else
 					new AlertBoxPopup("Input Error", "Enter a number.");
			}
		});
		addButton.setOnAction(e -> {
			if (oldItemToggleButton.isSelected()) {
				if (!tableView.getSelectionModel().getSelectedItems().isEmpty() && qtySpinner.getValue() > 0) {
					mvc.addItemOrder(new ItemOrder(orderID, ((ObservableList<String>)tableView.getSelectionModel().getSelectedItem()).get(0), qtySpinner.getValue()));
					new AlertBoxPopup("Success", "Item order made.");
				}
				if (tableView.getSelectionModel().getSelectedItems().isEmpty())
					new AlertBoxPopup("Input Error", "There is nothing selected.");
				if (qtySpinner.getValue() <= 0)
					new AlertBoxPopup("Input Error", "Invalid quantity.");
			} else {
				if(!itemCodeField.getText().equals("") && !nameField.getText().equals("") && !ItemDescriptionField.getText().equals("") &&
						!categoryField.getText().equals("") && !manufacturerField.getText().equals("") && !supplierField.getText().equals("") &&
						!stockField.getText().equals("") && datePurchasedField.getValue() != null && !unitPriceSupField.getText().equals("") && !unitPriceCustField.getText().equals("") &&
						qtySpinner.getValue() != 0){
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
					
					int quantity = qtySpinner.getValue();
						
					try{
						mvc.addItem(new Item(itemCode, name, description, category, manufacturer, supplierCode, stock, datePurchase, priceSupplier, priceCustomer));
						new AlertBoxPopup("Success", "Item added to inventory list, item order made.");
						mvc.addItemOrder(new ItemOrder(orderID, itemCode, quantity));
						
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
			}
			mvc.getCurrentPurchaseOrderItems(new String[] {PurchaseOrderView.KEY}, orderID);
			this.closePopup();
		});
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void update() {
		if(rs != Database.getInstance().getRS()){
			rs = Database.getInstance().getRS();
			if (rs == null)
				return;
			
			if (!tableView.getColumns ().isEmpty ()){
				for(int i = 0; i < col.size(); i++){
					tableView.getColumns ().removeAll (col.get(i));
				}
				col.clear();
			}
			
			if (!tableView.getItems ().isEmpty ()){
				tableView.getItems ().removeAll (row);
				data.clear();
			}
			
			try {
				for (int i = 0; i < rs.getMetaData ().getColumnCount (); i++) {
					final int j = i;
					TableColumn c = new TableColumn (rs.getMetaData ().getColumnName (i + 1));
					c.setCellValueFactory (new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>> () {
						public ObservableValue<String> call (CellDataFeatures<ObservableList, String> param) {
								return new SimpleStringProperty (param.getValue ().get (j).toString ());
						}
					});
					
					col.add(c);
					tableView.getColumns ().addAll (c);
					
				}
				
				while (rs.next()) {
					row = FXCollections.observableArrayList ();
					
					for (int i = 1; i <= rs.getMetaData ().getColumnCount (); i++) {
						String s = "";
						switch(rs.getMetaData().getColumnType(i)){
							case Types.INTEGER: s = Integer.toString(rs.getInt(i));
								break;
							case Types.DATE: Date d = rs.getDate(i);
								s = d.toString();
								break;
							case Types.VARCHAR: s = rs.getString(i);
								break;
							case Types.BIGINT: s = Integer.toString(rs.getInt(i));
								break;
							case Types.DECIMAL: s = (rs.getBigDecimal(i)).doubleValue() + "";
								break;
						}
						row.add (s);
					}
					data.add(row);
				}
				tableView.setItems(data);
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}

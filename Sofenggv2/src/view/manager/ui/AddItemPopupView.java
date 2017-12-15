package view.manager.ui;

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
import view.View;

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
			private ComboBox<String> filterComboBox;
			private TextField searchTextField;
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
		private TableView itemTable;
	
	private ManagerViewController mvc;
	private int orderID;
	
	public AddItemPopupView(String title, ManagerViewController mvc, int orderID, String supplierCode) {
		super(title);
		this.mvc = mvc;
		this.orderID = orderID;
		
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
			
				filterComboBox = new ComboBox<String> ();
				filterComboBox.getStyleClass().add("ComboBox");
				filterComboBox.getItems().add("ID");
				filterComboBox.getItems().add("Name");
				
				filterComboBox.getSelectionModel ().selectFirst ();
				
				searchTextField = new TextField ();
				searchTextField.setId ("TextField");
				
				searchButton = new Button ();
				searchButton.getStyleClass ().add("SearchButton");
				searchButton.setMinSize(40, 40);	
				
			searchHBox.getChildren().addAll(filterComboBox, searchTextField, searchButton);
			
			buttonsHBox = new HBox (20);
			buttonsHBox.setAlignment(Pos.CENTER);
				
				qty = new Label("Quantity:");
				
				qtySpinner = new Spinner<Integer>(0, 999, 0, 1);
				qtySpinner.setEditable(true);
				
				addButton = new Button("Add Item");
				
			buttonsHBox.getChildren().addAll(qty, qtySpinner, addButton);
			
			itemTable = new Table();
		
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
			
			
		layout.getChildren().addAll(itemTypeHBox, searchHBox, itemTable, buttonsHBox);
			
		VBox.setVgrow (layout, Priority.ALWAYS);	
		
		
	}
	
	// BACKEND STUFF
	private void initHandlers() {
		oldItemToggleButton.setOnAction(e -> {
			oldItemToggleButton.setSelected(true);
			
			if (!layout.getChildren().isEmpty())
				layout.getChildren().removeAll(layout.getChildren());
			
			layout.getChildren().addAll(itemTypeHBox, searchHBox, itemTable, buttonsHBox);
			
			resizeScene();
		});
		
		newItemToggleButton.setOnAction(e -> {
			newItemToggleButton.setSelected(true);
			
			if (!layout.getChildren().isEmpty())
				layout.getChildren().removeAll(layout.getChildren());
			
			layout.getChildren().addAll(itemTypeHBox, newItemHBox, buttonsHBox);
		});
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void update() {
		if(rs != Database.getInstance().getRS()){
			rs = Database.getInstance().getRS();
			if (rs == null)
				return;
			
			if (!itemTable.getColumns ().isEmpty ()){
				for(int i = 0; i < col.size(); i++){
					itemTable.getColumns ().removeAll (col.get(i));
				}
				col.clear();
			}
			
			if (!itemTable.getItems ().isEmpty ()){
				itemTable.getItems ().removeAll (row);
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
					itemTable.getColumns ().addAll (c);
					
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
				itemTable.setItems(data);
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}

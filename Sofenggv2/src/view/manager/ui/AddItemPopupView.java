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
		private VBox newItemVBox;
			private Label itemCodeLabel;
			private TextField itemCodeTextField;
			private Label descriptionLabel;
			private TextField descriptionTextField;
			private Label uomLabel;
			private TextField uomTextField;
			private Label unitPriceLabel;
			private TextField unitPriceTextField;
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
			newItemVBox = new VBox(20);
				itemCodeLabel = new Label("Item Code:");
				itemCodeTextField = new TextField();
				descriptionLabel = new Label("Description");
				descriptionTextField = new TextField();
				uomLabel = new Label("UOM:");
				uomTextField = new TextField();
				unitPriceLabel = new Label("Unit Price:");
				unitPriceTextField = new TextField();
			
			newItemVBox.getChildren().addAll(itemCodeLabel, itemCodeTextField, descriptionLabel, descriptionTextField, uomLabel, uomTextField, unitPriceLabel, unitPriceTextField);
			
			
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
			
			layout.getChildren().addAll(itemTypeHBox, newItemVBox, buttonsHBox);
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

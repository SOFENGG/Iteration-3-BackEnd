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
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import model.Database;
import view.View;
import view.cashier.AlertBoxPopup;

public class TransactionDetailsPopupView extends Popup implements View {
	public static final String KEY = "transactiondetailsviewkey";
	
	@SuppressWarnings("rawtypes")
	private ObservableList<ObservableList> data;
	@SuppressWarnings("rawtypes")
	private ArrayList<TableColumn> col;
	private ObservableList<String> row;
	
	private ResultSet rs = null;

	private VBox layout;
	private HBox searchHBox;
		private ComboBox<String> searchColumns;
		private TextField searchField;
		private Button searchButton;
	private HBox labelsHBox;
		private Label dateTransactedLabel;
		private Label totalQtyLabel;
		private Label totalCostLabel;
	@SuppressWarnings("rawtypes")
	private TableView itemTable;
	
	private ManagerViewController mvc;
	private int transactionID;

	public TransactionDetailsPopupView(String title, ManagerViewController mvc, int transactionID) {
		super(title);
		this.mvc = mvc;
		this.transactionID = transactionID;
		
		init();
		initScene();
		initHandlers();
		
		//tries to detach view first (if there are any previous transaction view)
		Database.getInstance().detach(KEY);
		
		//attach view to database
		Database.getInstance().attach(KEY, this);
		
		//puts the inital contents of the table
		mvc.getTransactionDetails(new String[]{KEY}, transactionID);
		
		setScene(layout);
	}
	
	@SuppressWarnings("rawtypes")
	public void init(){
		col = new ArrayList<TableColumn>();
		data = FXCollections.observableArrayList();
	}
	
	public void initScene() {
		layout = new VBox (20);
		layout.setId("Popup");
		
			searchHBox = new HBox (30);
			searchHBox.setAlignment(Pos.CENTER);

			searchColumns = new ComboBox<String> ();
			searchColumns.getStyleClass().add("ComboBox");
			searchColumns.getItems().add("Sale ID");
			searchColumns.getItems().add("Item Code");
			searchColumns.getItems().add("Type");
			searchColumns.getItems().add("Quantity Sold");
			searchColumns.getItems().add("Original Price");
			searchColumns.getItems().add("Price Sold");
			
			searchColumns.getSelectionModel().selectFirst();
				
				searchField = new TextField ();
				searchField.setId ("TextField");
				
				searchButton = new Button ();
				searchButton.getStyleClass ().add("SearchButton");
				searchButton.setMinSize(40, 40);
					
			searchHBox.getChildren().addAll(searchColumns, searchField, searchButton);
			
			itemTable = new Table();
			
			labelsHBox = new HBox(30);
			
				dateTransactedLabel = new Label("Date transacted: ");
				totalQtyLabel = new Label("Total Quantity: ");
				totalCostLabel = new Label("Total Cost: ");
				
			labelsHBox.getChildren().addAll(dateTransactedLabel, totalQtyLabel, totalCostLabel);
			
		layout.getChildren().addAll(searchHBox, itemTable, labelsHBox);
		
		VBox.setVgrow(layout, Priority.ALWAYS);
	}
	
	// BACKEND STUFF
	private void initHandlers() {
		searchButton.setOnAction(e -> {
			try {
 				switch (searchColumns.getValue()) {
 				case "Sale ID":
 					mvc.searchTransactionDetailsBySaleID(new String[] {KEY}, transactionID, Integer.parseInt(searchField.getText()));
 					break;
 				case "Item Code":
 					mvc.searchTransactionDetailsByItemCode(new String[] {KEY}, transactionID, searchField.getText());
 					break;
 				case "Type":
 					mvc.searchTransactionDetailsByType(new String[] {KEY}, transactionID, searchField.getText());
 					break;
 				case "Quantity Sold":
 					mvc.searchTransactionDetailsByQuantitySold(new String[] {KEY}, transactionID, Integer.parseInt(searchField.getText()));
 					break;
 				case "Original Price":
 					mvc.searchTransactionDetailsByOriginalPrice(new String[] {KEY}, transactionID, BigDecimal.valueOf(Double.parseDouble(searchField.getText())));
 					break;
 				case "Price Sold":
 					mvc.searchTransactionDetailsByPriceSold(new String[] {KEY}, transactionID, BigDecimal.valueOf(Double.parseDouble(searchField.getText())));
 					break;
 				}
			} catch (NumberFormatException nfe) {
				if (searchField.getText().equals(""))
 					mvc.getCurrentTransactions(new String[] {KEY});
 				else
 					new AlertBoxPopup("Input Error", "Enter a number.");
			}
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

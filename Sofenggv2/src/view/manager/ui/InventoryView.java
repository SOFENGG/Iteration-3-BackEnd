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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;
import model.Database;
import view.View;
import view.cashier.AlertBoxPopup;

public class InventoryView extends MainView implements View {
	public static final String KEY = "managerinventoryviewkey";
	
	@SuppressWarnings("rawtypes")
	private ObservableList<ObservableList> data;
	@SuppressWarnings("rawtypes")
	private ArrayList<TableColumn> col;
	private ObservableList<String> row;
	
	private ResultSet rs = null;
	
	public InventoryView(ManagerViewController mvc) {
		super(mvc);
		init();
		initHandler();
		setUniqueToViewTableAndFilter();
	}
	
	@SuppressWarnings("rawtypes")
	public void init(){
		col = new ArrayList<TableColumn>();
		data = FXCollections.observableArrayList();
	}
	
	public void initHandler(){
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
 					mvc.getAllItems(new String[] {KEY});
 				else
 					new AlertBoxPopup("Input Error", "Enter a number.");
			}
		});
	}
	
	/* For the Back End Developers */
	/* This function will set the values inside the searchColumns ComboBox
	 * and the Columns for the Table unique to this View only
	 */
	private void setUniqueToViewTableAndFilter() {
		searchColumns.setItems(fillComboBox());
		//tableView.getColumns().setAll(fillColumns());
		searchColumns.getSelectionModel().selectFirst();
	}
	
	/* This function is for the Back End Developers */
	private ObservableList<String> fillComboBox() {
		ObservableList<String> list = FXCollections.observableArrayList();
		
		list.addAll("Item Code",
					"Name",
					"Description",
					"Category",
					"Manufacturer",
					"Supplier Code",
					"Stock",
					"Date Purchased",
					"Price Supplier",
					"Price Customer");
		
		return list;
	}
	
	
	/* This function is for the Back End Developers 
	private TableColumn<Object, ?> fillColumns() {
		
		return null;
	}*/

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

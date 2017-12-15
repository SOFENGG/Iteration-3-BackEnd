package view.manager.ui;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import controller.ManagerViewController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;
import model.Database;
import view.View;
import view.cashier.AlertBoxPopup;
import view.manager.final_values.Values;

public class PurchaseOrderView extends MainView implements View{
	public static final String KEY = "supplierviewkey";
	
	@SuppressWarnings("rawtypes")
	private ObservableList<ObservableList> data;
	@SuppressWarnings("rawtypes")
	private ArrayList<TableColumn> col;
	private ObservableList<String> row;
	
	private ResultSet rs = null;
	
	private TabPane tabbedPane;
		private Tab currentTab;
			private Button addItemBtn;
			private Button removeItemBtn;
			private Button clearAllBtn;
		private Tab pendingTab;
			private Button receiveOrderBtn;
		private Tab receivedTab;

	private int orderID;
	private String supplierCode;
	
	public PurchaseOrderView(ManagerViewController mvc) {
		super(mvc);
		addUniqueToViewNodes();
		setUniqueToViewTableAndFilter();
		init();
		initHandlers();
	}
	
	@SuppressWarnings("rawtypes")
	public void init(){
		col = new ArrayList<TableColumn>();
		data = FXCollections.observableArrayList();
	}
	
	private void initHandlers() {
		addItemBtn.setOnMouseClicked(e -> {
			AddItemPopupView ap = new AddItemPopupView(Values.ADD_ITEM_POPUP_TITLE, mvc, orderID, supplierCode);
			ap.show();
		});
		
		receiveOrderBtn.setOnAction(e -> {
			ReceiveOrderPopup roP = new ReceiveOrderPopup("Receive Orders");
			roP.show();
		});
		
		searchButton.setOnAction(e -> {
			try {
				if (tabbedPane.getSelectionModel().getSelectedIndex() == 1) {
					//pending tab
					switch (searchColumns.getValue()) {
					case "Order ID":
						mvc.searchPendingPurchaseOrdersByOrderID(new String[] {KEY}, Integer.parseInt(searchField.getText()));
					break;
					case "Supplier Code":
						mvc.searchPendingPurchaseOrdersBySupplierCode(new String[] {KEY}, searchField.getText());
						break;
					case "Total Price":
						mvc.searchPendingPurchaseOrdersByTotalPrice(new String[] {KEY}, BigDecimal.valueOf(Double.parseDouble(searchField.getText())));
						break;
					case "Date Ordered":
						mvc.searchPendingPurchaseOrdersByDateOrdered(new String[] {KEY}, searchField.getText());
						break;
					case "Date Received":
						mvc.searchPendingPurchaseOrdersByDateReceived(new String[] {KEY}, searchField.getText());
						break;
					}
				} else {
					//received tab
					switch (searchColumns.getValue()) {
					case "Order ID":
						mvc.searchReceivedPurchaseOrdersByOrderID(new String[] {KEY}, Integer.parseInt(searchField.getText()));
					break;
					case "Supplier Code":
						mvc.searchReceivedPurchaseOrdersBySupplierCode(new String[] {KEY}, searchField.getText());
						break;
					case "Total Price":
						mvc.searchReceivedPurchaseOrdersByTotalPrice(new String[] {KEY}, BigDecimal.valueOf(Double.parseDouble(searchField.getText())));
						break;
					case "Date Ordered":
						mvc.searchReceivedPurchaseOrdersByDateOrdered(new String[] {KEY}, searchField.getText());
						break;
					case "Date Received":
						mvc.searchReceivedPurchaseOrdersByDateReceived(new String[] {KEY}, searchField.getText());
						break;
					}
				}
			}  catch (NumberFormatException nfe) {
				if (searchField.getText().equals(""))
					switch (tabbedPane.getSelectionModel().getSelectedIndex()) {
					case 1:
						//pending tab
						mvc.getPendingPurchaseOrders(new String[] {KEY});
						break;
					case 2:
						//received tab
						mvc.getReceivedPurchaseOrders(new String[] {KEY});
						break;
					}
 				else
 					new AlertBoxPopup("Input Error", "Enter a number.");
			}
		});
	}
	
	private void addUniqueToViewNodes() {
		
		/* This View has buttons under the table */
		
		tabbedPane = new TabPane();
		
		currentTab = new Tab();
		currentTab.setText("Current");
		currentTab.setClosable(false);
		currentTab.setContent(tableView);
		
		pendingTab = new Tab();
		pendingTab.setText("Pending");
		pendingTab.setClosable(false);
		
		receivedTab = new Tab();
		receivedTab.setText("Received");
		receivedTab.setClosable(false);
		
		tabbedPane.getTabs().addAll(currentTab, pendingTab, receivedTab);
		setCenter(tabbedPane);
		setTop(null);
		
		/* Button Initialization */
		addItemBtn = new Button("Add Item");
		
		removeItemBtn = new Button("Remove Item");
		
		clearAllBtn = new Button("Clear All");
		
		receiveOrderBtn = new Button("Receive Order");
		
		/* Assembly */
		actionButtons.getChildren().addAll(addItemBtn, removeItemBtn, clearAllBtn);
		
		actionButtons.setPrefHeight(Values.ACTION_BUTTONS_PREF_WIDTH);
		
		tabbedPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
			@Override
			public void changed(ObservableValue<? extends Tab> arg0, Tab arg1, Tab arg2) {
				if (tabbedPane.getSelectionModel().getSelectedIndex() == 0) {
					/* Table Switching */
					mvc.getFilteredTransactions(new String[] {KEY});
					receivedTab.setContent(null);
					pendingTab.setContent(null);
					currentTab.setContent(tableView);

					//tableView.getColumns().setAll(fillColumns());
					
					setTop(null);
					actionButtons.getChildren().addAll(addItemBtn, removeItemBtn, clearAllBtn);
					actionButtons.getChildren().remove(receiveOrderBtn);
					
					/* Banner Switching */
					ManagerView.reinitBanner(05);
					
				} else  if (tabbedPane.getSelectionModel().getSelectedIndex() == 1){
					mvc.getCurrentTransactions(new String[] {KEY});
					currentTab.setContent(null);
					receivedTab.setContent(null);
					pendingTab.setContent(tableView);
					//tableView.getColumns().setAll(fillColumns());
					
					setTop(filterOptions);
					actionButtons.getChildren().removeAll(addItemBtn, removeItemBtn, clearAllBtn);
					actionButtons.getChildren().addAll(receiveOrderBtn);
					
					/* Banner Switching */
					ManagerView.reinitBanner(00);
					mvc.getPendingPurchaseOrders(new String[] {KEY});
					
					
				} else if (tabbedPane.getSelectionModel().getSelectedIndex() == 2) {
					currentTab.setContent(null);
					pendingTab.setContent(null);
					receivedTab.setContent(tableView);
					
					setTop(filterOptions);
					actionButtons.getChildren().removeAll(addItemBtn, removeItemBtn, clearAllBtn);
					actionButtons.getChildren().remove(receiveOrderBtn);
					
					ManagerView.reinitBanner(00);
					mvc.getReceivedPurchaseOrders(new String[] {KEY});
				}
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
		
		list.addAll("Order ID",
					"Supplier Code",
					"Total Price",
					"Date Ordered",
					"Date Received");
		
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

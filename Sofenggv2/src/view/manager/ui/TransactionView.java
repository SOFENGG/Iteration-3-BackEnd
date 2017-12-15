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
import javafx.event.EventHandler;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import model.Database;
import view.View;
import view.cashier.AlertBoxPopup;

public class TransactionView extends MainView implements View{
	public static final String KEY = "transactionviewkey";
	
	@SuppressWarnings("rawtypes")
	private ObservableList<ObservableList> data;
	@SuppressWarnings("rawtypes")
	private ArrayList<TableColumn> col;
	private ObservableList<String> row;
	
	private ResultSet rs = null;
	private static boolean isToday;
	
	private TabPane tabbedPane;
		private Tab todayTab;
		private Tab historyTab;
	
	public TransactionView(ManagerViewController mvc) {
		super(mvc);
		init();
		initHandler();
		addUniqueToViewNodes();
		setUniqueToViewTableAndFilter();
	}
	
	@SuppressWarnings("rawtypes")
	public void init(){
		col = new ArrayList<TableColumn>();
		data = FXCollections.observableArrayList();
	}
	
	public void initHandler(){
		searchButton.setOnAction(e -> {
			try {
				if (isToday) {
					//monitor tab
 					switch (searchColumns.getValue()) {
 					case "Transaction ID":
 						mvc.searchCurrentTransactionsByTransactionID(new String[] {KEY}, Integer.parseInt(searchField.getText()));
 						break;
 					case "User ID":
 						mvc.searchCurrentTransactionsByUserID(new String[] {KEY}, Integer.parseInt(searchField.getText()));
 						break;
 					case "Transaction Type":
 						mvc.searchCurrentTransactionsByTransactionType(new String[] {KEY}, searchField.getText());
 						break;
 					case "Is Loan":
 						mvc.searchCurrentTransactionsByIsLoan(new String[] {KEY}, Integer.parseInt(searchField.getText()));
 						break;
 					case "Date Sold":
 						mvc.searchCurrentTransactionsByDateSold(new String[] {KEY}, searchField.getText());
 						break;
 					case "Total Cost":
 						mvc.searchCurrentTransactionsByTotalPrice(new String[] {KEY}, BigDecimal.valueOf(Double.parseDouble(searchField.getText())));
 						break;
 					}
 				}
 				else {
 					//history tab
 					switch (searchColumns.getValue()) {
 					case "Transaction ID":
 						mvc.searchFilteredTransactionsByTransactionID(new String[] {KEY}, Integer.parseInt(searchField.getText()));
 						break;
 					case "User ID":
 						mvc.searchFilteredTransactionsByUserID(new String[] {KEY}, Integer.parseInt(searchField.getText()));
 						break;
 					case "Transaction Type":
 						mvc.searchFilteredTransactionsByTransactionType(new String[] {KEY}, searchField.getText());
 						break;
 					case "Is Loan":
 						mvc.searchFilteredTransactionsByIsLoan(new String[] {KEY}, Integer.parseInt(searchField.getText()));
 						break;
 					case "Date Sold":
 						mvc.searchFilteredTransactionsByDateSold(new String[] {KEY}, searchField.getText());
 						break;
 					case "Total Cost":
 						mvc.searchFilteredTransactionsByTotalPrice(new String[] {KEY}, BigDecimal.valueOf(Double.parseDouble(searchField.getText())));
 						break;
 					}
 				}
			} catch (NumberFormatException nfe) {
				if (searchField.getText().equals("")) {
 					if (isToday)
 						mvc.getCurrentTransactions(new String[] {KEY});
 					else
 						mvc.getFilteredTransactions(new String[] {KEY});
 				} else
 					new AlertBoxPopup("Input Error", "Enter a number.");
			}
 		});
		
		//double click event to launch transaction details popup
		tableView.setOnMousePressed(new EventHandler<MouseEvent>() {
		    @SuppressWarnings("unchecked")
			public void handle(MouseEvent event) {
		        if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
		            TransactionDetailsPopupView tdPopup = new TransactionDetailsPopupView("Transaction Details", mvc, Integer.parseInt(((ObservableList<String>)tableView.getSelectionModel().getSelectedItem()).get(0)));
                    tdPopup.show();
		        }
		    }
		});
	}
	
	private void addUniqueToViewNodes() {
		/* This View has a filter Button */
		//addFilterButton();
		addTabbedPane();
	}
	
	private void addTabbedPane() {
		/* Tabbed Pane Initialization */
		tabbedPane = new TabPane();
		
		/* Tabs Initialization */
		todayTab = new Tab();
		todayTab.setText("Today");
		todayTab.setClosable(false);
		todayTab.setContent(tableView);
		
		historyTab = new Tab();
		historyTab.setText("History");
		historyTab.setClosable(false);
		
		isToday = true;
		
		/* Assembly */
		tabbedPane.getTabs().addAll(todayTab, historyTab);
		setCenter(tabbedPane);
		
		/* This Listener switches which tab holds tableView
		 * Both Tabs use the same table but with differing values each tab
		 */
		tabbedPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
			@Override
			public void changed(ObservableValue<? extends Tab> arg0, Tab arg1, Tab arg2) {
				if (isToday) {
					/* Table Switching */
					mvc.getFilteredTransactions(new String[] {KEY});
					todayTab.setContent(null);
					historyTab.setContent(tableView);
					//tableView.getColumns().setAll(fillColumns());
					isToday = false;
					
					/* Banner Switching */
					ManagerView.reinitBanner(02);
					
					addFilterButton();
					
				} else {
					mvc.getCurrentTransactions(new String[] {KEY});
					historyTab.setContent(null);
					todayTab.setContent(tableView);
					//tableView.getColumns().setAll(fillColumns());
					isToday = true;
					
					/* Banner Switching */
					ManagerView.reinitBanner(00);
					
					removeFilterButton();
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
		
		list.addAll("Transaction ID",
					"User ID",
					"Transaction Type", 
					"Is Loan",
					"Date Sold",
					"Total Price");
		
		return list;
	}
	
	
	/* This function is for the Back End Developers */
	/*private TableColumn<Object, ?> fillColumns() {
		
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

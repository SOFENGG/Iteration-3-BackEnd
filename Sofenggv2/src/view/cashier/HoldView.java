package view.cashier;

import java.sql.ResultSet;
import java.util.ArrayList;

import controller.CashierViewController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.util.Callback;
import model.Cart;
import view.View;

public class HoldView extends HBox implements View {
	public static final String KEY = "holdview";
	//indexes
	public static final int NUMBER = 0;
	private static final int OWNER = 1;
	private static final int TRANSACTION = 2;
	private static final int DATE = 3;
	private static final int TIME = 4;
	private static final int TOTAL_PRICE = 5;
	
	private CashierViewController cvc;
	
	private TableView tableView;
	private ObservableList<ObservableList> data;
	
	private ArrayList<TableColumn> col;
	private ObservableList<String> row;
	
	private ResultSet rs = null;

	public HoldView (CashierViewController cvc) {
		super ();
		
		this.cvc = cvc;
		
		initCV ();
	}
	
	private void initCV() {
		//setId ("Border");
		
		tableView = new TableView();
		//tableView.setId("TableView");
		col = new ArrayList<TableColumn>();
		data = FXCollections.observableArrayList();
		
		setHgrow(tableView, Priority.ALWAYS);
		getChildren().addAll(tableView);
		
		//add columns
		addTableColumn("No.", NUMBER);
		addTableColumn("Owner", OWNER);
		addTableColumn("Transaction", TRANSACTION);
		addTableColumn("Date", DATE);
		addTableColumn("Time", TIME);
		addTableColumn("Total Price", TOTAL_PRICE);
	}
	
	public ObservableList<String> getSelectedItem(){
		return (ObservableList<String>) tableView.getSelectionModel().getSelectedItem();
	}
	
	@Override
	public void update() {
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
		
		addTableColumn("No.", NUMBER);
		addTableColumn("Owner", OWNER);
		addTableColumn("Transaction", TRANSACTION);
		addTableColumn("Date", DATE);
		addTableColumn("Time", TIME);
		addTableColumn("Total Price", TOTAL_PRICE);

		int index = 1;
		for(Cart cart : cvc.getCartBuffer()){
			row = FXCollections.observableArrayList ();
			row.add(index + "");
			row.add(cart.getOwner());
			row.add(cart.getTransactionType());
			row.add(cart.getDate());
			row.add(cart.getTime());
			row.add(cart.getTotalPrice().toString());
			data.add(row);
			index++;
		}
		
		tableView.setItems(data);
	}
	
	public void addTableColumn(String column, final int index){
		TableColumn c = new TableColumn (column);
		c.setCellValueFactory (new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>> () {
			public ObservableValue<String> call (CellDataFeatures<ObservableList, String> param) {
					return new SimpleStringProperty (param.getValue ().get (index).toString ());
			}
		});
		
		col.add(c);
		tableView.getColumns ().addAll (c);
	}
	
}

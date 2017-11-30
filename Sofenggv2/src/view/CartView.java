package view;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;

import controller.CashierViewController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;
import model.CartItem;

public class CartView extends ScrollPane implements View  {
	public static final String KEY = "cart";
	
	private CashierViewController cvc;
	
	private TableView tableView;
	private ObservableList<ObservableList> data;
	
	private ArrayList<TableColumn> col;
	private ObservableList<String> row;
	
	private ResultSet rs = null;
	
	public CartView (CashierViewController cvc) {
		super ();
		
		this.cvc = cvc;
		
		initIV ();
	}
	
	private void initIV() {
		setHbarPolicy (ScrollPane.ScrollBarPolicy.AS_NEEDED);
		setVbarPolicy (ScrollPane.ScrollBarPolicy.AS_NEEDED);
		//setId ("Border");
		
		tableView = new TableView();
		//tableView.setId("TableView");
		col = new ArrayList<TableColumn>();
		data = FXCollections.observableArrayList();

		setContent (tableView);
		
		//add columns
		addTableColumn("item_code", 0);
		addTableColumn("name", 1);
		addTableColumn("quantity", 2);
		addTableColumn("unit price", 3);
		addTableColumn("total price", 4);
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
		
		addTableColumn("item_code", 0);
		addTableColumn("name", 1);
		addTableColumn("quantity", 2);
		addTableColumn("unit price", 3);
		addTableColumn("total price", 4);
		
		for(CartItem item : cvc.getCartItems()){
			row = FXCollections.observableArrayList ();
			row.add(item.getItemCode());
			row.add(item.getName());
			row.add(item.getQuantity()+"");
			row.add(item.getOriginalPrice().toString());
			row.add(item.getPriceSold().multiply(BigDecimal.valueOf(item.getQuantity())).toString());
			data.add(row);
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

package view.cashier;

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
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.util.Callback;
import model.CartItem;
import model.CartItemType;
import view.View;

public class CartView extends HBox implements View  {
	public static final String KEY = "cart";
	//column indexes
	public static final int TYPE = 0;
	public static final int ITEM_CODE = 1;
	public static final int NAME = 2;
	public static final int QUANTITY = 3;
	public static final int UNIT_PRICE = 4;
	public static final int TOTAL_PRICE = 5;
	
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
		//setId ("Border");
		
		tableView = new TableView();
		//tableView.setId("TableView");
		col = new ArrayList<TableColumn>();
		data = FXCollections.observableArrayList();

		setHgrow(tableView, Priority.ALWAYS);
		getChildren().addAll(tableView);
		
		//add columns
		addTableColumn("type", TYPE);
		addTableColumn("item_code", ITEM_CODE);
		addTableColumn("name", NAME);
		addTableColumn("quantity", QUANTITY);
		addTableColumn("unit price", UNIT_PRICE);
		addTableColumn("total price", TOTAL_PRICE);
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

		addTableColumn("type", TYPE);
		addTableColumn("item_code", ITEM_CODE);
		addTableColumn("name", NAME);
		addTableColumn("quantity", QUANTITY);
		addTableColumn("unit price", UNIT_PRICE);
		addTableColumn("total price", TOTAL_PRICE);
		
		for(CartItem item : cvc.getCart().getCartItems()){
			row = FXCollections.observableArrayList ();
			
			row.add(item.getType().toString());
			
			if(item.getType() == CartItemType.ITEM){
				row.add(item.getItemCode());
			}else{
				row.add(item.getServiceId() + "");
			}
			
			row.add(item.getName());
			row.add(item.getQuantity()+"");
			row.add(item.getPriceSold().toString());
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

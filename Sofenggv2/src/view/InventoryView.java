package view;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import controller.CashierViewController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TableRow;
import javafx.util.Callback;
import model.Database;
import model.Item;

public class InventoryView extends ScrollPane implements View {
	public static final String KEY = "inventory";
	//column indexes
	public static int ITEM_CODE;
	public static int NAME;
	public static int PRICE;
	
	private CashierViewController cvc;
	
	private TableView tableView;
	private ObservableList<ObservableList> data;
	
	private ArrayList<TableColumn> col;
	private ObservableList<String> row;
	
	private ResultSet rs = null;
	
	public InventoryView (CashierViewController cvc) {
		super ();
		
		this.cvc = cvc;
		
		initIV ();
		initHandlers ();
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
	}
	
	private void initHandlers() {
		// TODO Auto-generated method stub
		/*tableView.setOnMousePressed(e -> {
			if (e.isPrimaryButtonDown() && e.getClickCount() == 2) {
				System.out.println(tableView.getSelectionModel().getSelectedItem());
			}
		});*/
	}
	
	public ObservableList<String> getSelectedItem(){
		return (ObservableList<String>) tableView.getSelectionModel().getSelectedItem();
	}

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
					
					if(rs.getMetaData ().getColumnName (i + 1).equals(Item.COLUMN_ITEM_CODE)){
						ITEM_CODE = i;
					}else if(rs.getMetaData ().getColumnName (i + 1).equals(Item.COLUMN_NAME)){
						NAME = i;
					}else if(rs.getMetaData ().getColumnName (i + 1).equals(Item.COLUMN_PRICE_CUSTOMER)){
						PRICE = i;
					}
					
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
				tableView.setItems (data);
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}

package view;

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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;
import model.Database;

public class InventoryView extends ScrollPane implements View {

	private CashierViewController cvc;
	
	private TableView tableView;
	private ObservableList<ObservableList> data;
	
	private ArrayList<TableColumn> col;
	private ObservableList<String> row;
	
	private ResultSet rs = null;
	
	public InventoryView (CashierViewController cvc) {
		super ();
		
		Database.getInstance().attach(this);
		
		this.cvc = cvc;
		
		initIV ();
	}
	
	private void initIV() {
		setHbarPolicy (ScrollPane.ScrollBarPolicy.AS_NEEDED);
		setVbarPolicy (ScrollPane.ScrollBarPolicy.AS_NEEDED);
		//setId ("Border");
		setContent (tableView);
		
		tableView = new TableView();
		//tableView.setId("TableView");
		col = new ArrayList<TableColumn>();
		data = FXCollections.observableArrayList();
	}

	@Override
	public void update() {
		//rs = Database.getInstance().getRS();
		
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
				//System.out.println ("Column " + i + " added");
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
					}
					row.add (s);
				}
				
				data.add(row);
				//System.out.println ("Row " + row + " added");
				
			}
			//Database.getInstance().queryClose();
			tableView.setItems (data);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}

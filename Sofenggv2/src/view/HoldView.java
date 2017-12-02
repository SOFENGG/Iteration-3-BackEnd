package view;

import java.sql.ResultSet;
import java.util.ArrayList;

import controller.CashierViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class HoldView extends ScrollPane implements View {

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
		setHbarPolicy (ScrollPane.ScrollBarPolicy.AS_NEEDED);
		setVbarPolicy (ScrollPane.ScrollBarPolicy.AS_NEEDED);
		//setId ("Border");
		
		tableView = new TableView();
		//tableView.setId("TableView");
		col = new ArrayList<TableColumn>();
		data = FXCollections.observableArrayList();
		
		setContent (tableView);
	}
	
	@Override
	public void update() {
		
	}
	
}

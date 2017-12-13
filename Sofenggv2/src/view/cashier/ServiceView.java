package view.cashier;

import java.sql.ResultSet;
import java.util.ArrayList;

import controller.CashierViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import view.View;

public class ServiceView extends HBox implements View{

	private CashierViewController cvc;
	
	private TableView tableView;
	private ObservableList<ObservableList> data;
	
	private ArrayList<TableColumn> col;
	private ObservableList<String> row;
	
	private ResultSet rs = null;
	
	public ServiceView (CashierViewController cvc) {
		super ();
		
		this.cvc = cvc;
		
		initSV ();
		initHandlers ();
	}

	private void initSV() {
		//setId ("Border");
		
		tableView = new TableView();
		//tableView.setId("TableView");
		col = new ArrayList<TableColumn>();
		data = FXCollections.observableArrayList();

		setHgrow(tableView, Priority.ALWAYS);
		getChildren().addAll(tableView);
	}

	private void initHandlers() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
}

package view;

import java.sql.ResultSet;
import java.util.ArrayList;

import controller.CashierViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class EndOfDayView extends HBox implements View {
	public static final String KEY = "endofday";

	private CashierViewController cvc;
	
	private TableView tableView;
	private ObservableList<ObservableList> data;
	
	private ArrayList<TableColumn> col;
	private ObservableList<String> row;
	
	private ResultSet rs = null;
	
	public EndOfDayView (CashierViewController cvc) {
		super ();
		
		this.cvc = cvc;
		
		initEOD ();
	}
	
	private void initEOD() {		
		tableView = new TableView();
		//tableView.setId("TableView");
		col = new ArrayList<TableColumn>();
		data = FXCollections.observableArrayList();
		
		setHgrow(tableView, Priority.ALWAYS);
		getChildren().addAll(tableView);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

}

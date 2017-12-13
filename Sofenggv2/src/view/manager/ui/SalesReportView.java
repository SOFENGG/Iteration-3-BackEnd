package view.manager.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import view.View;
import view.manager.final_values.Values;

public class SalesReportView extends MainView implements View {
	
	private Button createReportBtn;
	private Button reportDetailsBtn;
	
	public SalesReportView() {
		super();
		addUniqueToViewNodes();
		setUniqueToViewTableAndFilter();
	}
	
	private void addUniqueToViewNodes() {
		/* This View has a Filter Button and Buttons below the table */
		/* Adding Filter Button */
		addFilterButton();
		
		/* Button Initialization */
		//This Button Creates a Report
		createReportBtn = new Button("Create Report");
		
		// This Button views the currently selected report in more detail
		reportDetailsBtn = new Button("Report Details");
		
		/* Assembly */
		actionButtons.getChildren().addAll(createReportBtn, reportDetailsBtn);
		
		actionButtons.setPrefHeight(Values.ACTION_BUTTONS_PREF_WIDTH);
	}
	
	/* For the Back End Developers */
	/* This function will set the values inside the searchColumns ComboBox
	 * and the Columns for the Table unique to this View only
	 */
	private void setUniqueToViewTableAndFilter() {
		searchColumns.setItems(fillComboBox());
		//tableView.getColumns().setAll(fillColumns());
	}
	
	/* This function is for the Back End Developers */
	private ObservableList<String> fillComboBox() {
		ObservableList<String> list = FXCollections.observableArrayList();
		
		/* Test Cases */
			list.addAll("Item Code", "Description");
		
		return list;
	}
	
	
	/* This function is for the Back End Developers */
	private TableColumn<Object, ?> fillColumns() {
		
		return null;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
}

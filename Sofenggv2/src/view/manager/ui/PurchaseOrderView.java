package view.manager.ui;

import controller.ManagerViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import view.View;
import view.manager.final_values.Values;

public class PurchaseOrderView extends MainView implements View{
	
	private Button addItemBtn;
	private Button removeItemBtn;
	private Button clearAllBtn;
	
	public PurchaseOrderView(ManagerViewController mvc) {
		super(mvc);
		setUniqueToViewTableAndFilter();
		initHandlers();
	}
	
	private void initHandlers() {
		addItemBtn.setOnMouseClicked(e -> {
			AddItemPopup ap = new AddItemPopup(Values.ADD_ITEM_POPUP_TITLE);
			ap.show();
		});
	}
	
	private void addUniqueToViewNodes() {
		
		/* This View has buttons under the table */
		/* Button Initialization */
		addItemBtn = new Button("Add Item");
		
		removeItemBtn = new Button("Remove Item");
		
		clearAllBtn = new Button("Clear All");
		
		/* Assembly */
		actionButtons.getChildren().addAll(addItemBtn, removeItemBtn, clearAllBtn);
		
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

package view.manager.ui;

import controller.ManagerViewController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import view.View;

public class TransactionView extends MainView implements View{
	private static boolean isToday;
	
	private TabPane tabbedPane;
		private Tab todayTab;
		private Tab historyTab;
	
	public TransactionView() {
		super();
		addUniqueToViewNodes();
		setUniqueToViewTableAndFilter();
	}
	
	private void addUniqueToViewNodes() {
		/* This View has a filter Button */
		addFilterButton();
		addTabbedPane();
	}
	
	private void addTabbedPane() {
		/* Tabbed Pane Initialization */
		tabbedPane = new TabPane();
		
		/* Tabs Initialization */
		todayTab = new Tab();
		todayTab.setText("Today");
		todayTab.setClosable(false);
		todayTab.setContent(tableView);
		
		historyTab = new Tab();
		historyTab.setText("History");
		historyTab.setClosable(false);
		
		isToday = true;
		
		/* Assembly */
		tabbedPane.getTabs().addAll(todayTab, historyTab);
		setCenter(tabbedPane);
		
		/* This Listener switches which tab holds tableView
		 * Both Tabs use the same table but with differing values each tab
		 */
		tabbedPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
			@Override
			public void changed(ObservableValue<? extends Tab> arg0, Tab arg1, Tab arg2) {
				if (isToday) {
					/* Table Switching */
					todayTab.setContent(null);
					historyTab.setContent(tableView);
					//tableView.getColumns().setAll(fillColumns());
					isToday = false;
					
					/* Banner Switching */
					ManagerView.reinitBanner(02);
					
				} else {
					historyTab.setContent(null);
					todayTab.setContent(tableView);
					//tableView.getColumns().setAll(fillColumns());
					isToday = true;
					
					/* Banner Switching */
					ManagerView.reinitBanner(00);
				}
			}
		});
		
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

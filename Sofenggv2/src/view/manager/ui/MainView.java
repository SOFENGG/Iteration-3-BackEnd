package view.manager.ui;

import controller.ManagerViewController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import view.manager.final_values.Values;

public abstract class MainView extends BorderPane{
	
		protected HBox filterOptions;
			protected ComboBox<String> searchColumns;
			protected TextField searchField;
			protected Button searchButton;
			private Button filterButton;
		@SuppressWarnings("rawtypes")
		protected TableView tableView;
		protected HBox actionButtons;
			
	protected ManagerViewController mvc;
	
	public MainView(ManagerViewController mvc) {
		this.mvc = mvc;
		setId("View");
		updatePaneView();
	}
	
	public void updatePaneView() {
		initFilterOptions();
		initTableView();
		initActionButtons();
		initHandlers();
		
		setTop(filterOptions);
		setAlignment(filterOptions, Pos.CENTER_RIGHT);
		setCenter(tableView);
		setBottom(actionButtons);
	}
	
	private void initHandlers() {
		filterButton.setOnAction(e -> {
			FilterPopup fp = new FilterPopup(Values.FILTER_POPUP_TITLE, mvc);
			fp.show();
		});
	}

	private void initActionButtons() {
		actionButtons = new HBox(10);
		setAlignment(actionButtons, Pos.CENTER);
		setMargin(actionButtons, new Insets(5, 0, 5, 10));
	}
	
	
	private void initTableView() {
		 tableView = new Table();
		 //tableView.getColumns().setAll(fillColumns());
	}

	private void initFilterOptions() {
		
		/* Filter Options Initialization */
		filterOptions = new HBox(Values.FILTER_OPTIONS_ITEM_SPACING);
		filterOptions.setPrefHeight(Values.FILTER_OPTIONS_PREF_HEIGHT);
		filterOptions.setPadding(new Insets(10, 0, 0, 10));
		
		/* Search Columns Initialization */
		searchColumns = new ComboBox<String>();
		searchColumns.getStyleClass().add("ComboBox");
		
		/* Search Field Initialization */
		searchField = new TextField();
		searchField.setMinWidth(Values.SEARCH_FIELD_WIDTH);
		searchField.setId("TextField");
		
		/* Search Button Initialization */
		searchButton = new Button(); // To be replaced with the magnifying Icon
		searchButton.getStyleClass().add("SearchButton");
		searchButton.setMinSize(40,  40);
		
		/* Filter Button Initialization */
		filterButton = new Button("O " + "Filter"); // the O character to be replaced with the filter icon
		
		/* Assembly of Search Columns, Search Field, Search Button and Filter Button into Filter Options */
		filterOptions.getChildren().addAll(searchColumns, searchField, searchButton);
	}
	
	protected void addFilterButton() {
		filterOptions.getChildren().add(filterButton);
	}
	
	protected void removeFilterButton() {
		filterOptions.getChildren().remove(filterButton);
	}
	
}

package view.manager.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class AddItemPopup extends Popup {
	
	private VBox layout;
		private HBox itemTypeHBox;
			private ToggleButton oldItemToggleButton;
			private ToggleButton newItemToggleButton;
			private ToggleGroup itemGroup;
		private HBox searchHBox;
			private ComboBox<String> filterComboBox;
			private TextField searchTextField;
			private Button searchButton;
		private HBox buttonsHBox;
			private Label qty;
			private Spinner<Integer> qtySpinner;
			private Button addButton;
		private VBox newItemVBox;
			private Label itemCodeLabel;
			private TextField itemCodeTextField;
			private Label descriptionLabel;
			private TextField descriptionTextField;
			private Label uomLabel;
			private TextField uomTextField;
			private Label unitPriceLabel;
			private TextField unitPriceTextField;
		private Table itemTable;

	public AddItemPopup(String title) {
		super(title);
		
		initScene();
		initHandlers();

		setScene(layout);
	}
	
	public void initScene() {
		layout = new VBox (10);
		layout.setId("Popup");
		layout.setPadding(new Insets(10, 10, 10, 10));
		
			itemTypeHBox = new HBox(20);
		
				itemGroup = new ToggleGroup();
				
				oldItemToggleButton = new ToggleButton("Old Item");
				oldItemToggleButton.setToggleGroup(itemGroup);
				oldItemToggleButton.setSelected(true);
				
				newItemToggleButton = new ToggleButton("New Item");
				newItemToggleButton.setToggleGroup(itemGroup);
			
			itemTypeHBox.getChildren().addAll(oldItemToggleButton, newItemToggleButton);
			
			// old item
			searchHBox = new HBox (20);
			searchHBox.setAlignment(Pos.CENTER);
			
				filterComboBox = new ComboBox<String> ();
				filterComboBox.getStyleClass().add("ComboBox");
				filterComboBox.getItems().add("ID");
				filterComboBox.getItems().add("Name");
				
				filterComboBox.getSelectionModel ().selectFirst ();
				
				searchTextField = new TextField ();
				searchTextField.setId ("TextField");
				
				searchButton = new Button ();
				searchButton.getStyleClass ().add("SearchButton");
				searchButton.setMinSize(40, 40);	
				
			searchHBox.getChildren().addAll(filterComboBox, searchTextField, searchButton);
			
			buttonsHBox = new HBox (20);
			buttonsHBox.setAlignment(Pos.CENTER);
				
				qty = new Label("Quantity:");
				
				qtySpinner = new Spinner<Integer>(0, 999, 0, 1);
				qtySpinner.setEditable(true);
				
				addButton = new Button("Add Item");
				
			buttonsHBox.getChildren().addAll(qty, qtySpinner, addButton);
			
			itemTable = new Table();
		
			// new item
			newItemVBox = new VBox(20);
				itemCodeLabel = new Label("Item Code:");
				itemCodeTextField = new TextField();
				descriptionLabel = new Label("Description");
				descriptionTextField = new TextField();
				uomLabel = new Label("UOM:");
				uomTextField = new TextField();
				unitPriceLabel = new Label("Unit Price:");
				unitPriceTextField = new TextField();
			
			newItemVBox.getChildren().addAll(itemCodeLabel, itemCodeTextField, descriptionLabel, descriptionTextField, uomLabel, uomTextField, unitPriceLabel, unitPriceTextField);
			
			
		layout.getChildren().addAll(itemTypeHBox, searchHBox, itemTable, buttonsHBox);
			
		VBox.setVgrow (layout, Priority.ALWAYS);	
		
		
	}
	
	// BACKEND STUFF
	private void initHandlers() {
		oldItemToggleButton.setOnAction(e -> {
			oldItemToggleButton.setSelected(true);
			
			if (!layout.getChildren().isEmpty())
				layout.getChildren().removeAll(layout.getChildren());
			
			layout.getChildren().addAll(itemTypeHBox, searchHBox, itemTable, buttonsHBox);
			
			resizeScene();
		});
		
		newItemToggleButton.setOnAction(e -> {
			newItemToggleButton.setSelected(true);
			
			if (!layout.getChildren().isEmpty())
				layout.getChildren().removeAll(layout.getChildren());
			
			layout.getChildren().addAll(itemTypeHBox, newItemVBox, buttonsHBox);
		});
	}
}

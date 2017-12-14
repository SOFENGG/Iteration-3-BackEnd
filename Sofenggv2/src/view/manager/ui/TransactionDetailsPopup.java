package view.manager.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class TransactionDetailsPopup extends Popup {

	private VBox layout;
	private HBox searchHBox;
		private ComboBox<String> filterComboBox;
		private TextField searchTextField;
		private Button searchButton;
	private HBox labelsHBox;
		private Label dateTransactedLabel;
		private Label totalQtyLabel;
		private Label totalCostLabel;
	private Table itemTable;

	public TransactionDetailsPopup(String title) {
		super(title);
		
		initScene();
		initHandlers();
		
		setScene(layout);
	}
	
	public void initScene() {
		layout = new VBox (20);
		layout.setId("Popup");
		
			searchHBox = new HBox (30);
			searchHBox.setAlignment(Pos.CENTER);
			
				filterComboBox = new ComboBox<String> ();
				filterComboBox.getStyleClass().add("ComboBox");
				filterComboBox.getItems().add("Description");
				filterComboBox.getItems().add("Item Code");
				filterComboBox.getItems().add("Pieces Order");
				filterComboBox.getItems().add("UOM");
				filterComboBox.getItems().add("Unit Price");
				filterComboBox.getItems().add("Total Price");
				
				filterComboBox.getSelectionModel ().selectFirst ();
				
				searchTextField = new TextField ();
				searchTextField.setId ("TextField");
				
				searchButton = new Button ();
				searchButton.getStyleClass ().add("SearchButton");
				searchButton.setMinSize(40, 40);
					
			searchHBox.getChildren().addAll(filterComboBox, searchTextField, searchButton);
			
			itemTable = new Table();
			
			labelsHBox = new HBox(30);
			
				dateTransactedLabel = new Label("Date transacted: ");
				totalQtyLabel = new Label("Total Quantity: ");
				totalCostLabel = new Label("Total Cost: ");
				
			labelsHBox.getChildren().addAll(dateTransactedLabel, totalQtyLabel, totalCostLabel);
			
		layout.getChildren().addAll(searchHBox, itemTable, labelsHBox);
		
		VBox.setVgrow(layout, Priority.ALWAYS);
	}
	
	// BACKEND STUFF
	private void initHandlers() {
		
	}
	
}

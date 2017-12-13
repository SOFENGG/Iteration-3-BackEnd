package view.manager.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;


public class SupplierPopup extends Popup {
	
	private VBox layout;
		private HBox searchHBox;
			private ComboBox<String> filterComboBox;
			private TextField searchTextField;
			private Button searchButton;
		private Table supplierTable;
			
	public SupplierPopup(String title) {
		super(title);
		
		initScene();
		initHandlers();
		
		setScene(layout);
	}
	
	private void initScene() {
		layout = new VBox (10);
		layout.setId("Popup");
		layout.setPadding(new Insets(10, 10, 10, 10));
		
			searchHBox = new HBox (20);
			searchHBox.setAlignment(Pos.CENTER);
			
				filterComboBox = new ComboBox<String> ();
				filterComboBox.getStyleClass().add("ComboBox");
				filterComboBox.getItems().add("ID");
				filterComboBox.getItems().add("Name");
				filterComboBox.getItems().add("Address");
				
				filterComboBox.getSelectionModel ().selectFirst ();
				
				searchTextField = new TextField ();
				searchTextField.setId ("TextField");
				
				searchButton = new Button ();
				searchButton.getStyleClass ().add("SearchButton");
				searchButton.setMinSize(40, 40);
					
			searchHBox.getChildren().addAll(filterComboBox, searchTextField, searchButton);
		
			supplierTable = new Table();
			
		layout.getChildren().addAll(searchHBox, supplierTable);
		
		VBox.setVgrow (layout, Priority.ALWAYS);
	}

	//backend stuff
	private void initHandlers() {
		
	}


}

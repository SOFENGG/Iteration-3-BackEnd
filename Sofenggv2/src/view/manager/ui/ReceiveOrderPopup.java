package view.manager.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class ReceiveOrderPopup extends Popup {
	
	private VBox layout;
		private HBox searchHBox;
			private ComboBox<String> filterComboBox;
			private TextField searchTextField;
			private Button searchButton;
		private Table itemTable;
		private HBox receiveOrderButtons;
			private Button receiveBtn;
	
	
	public ReceiveOrderPopup(String title) {
		super(title);
		// TODO Auto-generated constructor stub
		initScene();
		initHandlers();
		
		setScene(layout);
	}
	
	private void initScene() {
		
		/* Layout Initialization */
		layout = new VBox(20);
		layout.setId("Popup");
		
		/* Search HBox Initialization */
		searchHBox = new HBox(30);
		searchHBox.setAlignment(Pos.CENTER);
	
			
			filterComboBox = new ComboBox<String> ();
			filterComboBox.getStyleClass().add("ComboBox");
			filterComboBox.getItems().add("Order ID");
			filterComboBox.getItems().add("Supplier Code");
			filterComboBox.getItems().add("Date Ordered");
	
			searchTextField = new TextField ();
			searchTextField.setId ("TextField");
			
			searchButton = new Button ();
			searchButton.getStyleClass ().add("SearchButton");
			searchButton.setMinSize(40, 40); 
			
		searchHBox.getChildren().addAll(filterComboBox, searchTextField, searchButton);
		
		itemTable = new Table();
		
		/* Receive Order Initialization */
		receiveOrderButtons = new HBox(20);
		
			receiveBtn = new Button("Received");
			
		receiveOrderButtons.getChildren().addAll(receiveBtn);
		
		
		
		layout.getChildren().addAll(searchHBox, itemTable, receiveOrderButtons);
		
		VBox.setVgrow(layout, Priority.ALWAYS);
	}
	
	private void initHandlers() {
		
		
		
	}
	
	

}

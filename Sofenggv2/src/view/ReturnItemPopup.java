package view;

import controller.CashierViewController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class ReturnItemPopup extends Popup{

	public static final String TITLE = "Return Item";
	
	private CashierViewController cvc;
	
	private VBox layout;
		private HBox searchHBox;
			private ComboBox<String> filterComboBox;
			private TextField searchTextField;
			private Button searchButton;
		private InventoryView iv;
		private HBox stockHBox;
			private TextField stockTextField;
			private Button stockButton;
	
	public ReturnItemPopup(CashierViewController cvc) {
		super(TITLE);
		
		this.cvc = cvc;
		
		initScene();
		initHandlers ();
		
		setScene(layout);
	}

	private void initScene() {
		layout = new VBox (10);
		layout.setId("Popup");
		
			searchHBox = new HBox (20);
			searchHBox.setAlignment(Pos.CENTER);
			
				filterComboBox = new ComboBox<String> ();
				filterComboBox.getStyleClass().add("ComboBox");
				filterComboBox.getItems().add("Item Code");
				filterComboBox.getItems().add("Description");
				filterComboBox.getItems().add("Service");
				
				filterComboBox.getSelectionModel ().selectFirst ();
				
				searchTextField = new TextField ();
				searchTextField.setId ("TextField");
				
				searchButton = new Button ();
				searchButton.getStyleClass ().add("SearchButton");
				searchButton.setMinSize(40, 40);
					
			searchHBox.getChildren().addAll(filterComboBox, searchTextField, searchButton);
		
			iv = new InventoryView (cvc);
			
			stockHBox = new HBox (20);
			stockHBox.setAlignment(Pos.CENTER);
			
				stockTextField = new TextField ();
				stockTextField.setId("TextField");
				stockTextField.setPromptText("Stock amount");
				stockTextField.setMaxWidth(125);
				
				stockButton = new Button ("Add to stock");
				stockButton.getStyleClass ().add("GreenButton");
				
			stockHBox.getChildren().addAll(stockTextField, stockButton);
			
		layout.getChildren().addAll(searchHBox, iv, stockHBox);
		
		VBox.setVgrow (layout, Priority.ALWAYS);
	}
	
	private void initHandlers() {
		searchButton.setOnAction(e -> {
			//tim pls
		});
		
		stockButton.setOnAction(e -> {
			//tim pls
		});
	}

}

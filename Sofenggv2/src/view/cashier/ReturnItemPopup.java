package view.cashier;

import java.math.BigDecimal;

import controller.CashierViewController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import model.Database;

public class ReturnItemPopup extends Popup{

	public static final String TITLE = "Return Item";
	private final String SPECIAL_INVENTORY_KEY = "inventory_return";
	
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
	
	private InventoryView mainInventory;
	
	public ReturnItemPopup(CashierViewController cvc, InventoryView mainInventory) {
		super(TITLE);
		
		this.cvc = cvc;
		this.mainInventory = mainInventory;
		
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
				
				filterComboBox.getSelectionModel ().selectFirst ();
				
				searchTextField = new TextField ();
				searchTextField.setId ("TextField");
				
				searchButton = new Button ();
				searchButton.getStyleClass ().add("SearchButton");
				searchButton.setMinSize(40, 40);
					
			searchHBox.getChildren().addAll(searchTextField, searchButton);
		
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
		Database.getInstance().attach(SPECIAL_INVENTORY_KEY, iv);
		cvc.getAllItems(new String[]{SPECIAL_INVENTORY_KEY});
		
		searchButton.setOnAction(e -> {
			/*switch(filterComboBox.getValue()){
				case "Item Code": cvc.searchItemByCode(new String[]{SPECIAL_INVENTORY_KEY}, searchTextField.getText());
					break;
				case "Description": cvc.searchItem(new String[]{SPECIAL_INVENTORY_KEY}, searchTextField.getText());
					break;
			}*/
			cvc.searchItem(new String[]{SPECIAL_INVENTORY_KEY}, searchTextField.getText());
		});
		
		stockButton.setOnAction(e -> {
			//tim pls
			Database.getInstance().detach(SPECIAL_INVENTORY_KEY);
			try{
				cvc.returnItem(iv.getSelectedItem().get(InventoryView.ITEM_CODE), Integer.parseInt(stockTextField.getText()), BigDecimal.valueOf(Double.parseDouble(iv.getSelectedItem().get(InventoryView.PRICE))));
				mainInventory.addStock(iv.getSelectedItem().get(InventoryView.ITEM_CODE), Integer.parseInt(stockTextField.getText()));
				closePopup();
			}catch(NumberFormatException exp){
				closePopup();
				new AlertBoxPopup("Error", "Enter a number.");
			}
			
		});
	}

}

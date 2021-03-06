package view.cashier;

import controller.CashierViewController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import model.Database;

public class ServiceWorkerPopup extends Popup{

	public static final String TITLE = "Service Workers";
	
	private CashierViewController cvc;
	
	private VBox layout;
		private HBox searchHBox;
			private ComboBox<String> filterComboBox;
			private TextField searchTextField;
			private Button searchButton;
		private ServiceWorkerView swv;
	
	public ServiceWorkerPopup(CashierViewController cvc) {
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
				filterComboBox.getItems().add("Worker ID");
				filterComboBox.getItems().add("Name");
				
				filterComboBox.getSelectionModel ().selectFirst ();
				
				searchTextField = new TextField ();
				searchTextField.setId ("TextField");
				
				searchButton = new Button ();
				searchButton.getStyleClass ().add("SearchButton");
				searchButton.setMinSize(40, 40);
					
			searchHBox.getChildren().addAll(filterComboBox, searchTextField, searchButton);
		
			swv = new ServiceWorkerView (cvc);
			
		layout.getChildren().addAll(searchHBox, swv);
		
		VBox.setVgrow (layout, Priority.ALWAYS);
	}

	private void initHandlers() {
		Database.getInstance().attach(ServiceWorkerView.KEY, swv);
		
		cvc.getAllSerivceWorkers();
		
		searchButton.setOnAction(e -> {
			switch(filterComboBox.getValue()){
				case "Worker ID":
					int number;
					try{
						number = Integer.parseInt(searchTextField.getText());
						cvc.getServiceWorkerWithID(number);
					}catch(NumberFormatException ex){
						if(!searchTextField.getText().equals(""))
							new AlertBoxPopup("Search Key", "Entered search key is not a number");
						else
							cvc.getAllSerivceWorkers();
					}
					break;
				case "Name": 
					if(!searchTextField.getText().equals(""))
						cvc.getServiceWorkerWithName(searchTextField.getText());
					else
						cvc.getAllSerivceWorkers();
					break;
			}
		});
	}

}

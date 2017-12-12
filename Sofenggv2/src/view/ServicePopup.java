package view;

import controller.CashierViewController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Database;

public class ServicePopup extends Popup{

	public static final String TITLE = "Service";
	
	private CashierViewController cvc;
	
	private VBox layout;
		
	private HBox searchHBox;
		private ComboBox<String> filterComboBox;
			private TextField searchTextField;
			private Button searchButton;
		private ServiceWorkerView swv;
	
		private HBox buttonsHBox;
			private Button okayButton;
			private Button cancelButton;
	
	public ServicePopup(CashierViewController cvc) {
		super(TITLE);
		
		this.cvc = cvc;
		
		initScene();
		initHandlers ();
		
		setScene(layout);
	}

	private void initScene() {
		layout = new VBox (20);
		layout.setId("Popup");
		
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
		
			swv = new ServiceWorkerView (cvc);
		
			buttonsHBox = new HBox (20);
			buttonsHBox.setAlignment(Pos.CENTER_RIGHT);
			
				okayButton = new Button ("Okay");
				okayButton.getStyleClass().add("GreenButton");
				
				cancelButton = new Button ("Cancel");
				cancelButton.getStyleClass().add("RedButton");
			
			buttonsHBox.getChildren().addAll (cancelButton, okayButton);
		
		layout.getChildren().addAll(searchHBox, swv, buttonsHBox);
	}

	private void initHandlers() {
		Database.getInstance().attach(ServiceWorkerView.KEY, swv);
		
		//BUGGED AF
		cvc.getAllSerivceWorkers();
		
		searchButton.setOnAction(e -> {
			switch(filterComboBox.getValue()){
				case "ID":
					int number;
					try{
						number = Integer.parseInt(searchTextField.getText());
						cvc.getServiceWorkerWithID(number);
					}catch(NumberFormatException ex){
						new AlertBoxPopup("Search Key", "Entered search key is not a number");
					}
					break;
				case "Name": cvc.getServiceWorkerWithName(searchTextField.getText());
					break;
			}
		});
		
		okayButton.setOnAction(e -> {
			closePopup();
		});
		
		cancelButton.setOnAction(e -> {
			closePopup();
			//Database.getInstance().detach(ServiceWorkerView.KEY); --> DOESNT WORK
		});
	}

}

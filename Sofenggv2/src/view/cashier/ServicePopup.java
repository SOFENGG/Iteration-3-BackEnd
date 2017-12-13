package view.cashier;

import controller.CashierViewController;
import javafx.collections.ObservableList;
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
			
	private boolean success;
	private boolean cancel;
	private int workerId;
	private String workerName;
	
	public ServicePopup(CashierViewController cvc) {
		super(TITLE);
		
		this.cvc = cvc;
		cancel = true;
		success = false;
		workerId = 0;
		workerName = "";
		
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
			cancel = false;
			ObservableList<String> row = swv.getSelectedItem();
			
			if(row != null){
				workerName = row.get(1);
				workerId = Integer.parseInt(row.get(0));
				success = true;
			}else{
				new AlertBoxPopup("Error", "No selected worker.");
			}
			closePopup();
		});
		
		cancelButton.setOnAction(e -> {
			cancel = true;
			closePopup();
			//Database.getInstance().detach(ServiceWorkerView.KEY); --> DOESNT WORK
		});
	}
	
	public boolean isCancel(){
		return cancel;
	}
	
	public boolean isSuccess(){
		return success;
	}
	
	public int getWorkerId(){
		return workerId;
	}

	public String getWorkerName(){
		return workerName;
	}
}

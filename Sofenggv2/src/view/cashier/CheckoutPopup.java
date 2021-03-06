package view.cashier;

import java.math.BigDecimal;

import controller.CashierViewController;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Customer;
import model.Database;

	public class CheckoutPopup extends Popup {
	
	public static final String TITLE = "Checkout";
	
	private CashierViewController cvc;
	
	private VBox layout;
		private HBox amountHBox;
			private Label amountLabel;
			private TextField amountTextField;
		private HBox paymentHBox;
			private Label paymentLabel;
			private VBox paymentVBox;
				private ToggleGroup group;
				private RadioButton cashOutRadioButton;
				private RadioButton debtRadioButton;
		
		private HBox searchHBox;
			private ComboBox<String> filterComboBox;
			private TextField searchTextField;
			private Button searchButton;
		private CustomerView cv;
				
		private HBox buttonsHBox;
			private Button okayButton;
			private Button cancelButton;
	
	public CheckoutPopup(CashierViewController cvc) {
		super(TITLE);
		
		this.cvc = cvc;
		
		initScene();
		initHandlers ();
		
		setScene(layout);
	}

	private void initScene() {
		layout = new VBox (20);
		layout.setId("Popup");
		
			amountHBox = new HBox (10);
				
				amountLabel = new Label ("Amount (in PHP):");
				amountLabel.setId("DefaultLabel");
				
				amountTextField = new TextField ();
				amountTextField.setId("TextField");
				amountTextField.setPromptText("0000.00");
				
			amountHBox.getChildren().addAll(amountLabel, amountTextField);
			
			paymentHBox = new HBox (10);
			
				paymentLabel = new Label ("Payment method:");
				paymentLabel.setId("DefaultLabel");
			
				paymentVBox = new VBox (10);
					
					group = new ToggleGroup ();
					
					cashOutRadioButton = new RadioButton ("Cash out");
					cashOutRadioButton.setId("RadioButton");
					cashOutRadioButton.setToggleGroup(group);
					cashOutRadioButton.setSelected(true);
					
					debtRadioButton = new RadioButton ("Debt");
					debtRadioButton.setId("RadioButton");
					debtRadioButton.setToggleGroup(group);
				
				paymentVBox.getChildren().addAll(cashOutRadioButton, debtRadioButton);
				
			paymentHBox.getChildren().addAll(paymentLabel, paymentVBox);
		
			searchHBox = new HBox (20);
			searchHBox.setAlignment(Pos.CENTER);
			
				filterComboBox = new ComboBox<String> ();
				filterComboBox.getStyleClass().add("ComboBox");
				filterComboBox.getItems().add("Account ID");
				filterComboBox.getItems().add("Name");
				filterComboBox.getItems().add("Address");
				
				filterComboBox.getSelectionModel ().selectFirst ();
				
				searchTextField = new TextField ();
				searchTextField.setId ("TextField");
				
				searchButton = new Button ();
				searchButton.getStyleClass ().add("SearchButton");
				searchButton.setMinSize(40, 40);
					
			searchHBox.getChildren().addAll(filterComboBox, searchTextField, searchButton);
			
			cv = new CustomerView (cvc);
			
			buttonsHBox = new HBox (20);
			buttonsHBox.setAlignment(Pos.CENTER_RIGHT);
				
				okayButton = new Button ("Okay");
				okayButton.getStyleClass().add("GreenButton");
				
				cancelButton = new Button ("Cancel");
				cancelButton.getStyleClass().add("RedButton");
				
			buttonsHBox.getChildren().addAll (cancelButton, okayButton);
		
		layout.getChildren().addAll(amountHBox, paymentHBox, buttonsHBox);
	}

	private void initHandlers() {
		searchButton.setOnAction(e -> {
			switch(filterComboBox.getValue()){
				case "Account ID": 
					try{
						cvc.searchCustomer(Integer.parseInt(searchTextField.getText().toString()));
					}catch(NumberFormatException nfe){
						if(!searchTextField.getText().equals(""))
							new AlertBoxPopup("Input Error", "Enter a number.");
						else
							cvc.getAllCustomers();
					}
					break;
				case "Name": 
					if(!searchTextField.getText().equals(""))
						cvc.searchCustomerName(searchTextField.getText().toString());
					else
						cvc.getAllCustomers();
					break;
				case "Address": 
					if(!searchTextField.getText().equals(""))
						cvc.searchCustomerAddress(searchTextField.getText().toString());
					else
						cvc.getAllCustomers();
					break;
			}
		});
		
		cashOutRadioButton.setOnAction(e -> {
			cashOutRadioButton.setSelected(true);
			
			if (!layout.getChildren().isEmpty())
				layout.getChildren().removeAll(layout.getChildren());
			
			layout.getChildren().addAll(amountHBox, paymentHBox, buttonsHBox);
			
			detach();
			resizeScene();
		});
		
		debtRadioButton.setOnAction(e -> {
			debtRadioButton.setSelected(true);
			
			if (!layout.getChildren().isEmpty())
				layout.getChildren().removeAll(layout.getChildren());
			
			layout.getChildren().addAll(amountHBox, paymentHBox, searchHBox, cv, buttonsHBox);
			
			//gets all customer and puts then to customer view
			attach();
			cvc.getAllCustomers();
			
			resizeScene();
		});
		
		okayButton.setOnAction(e -> {
			
			Customer customer = null;
			boolean isloan = debtRadioButton.isSelected();
			boolean success = false;
			
			if(isloan){
				//manager verification if debt
				ManagerPopup pop = new ManagerPopup(cvc);
				
				if(pop.getAccess()){
					ObservableList<String> row = cv.getSelectedItem();
					customer = new Customer(Integer.parseInt(row.get(0)),
							row.get(1),
							row.get(2),
							row.get(3),
							Integer.parseInt(row.get(4)),
							BigDecimal.valueOf(Double.parseDouble(row.get(5))),
							BigDecimal.valueOf(Double.parseDouble(row.get(6))));
					
					success = cvc.buyItems(CashierView.transaction, isloan, customer);
					
					detach();
					closePopup();
					
					if(!success){
						new AlertBoxPopup("Transaction Failed", "Insufficient funds");
					}else{
						new AlertBoxPopup("Transaction Success", "Purchase Complete!");
					}
					
				}else{
					if(!pop.isCanceled())
						new AlertBoxPopup("Access", "Access denied");
				}
			}else{
				try{
					float input = Float.parseFloat(amountTextField.getText());
					double total = cvc.getCart().getTotalPrice().doubleValue();
					if(BigDecimal.valueOf(input).doubleValue() >= total){
						success = cvc.buyItems(CashierView.transaction, isloan, null);
						detach();
						closePopup();
						new AlertBoxPopup("Transaction Success", "The change is: " + (input - total) + "php");
					}else{
						detach();
						closePopup();

						new AlertBoxPopup("Error", "Cash received was not enough.");
					}
					
				}catch(NumberFormatException ex){
					detach();
					closePopup();
					new AlertBoxPopup("Error", "Enter an amount.");
				}
				
			}
			
		});
		
		cancelButton.setOnAction(e -> {
			detach();
			closePopup();
		});
	}
	
	private void attach(){
		Database.getInstance().attach(CustomerView.KEY, cv);
	}
	
	private void detach(){
		Database.getInstance().detach(CustomerView.KEY);
	}
	

}

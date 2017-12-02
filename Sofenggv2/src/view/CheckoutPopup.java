package view;

import controller.CashierViewController;
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
			
			cv = new CustomerView (cvc);
			
			buttonsHBox = new HBox (20);
			buttonsHBox.setAlignment(Pos.CENTER_RIGHT);
				
				okayButton = new Button ("Okay");
				okayButton.getStyleClass().add("GreenButton");
				
				cancelButton = new Button ("Cancel");
				cancelButton.getStyleClass().add("Button");
				
			buttonsHBox.getChildren().addAll (cancelButton, okayButton);
		
		layout.getChildren().addAll(amountHBox, paymentHBox, buttonsHBox);
	}

	private void initHandlers() {
		cashOutRadioButton.setOnAction(e -> {
			cashOutRadioButton.setSelected(true);
			
			if (!layout.getChildren().isEmpty())
				layout.getChildren().removeAll(layout.getChildren());
			
			layout.getChildren().addAll(amountHBox, paymentHBox, buttonsHBox);
			
			resizeScene();
		});
		
		debtRadioButton.setOnAction(e -> {
			debtRadioButton.setSelected(true);
			
			if (!layout.getChildren().isEmpty())
				layout.getChildren().removeAll(layout.getChildren());
			
			layout.getChildren().addAll(amountHBox, paymentHBox, searchHBox, cv, buttonsHBox);
			
			resizeScene();
		});
		
		okayButton.setOnAction(e -> {
			//tim
		});
		
		cancelButton.setOnAction(e -> {
			closePopup();
		});
	}
	

}

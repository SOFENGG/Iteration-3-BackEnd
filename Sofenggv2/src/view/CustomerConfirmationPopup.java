package view;

import controller.CashierViewController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CustomerConfirmationPopup extends Popup {

public static final String TITLE = "Customer confirmation";
	
	private CashierViewController cvc;

	private VBox layout;
		private Label customerLabel;
		private HBox usernameHBox;
			private Label usernameLabel;
			private TextField usernameTextField;
		private HBox buttonsHBox;
			private Button okayButton;
			private Button cancelButton;
	
	public CustomerConfirmationPopup(CashierViewController cvc) {
		super(TITLE);
		
		this.cvc = cvc;
		
		initScene();
		initHandlers ();
		
		setScene(layout);
	}

	private void initScene() {
		layout = new VBox (20);
		layout.setId("Popup");
		
			customerLabel = new Label ("Customer information");
			customerLabel.setId("DefaultLabel");
			
			usernameHBox = new HBox (10);
			
				usernameLabel = new Label ("Name:");
				usernameLabel.setId("DefaultLabel");
				
				usernameTextField = new TextField ();
				usernameTextField.setId("TextField");
			
			usernameHBox.getChildren().addAll(usernameLabel, usernameTextField);
			
			buttonsHBox = new HBox (20);
			buttonsHBox.setAlignment(Pos.CENTER_RIGHT);
			
				okayButton = new Button ("Hold Cart");
				okayButton.getStyleClass().add("GreenButton");
				
				cancelButton = new Button ("Cancel");
				cancelButton.getStyleClass().add("RedButton");
			
			buttonsHBox.getChildren().addAll (cancelButton, okayButton);
		
		layout.getChildren().addAll(customerLabel, usernameHBox, buttonsHBox);
	}

	private void initHandlers() {
		okayButton.setOnAction(e -> {
			
		});
		
		cancelButton.setOnAction(e -> {
			closePopup();
		});
	}
	
}

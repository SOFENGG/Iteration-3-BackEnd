package view;

import controller.CashierViewController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ManagerPopup extends Popup{
	
	public static final String TITLE = "Manager verification";
	
	private CashierViewController cvc;

	private VBox layout;
		private Label managerLabel;
		private HBox usernameHBox;
			private Label usernameLabel;
			private TextField usernameTextField;
		private HBox passwordHBox;
			private Label passwordLabel;
			private PasswordField passwordField;
		private HBox buttonsHBox;
			private Button okayButton;
			private Button cancelButton;
	
	public ManagerPopup(CashierViewController cvc) {
		super(TITLE);
		
		this.cvc = cvc;
		
		initScene();
		initHandlers ();
		
		setScene(layout);
	}

	private void initScene() {
		layout = new VBox (20);
		layout.setId("Popup");
		
			managerLabel = new Label ("Manager credentials");
			managerLabel.setId("DefaultLabel");
			
			usernameHBox = new HBox (10);
			
				usernameLabel = new Label ("Username:");
				usernameLabel.setId("DefaultLabel");
				
				usernameTextField = new TextField ();
				usernameTextField.setId("TextField");
			
			usernameHBox.getChildren().addAll(usernameLabel, usernameTextField);
			
			passwordHBox = new HBox (10);
			
				passwordLabel = new Label ("Password:");
				passwordLabel.setId("DefaultLabel");
				
				passwordField = new PasswordField ();
				passwordField.setId("TextField");
			
			passwordHBox.getChildren().addAll(passwordLabel, passwordField);
			
			buttonsHBox = new HBox (20);
			buttonsHBox.setAlignment(Pos.CENTER_RIGHT);
			
				okayButton = new Button ("Okay");
				okayButton.getStyleClass().add("GreenButton");
				
				cancelButton = new Button ("Cancel");
				cancelButton.getStyleClass().add("Button");
			
			buttonsHBox.getChildren().addAll (cancelButton, okayButton);
		
		layout.getChildren().addAll(managerLabel, usernameHBox, passwordHBox, buttonsHBox);
	}

	private void initHandlers() {
		okayButton.setOnAction(e -> {
			//new AlertBoxPopup if wrong userpass
		});
		
		cancelButton.setOnAction(e -> {
			closePopup();
		});
	}

}

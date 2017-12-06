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
		private HBox passwordHBox;
			private Label passwordLabel;
			private PasswordField passwordField;
		private HBox buttonsHBox;
			private Button okayButton;
			private Button cancelButton;
			
	private boolean access;
	
	public ManagerPopup(CashierViewController cvc) {
		super(TITLE);
		
		this.cvc = cvc;
		access = false;
		
		initScene();
		initHandlers ();
		
		setScene(layout);
	}

	private void initScene() {
		layout = new VBox (20);
		layout.setId("Popup");
		
			managerLabel = new Label ("Manager credentials");
			managerLabel.setId("DefaultLabel");
			
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
				cancelButton.getStyleClass().add("RedButton");
			
			buttonsHBox.getChildren().addAll (cancelButton, okayButton);
		
		layout.getChildren().addAll(managerLabel, passwordHBox, buttonsHBox);
	}

	private void initHandlers() {
		okayButton.setOnAction(e -> {
			//new AlertBoxPopup if wrong userpass
			access = cvc.managerAccess(passwordField.getText());
			closePopup();
		});
		
		cancelButton.setOnAction(e -> {
			closePopup();
		});
	}
	
	public boolean getAccess(){
		return access;
	}

}

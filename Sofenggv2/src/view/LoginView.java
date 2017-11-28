package view;

import controller.LoginController;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class LoginView extends BorderPane implements View{
	private LoginController lc;
	
	private VBox loginBox;
	private HBox usernameBox;
		private Label username;
		private TextField usernameField;
	private HBox passwordBox;
		private Label password;
		private TextField passwordField;
	private Button login;
	
	public LoginView (LoginController lc) {
		super();
		
		this.lc = lc;
		
		setMaxSize (Double.MAX_VALUE, Double.MAX_VALUE);
			
		initLoginView();
		initHandlers();
		
		setCenter (loginBox);
	}

	private void initLoginView() {
		loginBox = new VBox (20);
		loginBox.setAlignment (Pos.CENTER);
		loginBox.setId("LoginBox");
		
			usernameBox = new HBox (10);
			usernameBox.setAlignment (Pos.CENTER);
			
				username = new Label ();
				username.setText ("Username:");
				username.setId ("DefaultLabel");
				usernameField = new TextField ();
				usernameField.setText("");
				usernameField.setId("TextField");
				
			usernameBox.getChildren ().addAll (username, usernameField);
			
			passwordBox = new HBox (10);
			passwordBox.setAlignment (Pos.CENTER);
			
				password = new Label ();
				password.setText ("Password:");
				password.setId ("DefaultLabel");
				passwordField = new TextField ();
				passwordField.setText ("");
				passwordField.setId("TextField");
				
			passwordBox.getChildren ().addAll (password, passwordField);
			
			login = new Button ();
			login.setText("Login");
			login.getStyleClass ().add ("Button");
			
		loginBox.getChildren ().addAll (usernameBox, passwordBox, login);
	}
	
	private void initHandlers() {
		login.setOnAction(e ->  {
			lc.logIn(usernameField.getText(), passwordField.getText());
		});
		
	}

	@Override
	public void update() {
		
	}
	
	public Parent getView() {
		return this;
	}
}

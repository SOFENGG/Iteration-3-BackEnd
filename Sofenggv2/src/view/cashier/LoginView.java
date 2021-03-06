package view.cashier;

import controller.LoginController;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import model.Database;
import model.Item;
import view.View;

public class LoginView extends StackPane implements View{
	public static final String KEY = "login";
	private LoginController lc;
	
	private MediaPlayer player, music;
	private MediaView mediaView, mediaViewMusic;
	
	private VBox imgBox;
	
	private VBox loginBox;
	private HBox usernameBox;
		private Image usernameImage;
		private ImageView usernameView;
		private Label username;
		private TextField usernameField;
	private HBox passwordBox;
		private Image passwordImage;
		private ImageView passwordView;
		private Label password;
		private PasswordField passwordField;
	private Button login;
	
	public LoginView (LoginController lc) {
		super();
		
		this.lc = lc;
		
		setMaxSize (Double.MAX_VALUE, Double.MAX_VALUE);
			
		initLoginView();
		//initMedia();
		//initMusic();
		initHandlers();
		
		//setCenter (loginBox);
		getChildren().addAll(imgBox, loginBox);
	}

	private void initMedia() {
		player = new MediaPlayer( new Media(getClass().getResource("/video.mp4").toExternalForm()));
		mediaView = new MediaView(player);
		DoubleProperty mvw = mediaView.fitWidthProperty();
		DoubleProperty mvh = mediaView.fitHeightProperty();
		//mvw.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
		mvh.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));
		//mediaView.setPreserveRatio(true);
		player.setCycleCount(MediaPlayer.INDEFINITE);
        player.setAutoPlay(true);
        player.play();
	}
	
	private void initMusic() {
		music = new MediaPlayer( new Media(getClass().getResource("/legion.mp3").toExternalForm()));
		mediaViewMusic = new MediaView(music);
		music.setCycleCount(MediaPlayer.INDEFINITE);
        //music.setAutoPlay(true);
        //music.play();
	}

	private void initLoginView() {
		imgBox = new VBox ();
		imgBox.setAlignment(Pos.CENTER);
		imgBox.setId("LogoBox");
		
		loginBox = new VBox (20);
		loginBox.setAlignment (Pos.CENTER);
		//loginBox.setId("LoginBox");
		loginBox.setPadding(new Insets(300, 0, 0, 0));
		
			usernameBox = new HBox (10);
			usernameBox.setAlignment (Pos.CENTER);
				
				usernameImage = new Image(("username.png"));
				usernameView = new ImageView(); 
				usernameView.setImage(usernameImage);
				usernameView.setFitHeight(25);
				usernameView.setPreserveRatio(true);
			
				username = new Label ();
				username.setText ("Username:");
				username.setId ("DefaultLabel");
				usernameField = new TextField ();
				usernameField.setText("");
				usernameField.setId("TextField");
				usernameField.setPromptText("Username");
				
			usernameBox.getChildren ().addAll (usernameView, usernameField);
			
			passwordBox = new HBox (10);
			passwordBox.setAlignment (Pos.CENTER);
			
				passwordImage = new Image(("password.png"));
				passwordView = new ImageView(); 
				passwordView.setImage(passwordImage);
				passwordView.setFitHeight(25);
				passwordView.setPreserveRatio(true);
			
				password = new Label ();
				password.setText ("Password:");
				password.setId ("DefaultLabel");
				
				passwordField = new PasswordField ();
				passwordField.setId("TextField");
				passwordField.setPromptText("Password");
				
			passwordBox.getChildren ().addAll (passwordView, passwordField);
			
			login = new Button ();
			login.setText("Login");
			login.getStyleClass ().add ("GreenButton");
			
		loginBox.getChildren ().addAll (usernameBox, passwordBox, login);
	}
	
	private void initHandlers() {
		login.setOnAction(e ->  {
			lc.logIn(usernameField.getText(), passwordField.getText());
			usernameField.clear();
			passwordField.clear();
			//player.stop();
			//music.stop();
		});
		
	}

	@Override
	public void update() {
		
	}
	
	public Parent getView() {
		return this;
	}
}

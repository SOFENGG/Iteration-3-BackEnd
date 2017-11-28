package view;

import controller.LoginController;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import model.Database;
import model.Item;

public class LoginView extends StackPane implements View{
	private LoginController lc;
	
	private MediaPlayer player, music;
	private MediaView mediaView, mediaViewMusic;
	
	private VBox imgBox;
	
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
		initMedia();
		initMusic();
		initHandlers();
		
		//setCenter (loginBox);
		getChildren().addAll(mediaViewMusic, mediaView, imgBox, loginBox);
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
        //player.setAutoPlay(true);
        //player.play();
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
		imgBox.setId("LoginBox");
		
		loginBox = new VBox (20);
		loginBox.setAlignment (Pos.CENTER);
		loginBox.setId("LoginBox");
		loginBox.setPadding(new Insets(300, 0, 0, 0));
		
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
			player.stop();
		});
		
	}

	@Override
	public void update() {
		
	}
	
	public Parent getView() {
		return this;
	}
}

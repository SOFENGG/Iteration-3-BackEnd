package controller;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.User;

public class MainController extends Controller {
	private LoginController lc;
	private CashierViewController cvc;
	private ManagerViewController mvc;
	
	public MainController(Stage stage) {
		super(stage);
		mainStage.setTitle("POS System");
		scene.getStylesheets ().add ("./StyleSheet.css");
		
		stage.setFullScreenExitHint ("Press ESC to Exit Full Screen");
		stage.maximizedProperty ().addListener ((a,b,c) -> {
			if (c) {
				stage.setMaximized (false);
				stage.setFullScreen (true);
			}
		});
	}

	@Override
	protected void initControllers() {
		lc = new LoginController (this);
		cvc = new CashierViewController (this);
		mvc = new ManagerViewController (this);
	}

	@Override
	public void setScene(int requestCode, int view) {
		switch(requestCode) {
			case Code.LC_CODE: scene.setRoot(lc.getView(view));
				break;
			case Code.CVC_CODE: scene.setRoot(cvc.getView(view));
				break;
			case Code.MVC_CODE: scene.setRoot(mvc.getView(view));
				break;
		}
		//resetStage();
	}
	
	public void passUser(int requestCode, User user){
		switch(requestCode){
			case Code.CVC_CODE: cvc.setUser(user);
				break;
			case Code.MVC_CODE: mvc.setUser(user);
				break;
		}
	}

}

package view.manager.ui;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public abstract class Popup extends Stage {
	
	protected String title;
	
	protected Scene scene;
	
	public Popup (String title) {
		super();
		
		this.title = title;
		
		initModality(Modality.APPLICATION_MODAL); //Blocks access to other windows until this is closed
		setTitle(title);
		setResizable(false);
		
		scene = new Scene(new Group ());
		scene.getStylesheets ().add ("./StyleSheet.css");
	}
	
	public void setScene (Parent value) {
		scene.setRoot(value);
		sizeToScene();
		setScene(scene);
		//showAndWait();
	}
	
	public void resizeScene() {
		sizeToScene();
	}
	
	public void closePopup () {
		close();
	}
}

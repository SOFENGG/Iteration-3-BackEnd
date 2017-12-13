package view.cashier;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class AlertBoxPopup extends Popup {

	private String message;
	
	protected VBox layout;
		private Label label;
		private Button closeButton;
	
	public AlertBoxPopup(String title, String message) {
		super(title);
		
		this.message = message;
		
		initScene();
		setScene(layout);
	}
	
	private void initScene() {
		layout = new VBox(10);
		layout.setId("Popup");
		
			label = new Label();
			label.setId("DefaultLabel");
			label.setText(message);
			
			closeButton = new Button("Close the window");
			closeButton.getStyleClass().add("RedButton");
			closeButton.setOnAction(e -> close());
		
		layout.getChildren().addAll(label, closeButton);
		layout.setAlignment(Pos.CENTER);
	}
}

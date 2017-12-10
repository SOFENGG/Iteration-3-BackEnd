package view;

import java.math.BigDecimal;

import controller.CashierViewController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class EndOfDayPopup extends Popup{

	public static final String TITLE = "End of Day";
	
	private CashierViewController cvc;
	
	private int total;
	
	private VBox layout;
		private EndOfDayView eodv;
		private HBox buttonsHBox;
			private Button okayButton;
			private Button cancelButton;
	
	public EndOfDayPopup(CashierViewController cvc) {
		super(TITLE);
		
		this.cvc = cvc;
		
		initScene();
		initHandlers ();
		
		setScene(layout);
	}

	private void initScene() {
		layout = new VBox (10);
		layout.setId("Popup");
		
			eodv = new EndOfDayView(cvc);
			
			buttonsHBox = new HBox (20);
			buttonsHBox.setAlignment(Pos.CENTER_RIGHT);
			
				okayButton = new Button ("Okay");
				okayButton.getStyleClass().add("GreenButton");
				
				cancelButton = new Button ("Cancel");
				cancelButton.getStyleClass().add("RedButton");
			
			buttonsHBox.getChildren().addAll (cancelButton, okayButton);
			
		layout.getChildren().addAll(eodv, buttonsHBox);
		
		HBox.setHgrow (layout, Priority.ALWAYS);
		VBox.setVgrow (layout, Priority.ALWAYS);
	}

	private void initHandlers() {
		okayButton.setOnAction(e -> {
			cvc.endOfDay(BigDecimal.valueOf(total));
			closePopup();
		});
		
		cancelButton.setOnAction(e -> {
			closePopup();
		});
	}

}

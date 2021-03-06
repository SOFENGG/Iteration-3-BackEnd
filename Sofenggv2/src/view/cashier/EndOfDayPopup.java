package view.cashier;

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
		private VBox cashDenominationVBox;
			private Label cashDenominationLabel;
			private HBox input1000;
			private HBox input500;
			private HBox input200;
			private HBox input100; 
			private HBox input50;
			private HBox input20;
			private HBox input10;
			private HBox input5; 
			private HBox input1;
			private HBox totalHBox;
				private Label totalLabel;
				private Label totalAmountLabel;
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
		
			cashDenominationVBox = new VBox ();
			cashDenominationVBox.setId("PopupDetails");
			
				cashDenominationLabel = new Label ("Cash Denominations");
				cashDenominationLabel.setId("LabelTitle");
				
				input1000 = createDenomBox(1000);
				input500 = createDenomBox(500);
				input200 = createDenomBox(200);
				input100 = createDenomBox(100);
				input50 = createDenomBox(50);
				input20 = createDenomBox(20);
				input10 = createDenomBox(10);
				input5 = createDenomBox(5);
				input1 = createDenomBox(1);
				
				totalHBox = new HBox (20);
				
					totalLabel = new Label ("Total Amount:");
					totalLabel.setId("LabelGray");
					
					totalAmountLabel = new Label ("PHP 0");
					totalAmountLabel.setId("DefaultLabel");
					
				totalHBox.getChildren().addAll(totalLabel, totalAmountLabel);
			
			cashDenominationVBox.getChildren().addAll(cashDenominationLabel, input1000, input500, input200, 
					input100, input50, input20, input10, 
					input5, input1, totalHBox);
			
			buttonsHBox = new HBox (20);
			buttonsHBox.setAlignment(Pos.CENTER_RIGHT);
			
				okayButton = new Button ("Okay");
				okayButton.getStyleClass().add("GreenButton");
				
				cancelButton = new Button ("Cancel");
				cancelButton.getStyleClass().add("RedButton");
			
			buttonsHBox.getChildren().addAll (cancelButton, okayButton);
			
		layout.getChildren().addAll(cashDenominationVBox, buttonsHBox);
		
		HBox.setHgrow (layout, Priority.ALWAYS);
		VBox.setVgrow (layout, Priority.ALWAYS);
	}

	private HBox createDenomBox(int denominations) {
		HBox denominationsHBox = new HBox(15);
		
			Label denominationsLabel = new Label("PHP " + denominations + " x ");
			denominationsLabel.setId("DefaultLabel");
			denominationsLabel.setPrefWidth(125);
		
			Spinner<Integer> spinner = new Spinner<Integer>();
			spinner.setPrefWidth(75);
		
			SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 9999, 0);
			spinner.setValueFactory(valueFactory);
			
			Label totalDenominationLabel = new Label();
			totalDenominationLabel.setId("DefaultLabel");
			
			spinner.valueProperty().addListener(new ChangeListener<Integer>(){
				@Override
				public void changed(ObservableValue<? extends Integer> arg0, Integer arg1, Integer arg2) {
					total = total - (denominations * arg1) + (denominations * arg2);
					totalAmountLabel.setText ("PHP " + total);
					resizeScene();
				}
			});
			
		
		denominationsHBox.getChildren().addAll(denominationsLabel, spinner, totalDenominationLabel);
		return denominationsHBox;
	}

	private void initHandlers() {
		okayButton.setOnAction(e -> {
			if(total > 0){
				cvc.endOfDay(BigDecimal.valueOf(total));
				closePopup();
				new AlertBoxPopup("Success", "Total earning for today was logged.");
			}else{
				closePopup();
				new AlertBoxPopup("Error", "No input.");
			}
		});
		
		cancelButton.setOnAction(e -> {
			closePopup();
		});
}

}

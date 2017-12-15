package view.manager.ui;

import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CreateReportPopup extends Popup {
	
	private VBox layout;
		private HBox typeHBox;
			private ToggleGroup reportTypeGroup;
			private RadioButton dailyRadio;
			private RadioButton weeklyRadio;
			private RadioButton monthlyRadio;
			private RadioButton yearlyRadio;
		private HBox datePickers;
			private DatePicker startDatePicker;
			private DatePicker endDatePicker;
	
	public CreateReportPopup(String title) {
		super(title);
		
		initScene();
		initHandlers();
		
		setScene(layout);
		
	}
	
	public void initScene() {
		layout = new VBox(20);
		layout.setId("Popup");
		
		// Type Box Initialization
		
			//
		typeHBox = new HBox (20);
			reportTypeGroup = new ToggleGroup();
			
			dailyRadio = new RadioButton("Daily");
			dailyRadio.setToggleGroup(reportTypeGroup);
			dailyRadio.setSelected(true);
			
			weeklyRadio = new RadioButton("Weekly");
			weeklyRadio.setToggleGroup(reportTypeGroup);
			
			monthlyRadio = new RadioButton("Monthly");
			monthlyRadio.setToggleGroup(reportTypeGroup);
			
			yearlyRadio = new RadioButton("Yealry");
			yearlyRadio.setToggleGroup(reportTypeGroup);
		
		typeHBox.getChildren().addAll(dailyRadio, weeklyRadio, monthlyRadio, yearlyRadio);
		
		
		
	}
	
	public void initHandlers() {
		
	}

}

package view.manager.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class CreateReportPopup extends Popup {
	
	private VBox layout;
		private HBox typeHBox;
			private ToggleGroup reportTypeGroup;
			private RadioButton dailyRadio;	
			private RadioButton weeklyRadio;			
			private RadioButton monthlyRadio;
			private RadioButton yearlyRadio;
		
		private Label optionsLbl;
		private HBox optionsHBox;
			private DatePicker dayPicker;
			private HBox datePickerHBox;
				private DatePicker startDatePicker;
				private DatePicker endDatePicker;
			private ComboBox<String> monthBox;
			private ComboBox<Integer> yearBox;
		
		private Button createBtn;
		
			
	public CreateReportPopup(String title) {
		super(title);
		
		initScene();
		initHandlers();
		
		setScene(layout);
		
	}
	
	public void initScene() {
		layout = new VBox(10);
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
			
			yearlyRadio = new RadioButton("Yearly");
			yearlyRadio.setToggleGroup(reportTypeGroup);
		
		typeHBox.getChildren().addAll(dailyRadio, weeklyRadio, monthlyRadio, yearlyRadio);
		
		// Options Label
		optionsLbl = new Label("Select Date:");
		optionsLbl.setAlignment(Pos.CENTER);
		
		//Options HBox
		optionsHBox = new HBox(20);
		
		// Daily
		dayPicker = new DatePicker();
		
		// Weekly
		datePickerHBox = new HBox(20);
			VBox startCombo = new VBox();
			startDatePicker = new DatePicker();
			startCombo.getChildren().addAll(new Label("Start Date:"), startDatePicker);
			
			VBox endCombo = new VBox();
			endDatePicker = new DatePicker();
			endCombo.getChildren().addAll(new Label("End Date:"), endDatePicker);
		
		datePickerHBox.getChildren().addAll(startCombo, endCombo);
		
		// Monthly
		monthBox = new ComboBox<String>();
		monthBox.setItems(fillMonthlyComboBox());
		
		// Yearly
		yearBox = new ComboBox<Integer>();
		yearBox.setItems(fillYearlyComboBox());
		
		createBtn = new Button("Create Report");
		createBtn.getStyleClass().add("GreenButton");
		createBtn.setAlignment(Pos.CENTER_RIGHT);
		
		optionsHBox.getChildren().addAll(dayPicker);
		
		HBox.setHgrow(optionsHBox, Priority.ALWAYS);
		optionsHBox.setAlignment(Pos.CENTER);
		
		layout.getChildren().addAll(typeHBox, optionsLbl, optionsHBox, createBtn);
		VBox.setVgrow(layout, Priority.ALWAYS);
		
		resizeScene();
		
	}
	
	public void initHandlers() {
		createBtn.setOnAction(e -> {
			
		});
		
		dailyRadio.setOnAction(e -> {
			dailyRadio.setSelected(true);
			
			optionsLbl.setText("Select Date:");
			optionsHBox.getChildren().removeAll(datePickerHBox, monthBox, yearBox);
			optionsHBox.getChildren().addAll(dayPicker);
			resizeScene();
		});
		
		weeklyRadio.setOnAction(e -> {
			weeklyRadio.setSelected(true);
			
			
			optionsLbl.setText("Select Start and End Date of the Week:");
			optionsHBox.getChildren().removeAll(dayPicker, monthBox, yearBox);
			optionsHBox.getChildren().addAll(datePickerHBox);
			resizeScene();
		});
		
		monthlyRadio.setOnAction(e -> {
			monthlyRadio.setSelected(true);
			
			optionsLbl.setText("Select Month and Year:");
			optionsHBox.getChildren().removeAll(dayPicker, datePickerHBox, yearBox);
			optionsHBox.getChildren().addAll(monthBox, yearBox);
			resizeScene();
		});
		
		yearlyRadio.setOnAction(e -> {
			yearlyRadio.setSelected(true);
			
			optionsLbl.setText("Select Year:");
			optionsHBox.getChildren().removeAll(dayPicker, datePickerHBox, monthBox, yearBox);
			optionsHBox.getChildren().addAll(yearBox);
			resizeScene();
		});
	}

	
	/* This function is for the Back End Developers */
	private ObservableList<String> fillMonthlyComboBox() {
		ObservableList<String> list = FXCollections.observableArrayList();
		
		//indeces are as follows:
		//0 = transaction #, 1 = user id, 2 = transaction type
		//3 = is loan, 4 = date sold, 5 = total price
		list.addAll("January",
					"February",
					"March", 
					"April",
					"May",
					"June",
					"July",
					"August",
					"September",
					"October",
					"November",
					"December");
		
		return list;
	}
	
	/* This function is for the Back End Developers */
	private ObservableList<Integer> fillYearlyComboBox() {
		ObservableList<Integer> list = FXCollections.observableArrayList();
		
		//indeces are as follows:
		//0 = transaction #, 1 = user id, 2 = transaction type
		//3 = is loan, 4 = date sold, 5 = total price
		for (int i = 1998; i < 2099 ; i++)
			list.add(i);
		
		return list;
	}

}

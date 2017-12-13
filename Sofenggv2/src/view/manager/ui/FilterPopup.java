package view.manager.ui;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class FilterPopup extends Popup {
	
	private VBox layout;
		private Label filterFromLabel;
		private HBox optionHBox;
			private RadioButton presetRadioButton;
			private RadioButton customRadioButton;
			private ToggleGroup filterTypeGroup;
		private VBox presetHBox;
			private ComboBox<String> presetComboBox;
		private VBox customFilterVBox;
			private Label dateAndYearLabel;
			private HBox dateAndYearHBox;
				private DatePicker startDatePicker;
				private Label toLabel;
				private DatePicker endDatePicker;
			private Label weekLabel;
			private HBox weekHBox;
				private RadioButton allWeekRadioButton;
				private RadioButton selectDaysRadioButton;
				private ToggleGroup daysGroup;
			private HBox dayHBox;
				private CheckBox sundayCheckBox;
				private CheckBox mondayCheckBox;
				private CheckBox tuesdayCheckBox;
				private CheckBox wednesdayCheckBox;
				private CheckBox thursdayCheckBox;
				private CheckBox fridayCheckBox;
				private CheckBox saturdayCheckBox;
			private Label orderLabel;
			private HBox ascDescHBox;
				private RadioButton ascRadioButton;
				private RadioButton descRadioButton;
				private ToggleGroup ascDescGroup;
			private HBox customOrderHBox;
				private RadioButton monthRadioButton;
				private RadioButton yearRadioButton;
				private RadioButton costRadioButton;
				private RadioButton transactionNumRadioButton;
				private ToggleGroup customOrderGroup;
			private Button applyFilterButton;
			
			

	public FilterPopup(String title) {
		super(title);

		initScene();
		initHandlers();
		
		setScene(layout);
	}
	
	public void initScene() {
		layout = new VBox (20);
		layout.setId("Popup");
		layout.setPadding(new Insets(10, 10, 20, 10));
		
			filterFromLabel = new Label ("Filter from:");
			
			optionHBox = new HBox (20);
				filterTypeGroup = new ToggleGroup();
				
				presetRadioButton = new RadioButton("Preset");
				presetRadioButton.setToggleGroup(filterTypeGroup);
				presetRadioButton.setSelected(true);
				
				customRadioButton = new RadioButton("Custom");
				customRadioButton.setToggleGroup(filterTypeGroup);
				
			optionHBox.getChildren().addAll(presetRadioButton, customRadioButton);
			
			// preset filters
			presetHBox = new VBox(20);
		
				presetComboBox = new ComboBox<String>();
				presetComboBox.getStyleClass().add("ComboBox");
				presetComboBox.getItems().add("Last 7 days");
				presetComboBox.getItems().add("Last 5 days");
				presetComboBox.getItems().add("Last 3 months");
				presetComboBox.getItems().add("Last 6 months");
				presetComboBox.getItems().add("Last year");
				
				presetComboBox.getSelectionModel().selectFirst();
			
				applyFilterButton = new Button("Apply Filter");
			
			presetHBox.getChildren().addAll(presetComboBox, applyFilterButton);
			
			//custom filters
			customFilterVBox = new VBox(20);
			customFilterVBox.setId("CustomFilter");
			customFilterVBox.setPadding(new Insets(10, 10, 20, 10));
			
				dateAndYearLabel = new Label("Date & Year:");
				
				dateAndYearHBox = new HBox(20);
					
					startDatePicker = new DatePicker();
					
					toLabel = new Label(" to ");
					
					endDatePicker = new DatePicker();
				
				dateAndYearHBox.getChildren().addAll(startDatePicker, toLabel, endDatePicker);
				
				weekLabel = new Label("Week:");
				weekHBox = new HBox(10);
					daysGroup = new ToggleGroup();
					
					allWeekRadioButton = new RadioButton("All week");
					allWeekRadioButton.setSelected(true);
					allWeekRadioButton.setToggleGroup(daysGroup);
					
					selectDaysRadioButton = new RadioButton("Select days:");
					selectDaysRadioButton.setToggleGroup(daysGroup);
					
				weekHBox.getChildren().addAll(allWeekRadioButton, selectDaysRadioButton);	
				
				dayHBox = new HBox(20);
					
					sundayCheckBox = new CheckBox("S");
					
					mondayCheckBox = new CheckBox("M");
					
					tuesdayCheckBox = new CheckBox("T");
					
					wednesdayCheckBox = new CheckBox("W");
					
					thursdayCheckBox = new CheckBox("TH");
					
					fridayCheckBox = new CheckBox("F");
					
					saturdayCheckBox = new CheckBox("ST");
				
				orderLabel = new Label("Order:");
				
				ascDescHBox = new HBox(20);
					
					ascDescGroup = new ToggleGroup();
					
					ascRadioButton = new RadioButton("Ascending");
					ascRadioButton.setToggleGroup(ascDescGroup);
					
					descRadioButton = new RadioButton("Descending");
					descRadioButton.setToggleGroup(ascDescGroup);
				
				ascDescHBox.getChildren().addAll(ascRadioButton, descRadioButton);
				
				customOrderHBox = new HBox(20);
				
					customOrderGroup = new ToggleGroup();
					
					monthRadioButton = new RadioButton("By Month");
					monthRadioButton.setToggleGroup(customOrderGroup);
					
					yearRadioButton = new RadioButton("By Year");
					yearRadioButton.setToggleGroup(customOrderGroup);
					
					costRadioButton = new RadioButton("By Cost");
					costRadioButton.setToggleGroup(customOrderGroup);
					
					transactionNumRadioButton = new RadioButton("By Transaction No.");
					transactionNumRadioButton.setToggleGroup(customOrderGroup);
					
				customOrderHBox.getChildren().addAll(monthRadioButton, yearRadioButton, costRadioButton, transactionNumRadioButton);
			
			customFilterVBox.getChildren().addAll(dateAndYearLabel, dateAndYearHBox, weekLabel, weekHBox, dayHBox, orderLabel, ascDescHBox, customOrderHBox, applyFilterButton);
			
		layout.getChildren().addAll(filterFromLabel, optionHBox, presetHBox);
		
		VBox.setVgrow (layout, Priority.ALWAYS);
	}
	
	// BACKEND STUFF
	public void initHandlers() {
		presetRadioButton.setOnAction(e -> {
			presetRadioButton.setSelected(true);
			
			if (!layout.getChildren().isEmpty())
				layout.getChildren().removeAll(layout.getChildren());
			
			layout.getChildren().addAll(filterFromLabel, optionHBox, presetHBox);
			
			resizeScene();
		});
		
		customRadioButton.setOnAction(e -> {
			customRadioButton.setSelected(true);
			
			if (!layout.getChildren().isEmpty())
				layout.getChildren().removeAll(layout.getChildren());
			
			layout.getChildren().addAll(filterFromLabel, optionHBox, customFilterVBox);
			
			resizeScene();
		});
		
		allWeekRadioButton.setOnAction(e -> {
			allWeekRadioButton.setSelected(true);
			
			if (!dayHBox.getChildren().isEmpty())
				dayHBox.getChildren().removeAll(dayHBox.getChildren());
				
		});
		
		selectDaysRadioButton.setOnAction(e -> {
			selectDaysRadioButton.setSelected(true);
			

			if (!dayHBox.getChildren().isEmpty())
				dayHBox.getChildren().removeAll(dayHBox.getChildren());
			
			dayHBox.getChildren().addAll(sundayCheckBox, mondayCheckBox, tuesdayCheckBox, wednesdayCheckBox, thursdayCheckBox, fridayCheckBox, saturdayCheckBox);
		});
	}

}

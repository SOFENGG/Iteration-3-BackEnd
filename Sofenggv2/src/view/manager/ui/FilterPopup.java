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

import java.util.ArrayList;
import java.util.Date;

import controller.ManagerViewController;

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
			
			
	private ManagerViewController mvc;
	
	public FilterPopup(String title, ManagerViewController mvc) {
		super(title);
		this.mvc = mvc;

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
				applyFilterButton.getStyleClass().add("GreenButton");
			
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
			
			customFilterVBox.getChildren().addAll(dateAndYearLabel, dateAndYearHBox, weekLabel, weekHBox, dayHBox, orderLabel, ascDescHBox, customOrderHBox);
			
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
			presetHBox.getChildren().add(applyFilterButton);
			
			resizeScene();
		});
		
		customRadioButton.setOnAction(e -> {
			customRadioButton.setSelected(true);
			
			if (!layout.getChildren().isEmpty())
				layout.getChildren().removeAll(layout.getChildren());
			
			layout.getChildren().addAll(filterFromLabel, optionHBox, customFilterVBox);
			customFilterVBox.getChildren().add(applyFilterButton);
			
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
		
		//for passing filters to manager view controller
		applyFilterButton.setOnAction(e -> {
			ArrayList<Object> filters = new ArrayList<>();
			
			//get where clause
			//determine whether preset or custom is selected
			if (presetRadioButton.isSelected()) {
				//0 = preset selected
				filters.add((Integer) 0); 
				 
				//if preset, get and add index of selected time frame
				//0 = 7 days, 1 = 5 days, 2 = 3 months, 3 = 6 months, 4 = 1 year
				filters.add((Integer) presetComboBox.getSelectionModel().getSelectedIndex());
			} else {
				//1 = custom selected
				filters.add((Integer) 1);
				
				//get start and end dates as date class
				Date startDate = java.sql.Date.valueOf(startDatePicker.getValue());
				Date endDate = java.sql.Date.valueOf(endDatePicker.getValue());
				//add start and end dates as strings of db format (yyyy-MM-dd)
				filters.add((String) startDate.toString());
				filters.add((String) endDate.toString());
				
				//determine whether all week or select days is selected
				if (allWeekRadioButton.isSelected()) {
					//0 = all week selected
					filters.add((Integer) 0);
				} else {
					//1 = select days selected
					filters.add((Integer) 1);
					
					//get integers of days selected to be used by sql dayofweek()
					ArrayList<Integer> selectedDays = new ArrayList<>();
					if (sundayCheckBox.isSelected()) selectedDays.add(1);
					if (mondayCheckBox.isSelected()) selectedDays.add(2);
					if (tuesdayCheckBox.isSelected()) selectedDays.add(3);
					if (wednesdayCheckBox.isSelected()) selectedDays.add(4);
					if (thursdayCheckBox.isSelected()) selectedDays.add(5);
					if (fridayCheckBox.isSelected()) selectedDays.add(6);
					if (saturdayCheckBox.isSelected()) selectedDays.add(7);
					
					//add integers as arraylist of integer
					filters.add(selectedDays);
				}
				
				//get order by clause
				//determine whether ascending or descending is selected
				//0 = ascending selected
				if (ascRadioButton.isSelected()) filters.add((Integer) 0);
				//1 = descending selected
				else filters.add((Integer) 1);
					
				//determine whether by month, by year, by cost, or by transaction # is selected
				//0 = month selected
				if (monthRadioButton.isSelected()) filters.add((Integer) 0);
				//1 = year selected
				else if (yearRadioButton.isSelected()) filters.add((Integer) 1);
				//2 = cost selected
				else if (costRadioButton.isSelected()) filters.add((Integer) 2);
				//3 = transaction # selected
				else filters.add((Integer) 3);
			}
			
			//run sql filter parser -> ManagerViewController
			mvc.setFilter(filters);
			mvc.getFilteredTransactions(new String[] {TransactionView.KEY});
			
			//close popup
			this.closePopup();
		});
	}

}

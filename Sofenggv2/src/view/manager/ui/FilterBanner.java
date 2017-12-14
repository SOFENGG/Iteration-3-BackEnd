package view.manager.ui;

import javafx.scene.control.Label;
import view.manager.final_values.Values;

public class FilterBanner extends Banner {
	
	/* Outer Left Section */
	private Label dateYearTitleLbl;
	private Label dateYearFilterLbl;
	
	/* Inner Left Section */
	private Label weekTitleLbl;
	private Label weekFilterLbl;
	
	/* Inner Right Section */
	private Label orderTitleLbl;
	private Label orderFilterLbl;
	
	public FilterBanner() {
		super();
		initFilterBanner();
	}
	
	private void initFilterBanner() {
		/* Banner Title */
		bannerTitle.setText(Values.BANNER_FILTER);
		
		/* Left Column */
		dateYearTitleLbl = new Label();
		dateYearTitleLbl.setText(Values.DATE_YEAR);
		
		weekTitleLbl = new Label();
		weekTitleLbl.setText(Values.WEEK);
		
		orderTitleLbl = new Label();
		orderTitleLbl.setText(Values.ORDER);
		
		initFilterLabels();
		
		leftColumn.getChildren().addAll(dateYearTitleLbl, weekTitleLbl, orderTitleLbl);
		rightColumn.getChildren().addAll(dateYearFilterLbl, weekFilterLbl, orderFilterLbl);
		
	}
	
	/* For Back End Developers */
	private void initFilterLabels() {
		
		String dateYear, week, order;
		
		/* Test Cases */
		dateYear = "01/11/2017 - 08/11/2017";
		week = "All Week";
		order = "Ascending - By Transaction #";
		
		dateYearFilterLbl =  new Label(dateYear);
		weekFilterLbl = new Label(week);
		orderFilterLbl = new Label(order);
		
	}
	
	public Label getDateYearTitleLbl() {
		return dateYearTitleLbl;
	}

	public void setDateYearTitleLbl(Label dateYearTitleLbl) {
		this.dateYearTitleLbl = dateYearTitleLbl;
	}

	public Label getDateYearFilterLbl() {
		return dateYearFilterLbl;
	}

	public void setDateYearFilterLbl(Label dateYearFilterLbl) {
		this.dateYearFilterLbl = dateYearFilterLbl;
	}

	public Label getWeekTitleLbl() {
		return weekTitleLbl;
	}

	public void setWeekTitleLbl(Label weekTitleLbl) {
		this.weekTitleLbl = weekTitleLbl;
	}

	public Label getWeekFilterLbl() {
		return weekFilterLbl;
	}

	public void setWeekFilterLbl(Label weekFilterLbl) {
		this.weekFilterLbl = weekFilterLbl;
	}

	public Label getOrderTitleLbl() {
		return orderTitleLbl;
	}

	public void setOrderTitleLbl(Label orderTitleLbl) {
		this.orderTitleLbl = orderTitleLbl;
	}

	public Label getOrderFilterLbl() {
		return orderFilterLbl;
	}

	public void setOrderFilterLbl(Label orderFilterLbl) {
		this.orderFilterLbl = orderFilterLbl;
	}
	
}

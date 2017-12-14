package view.manager.ui;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javafx.scene.control.Label;
import view.manager.final_values.Values;

public class DateBanner extends Banner {
	
	private Label todaysDateLbl;
	
	public DateBanner() {
		super();
		initDateBanner();
	}
	
	private void initDateBanner() {
		
		/* Banner Title */
		bannerTitle.setText(Values.BANNER_DATE);
		
		todaysDateLbl = new Label();
		todaysDateLbl.setText(setTodaysDateLbl());
		
		leftColumn.getChildren().addAll(todaysDateLbl);
	}
	
	/* For Back End Developers */
	private String setTodaysDateLbl() {
		
		String todaysDate;
		
		/* Get current time */
		todaysDate = getToday();
		
		return todaysDate;
	}
	
	/* returns the string format of the current time*/
	@SuppressWarnings("static-access")
	private String getToday(){
		String dateString = "";

		Calendar cal = new GregorianCalendar();
		
		int dayOfMonth = cal.get(Calendar.getInstance().DATE);
		int month = cal.get(Calendar.getInstance().MONTH);
		int day = cal.get(Calendar.getInstance().DAY_OF_WEEK);
		int year = cal.get(Calendar.getInstance().YEAR);
		int hour = cal.get(Calendar.getInstance().HOUR + 1) - 12;
		int minute = cal.get(Calendar.getInstance().MINUTE);
		int am_pm = cal.get(Calendar.AM_PM);
		
		String monthString = "";
		String dayString = "";
		String minuteString = "";
		String time = "";
		
		switch(day){
			case Calendar.MONDAY:
				dayString = "Monday";
				break;
			case Calendar.TUESDAY:
				dayString = "Tuesday";
				break;
			case Calendar.WEDNESDAY:
				dayString = "Wednesday";
				break;
			case Calendar.THURSDAY:
				dayString = "Thursday";
				break;
			case Calendar.FRIDAY:
				dayString = "Friday";
				break;
			case Calendar.SATURDAY:
				dayString = "Saturday";
				break;
			case Calendar.SUNDAY:
				dayString = "Sunday";
				break;
		}
		
		switch(month){
			case Calendar.JANUARY:
				monthString = "January";
				break;
			case Calendar.FEBRUARY:
				monthString = "February";
				break;
			case Calendar.MARCH:
				monthString = "March";
				break;
			case Calendar.APRIL:
				monthString = "April";
				break;
			case Calendar.MAY:
				monthString = "May";
				break;
			case Calendar.JUNE:
				monthString = "June";
				break;
			case Calendar.JULY:
				monthString = "July";
				break;
			case Calendar.AUGUST:
				monthString = "August";
				break;
			case Calendar.SEPTEMBER:
				monthString = "September";
				break;
			case Calendar.OCTOBER:
				monthString = "October";
				break;
			case Calendar.NOVEMBER:
				monthString = "November";
				break;
			case Calendar.DECEMBER:
				monthString = "December";
			break;
		}
		
		if(minute < 10){
			minuteString = "0" + minute;
		}else
			minuteString = minute + "";

		if(am_pm == Calendar.AM)
			time = "AM";
		else
			time = "PM";
		
		dateString = monthString + " " +
					dayOfMonth + ", " +
					year + " - " +
					hour + ":"+
					minuteString + time +", " +
					dayString;
		
		return dateString;
	}
}

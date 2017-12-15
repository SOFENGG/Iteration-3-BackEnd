package view.manager.ui;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import view.manager.final_values.Values;

public class SalesReportBanner extends Banner {
	
	private TreeView<String> reportTree;
		private TreeItem<String> rootItem;
			private TreeItem<String> dailyReports;
				private TreeItem<String> dailyItem;
			private TreeItem<String> weeklyReports;
				private TreeItem<String> weeklyItem;
			private TreeItem<String> monthlyReports;
				private TreeItem<String> monthlyItem;
			private TreeItem<String> yearlyReports;
				private TreeItem<String> yearlyItem;
					
	public SalesReportBanner() {
		super();
		updateToSalesReportBanner();
	}
	
	private void updateToSalesReportBanner() {
		/* Banner Title */
		bannerTitle.setText(Values.BANNER_SALES_REPORT);
		
		createReportTree();
	}
	
	/* Create the Tree of Reports */
	private void createReportTree() {
		rootItem = new TreeItem<>("Reports");
		
		/* Test Cases */
		/* Dates Creation */
		dailyItem = new TreeItem<>("November 11, 2017");
		weeklyItem = new TreeItem<>("November 4 - November 11, 2107");
		monthlyItem = new TreeItem<>("November 2017");
		yearlyItem = new TreeItem<>("Year 2017");
		
		/* Categories Creation */
		dailyReports = new TreeItem<>("Daily Reports");
		weeklyReports = new TreeItem<>("Weekly Reports");
		monthlyReports = new TreeItem<>("Monthly Reports");
		yearlyReports = new TreeItem<>("Yearly Reports");
		
		
		/* Assembly */
		
		dailyReports.getChildren().add(dailyItem);
		
		weeklyReports.getChildren().add(weeklyItem);
		
		monthlyReports.getChildren().add(monthlyItem);
		
		yearlyReports.getChildren().add(yearlyItem);
		
		rootItem.getChildren().addAll(dailyReports, weeklyReports, monthlyReports, yearlyReports);
		
		reportTree = new TreeView<>(rootItem);
		
		setCenter(reportTree);
	}

}

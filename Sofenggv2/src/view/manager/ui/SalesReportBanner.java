package view.manager.ui;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import view.manager.final_values.Values;

public class SalesReportBanner extends Banner {
	
	private TreeView<String> reportTree;
		private TreeItem<String> rootItem;
			private TreeItem<String> categoryItem;
				private TreeItem<String> dateItem;
					private TreeItem<String> reportItem;
	
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
		dateItem = new TreeItem<>("November 11, 2017");
		
		/* Categories Creation */
		categoryItem = new TreeItem<>("Purchase Reports");
		
		/* Report Names */
		reportItem = new TreeItem<>("Report #1 Title");
		
		/* Assembly */
		dateItem.getChildren().add(reportItem);
		
		categoryItem.getChildren().add(dateItem);
		
		rootItem.getChildren().add(categoryItem);
		
		reportTree = new TreeView<>(rootItem);
		
		setCenter(reportTree);
	}

}

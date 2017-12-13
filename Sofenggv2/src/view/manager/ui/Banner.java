package view.manager.ui;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import view.manager.final_values.Values;

public abstract class Banner extends BorderPane {
	
	protected VBox bannerDetails;
		protected Label bannerTitle;
		protected HBox bannerContents;
			protected VBox leftColumn;
			protected VBox rightColumn;
				
	public Banner() {
		setId("Banner");
		setPrefWidth(Values.BANNER_PREF_WIDTH);
		initBanner();
	}
	
	private void initBanner() {
		/* Banner Details Initialization */
		bannerDetails = new VBox(Values.BANNER_DETAILS_ITEM_SPACING);
		
		/* Banner Title Initialization (No Set Text) */
		bannerTitle = new Label();
		bannerTitle.setPadding(new Insets(Values.BANNER_TITLE_TOP_PADDING, Values.BANNER_TITLE_RIGHT_PADDING, Values.BANNER_TITLE_BOTTOM_PADDING, Values.BANNER_TITLE_LEFT_PADDING));
		
		/* Banner Contents Initialization */
		bannerContents = new HBox(Values.BANNER_CONTENTS_ITEM_SPACING);
		bannerContents.setPadding(new Insets(Values.BANNER_CONTENTS_TOP_PADDING, Values.BANNER_CONTENTS_RIGHT_PADDING, Values.BANNER_CONTENTS_BOTTOM_PADDING, Values.BANNER_CONTENTS_LEFT_PADDING));
		
		/* Banner Contents Sections Initialization */
		leftColumn = new VBox(Values.LEFT_SPACING);
		rightColumn = new VBox(Values.RIGHT_ITEM_SPACING);
		
		/* Assembly */
		bannerContents.getChildren().addAll(leftColumn, rightColumn);
		
		bannerDetails.getChildren().addAll(bannerTitle, bannerContents);
		
		setTop(bannerDetails);
	}
	
	

}

package view.manager.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Rectangle;
import view.manager.final_values.Values;

public class HeaderPane extends BorderPane {
	
	private Button showNavMenuBtn;
	private ImageView logoView;
	private Image logo;
	private Button aboutUsBtn;
		
	
	public HeaderPane() {
		setId("HeaderPane");
		setPrefHeight(95);
		initHeaderPane();
		setPositions();
	}
	
	private void initHeaderPane() {
		
		/* Show Nav Menu Button Initialization */
		showNavMenuBtn = new Button();
		showNavMenuBtn.getStyleClass().add("MenuButton");
		showNavMenuBtn.setPrefWidth(Values.HEADER_PANE_CONTENTS_WIDTH);
		showNavMenuBtn.setPrefHeight(Values.HEADER_PANE_CONTENTS_HEIGHT);
		
		/* Logo Initializtion */
		//logo = new Rectangle(Values.HEADER_PANE_CONTENTS_WIDTH, Values.HEADER_PANE_CONTENTS_HEIGHT);
		logo = new Image(("E&Elogo.png"));
		logoView = new ImageView();
		logoView.setImage(logo);
		logoView.setFitHeight(50);
		logoView.setPreserveRatio(true);
		
		/* About Us Button Initialization */
		aboutUsBtn = new Button();
		aboutUsBtn.getStyleClass().add("HelpButton");
		aboutUsBtn.setPrefWidth(Values.HEADER_PANE_CONTENTS_WIDTH);
		aboutUsBtn.setPrefHeight(Values.HEADER_PANE_CONTENTS_HEIGHT);
		
		
		setLeft(showNavMenuBtn);
		setCenter(logoView);
		setRight(aboutUsBtn);
		
	}
	
	private void setPositions() {
		setAlignment(showNavMenuBtn, Pos.CENTER);
		setMargin(showNavMenuBtn, new Insets(0, 0, 0, 10));
		
		setAlignment(aboutUsBtn, Pos.CENTER);
		setMargin(aboutUsBtn, new Insets(0, 10, 0, 0));
	}
	
	public Button getShowNavMenuBtn() {
		return this.showNavMenuBtn;
	}
	
	public Button getAboutUsBtn() {
		return this.aboutUsBtn;
	}
	
	

}

package view;

import java.math.BigDecimal;

import controller.CashierViewController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import model.Database;

public class CashierView extends BorderPane implements View{
	
	private CashierViewController cvc;
	
	private HBox topHBox;
		private HBox menuHBox;
			private ToggleButton menuButton;
		private HBox logoHBox;
			private ImageView logoView;
			private Image logo;
		private HBox helpHBox;
			private ToggleButton helpButton;
		
	private VBox leftVBox;
		private ToggleGroup toggleGroup;
			private ToggleButton returnItem;
			private ToggleButton serviceWorker;
			private ToggleButton endOfDay;
			private ToggleButton logout;
	
	private VBox centerVBox;
		private HBox searchHBox;
			private ComboBox<String> filterComboBox;
			private TextField searchTextField;
			private Button searchButton;
		private InventoryView iv;
			
	private VBox rightVBox;
		private CartView cv;
		private HBox checkoutHBox;
			private HBox checkoutLeftHBox;
				private Label totalLabel;
			private HBox checkoutRightHBox;
				private Button checkoutButton;
		private HBox cartOptionsHBox;
			private Button holdButton;
			private Button overridePriceButton;
			private Button clearCartButton;
			
	public CashierView (CashierViewController cvc) {
		super ();
		this.cvc = cvc;
		
		initPane ();
		initHandlers ();
	}

	private void initPane() {
		setMaxSize (Double.MAX_VALUE, Double.MAX_VALUE);
		initTop();
		initLeft();
		initCenter();
		initRight();
		
		setTop(topHBox);
		setLeft(leftVBox);
		setCenter(centerVBox);
		setRight(rightVBox);
	}

	private void initTop() {
		topHBox = new HBox ();
		topHBox.setId("TopHbox");
		
		menuHBox = new HBox ();
		menuHBox.setSpacing (50);
		menuHBox.setAlignment (Pos.CENTER_LEFT);
		
			menuButton = new ToggleButton ();
			menuButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			menuButton.getStyleClass().add("MenuButton");
			menuButton.setMinSize(50, 50);
			
		menuHBox.getChildren().addAll(menuButton);
		
		logoHBox = new HBox ();
		logoHBox.setSpacing (50);
		logoHBox.setAlignment (Pos.CENTER);
		
			logo = new Image(("E&Elogo.png"));
			logoView = new ImageView(); 
			logoView.setImage(logo);
			logoView.setFitHeight(50);
			logoView.setPreserveRatio(true);
		
		logoHBox.getChildren().addAll(logoView);
		
		helpHBox = new HBox ();
		helpHBox.setSpacing (50);
		helpHBox.setAlignment (Pos.CENTER_RIGHT);
		
			helpButton = new ToggleButton ();
			helpButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			helpButton.getStyleClass().add("HelpButton");
			helpButton.setMinSize(50, 50);
		
		helpHBox.getChildren().addAll(helpButton);
		
		topHBox.getChildren ().addAll (menuHBox, logoHBox, helpHBox);
		
		HBox.setHgrow (menuHBox, Priority.ALWAYS);
		HBox.setHgrow (logoHBox, Priority.ALWAYS);
		HBox.setHgrow (helpHBox, Priority.ALWAYS);
	}
	
	private void initLeft() {
		leftVBox = new VBox (20);
		leftVBox.setId("LeftVbox");
		
			toggleGroup = new ToggleGroup ();
			
				returnItem = new ToggleButton ("Return Item");
				returnItem.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
				returnItem.getStyleClass().add("Button");
				returnItem.setToggleGroup(toggleGroup);
				
				serviceWorker = new ToggleButton ("Service Worker");
				serviceWorker.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
				serviceWorker.getStyleClass().add("Button");
				serviceWorker.setToggleGroup(toggleGroup);
				
				endOfDay = new ToggleButton ("End of Day");
				endOfDay.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
				endOfDay.getStyleClass().add("Button");
				endOfDay.setToggleGroup(toggleGroup);
				
				logout = new ToggleButton ("Logout");
				logout.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
				logout.getStyleClass().add("Button");
				logout.setToggleGroup(toggleGroup);
				
		leftVBox.getChildren().addAll(returnItem, serviceWorker, endOfDay, logout);
		
		VBox.setVgrow (leftVBox, Priority.ALWAYS);
	}

	private void initCenter() {
		centerVBox = new VBox (10);
		centerVBox.setId("CenterVbox");
		
			searchHBox = new HBox (20);
			
				filterComboBox = new ComboBox<String> ();
				filterComboBox.getStyleClass().add("ComboBox");
				filterComboBox.getItems().add("Item Code");
				filterComboBox.getItems().add("Description");
				filterComboBox.getItems().add("Service");
				
				filterComboBox.getSelectionModel ().selectFirst ();
				
				searchTextField = new TextField ();
				searchTextField.setId ("TextField");
				
				searchButton = new Button ();
				searchButton.setId("SearchButton");
				searchButton.setMinSize(40, 40);
				
			searchHBox.getChildren().addAll(filterComboBox, searchTextField, searchButton);
		
			iv = new InventoryView (cvc);
			
		centerVBox.getChildren().addAll(searchHBox, iv);
		
		VBox.setVgrow (centerVBox, Priority.ALWAYS);
	}

	private void initRight() {
		rightVBox = new VBox (20);
		rightVBox.setId("RightVbox");
		
			checkoutHBox = new HBox ();
			checkoutHBox.setAlignment(Pos.TOP_CENTER);
				
				checkoutLeftHBox = new HBox ();
				checkoutLeftHBox.setAlignment(Pos.CENTER_LEFT);
				
					totalLabel = new Label ("Total: ");
					totalLabel.setId ("DefaultLabel");
				
				checkoutLeftHBox.getChildren().addAll(totalLabel);
				
				checkoutRightHBox = new HBox ();
				checkoutRightHBox.setAlignment(Pos.CENTER_RIGHT);
				
					checkoutButton = new Button ("Checkout");
					checkoutButton.getStyleClass().add("Button");
				
				checkoutRightHBox.getChildren().addAll(checkoutButton);
				
			checkoutHBox.getChildren ().addAll (checkoutLeftHBox, checkoutRightHBox);
			
			cartOptionsHBox = new HBox (5);
			cartOptionsHBox.setAlignment(Pos.BOTTOM_CENTER);
			
				holdButton = new Button ("Hold");
				holdButton.getStyleClass().add("Button");
				
				overridePriceButton = new Button ("Override Price");
				overridePriceButton.getStyleClass().add("Button");
				
				clearCartButton = new Button ("Clear Cart");
				clearCartButton.getStyleClass().add("Button");
			
			cartOptionsHBox.getChildren ().addAll (holdButton, overridePriceButton, clearCartButton);
			
			cv = new CartView (cvc);
			
		rightVBox.getChildren ().addAll (checkoutHBox, cv, cartOptionsHBox);
		
		HBox.setHgrow (checkoutHBox, Priority.ALWAYS);
		HBox.setHgrow (checkoutLeftHBox, Priority.ALWAYS);
		HBox.setHgrow (checkoutRightHBox, Priority.ALWAYS);
		HBox.setHgrow (cartOptionsHBox, Priority.ALWAYS);
		VBox.setVgrow (rightVBox, Priority.ALWAYS);
	}
	
	private void initHandlers () {
		menuButton.setOnAction(e -> {
			if (menuButton.isSelected())
				setLeft (leftVBox);
			else
				setLeft(null);
		});
		
		filterComboBox.setOnAction(e -> {
			switch(filterComboBox.getValue()){
				case "Item Code": 
				case "Description": cvc.getAllItems();
					break;
				case "Service": cvc.getAllServices();
					break;
			}
			searchTextField.setText("");
		});
		
		searchButton.setOnAction(e -> {
			switch(filterComboBox.getValue()){
				case "Item Code": cvc.searchItemByCode(searchTextField.getText());
					break;
				case "Description": cvc.searchItem(searchTextField.getText());
					break;
				case "Service": cvc.searchService(searchTextField.getText());
					break;
			}
			
			cvc.addToCart("0", "white wheel", BigDecimal.valueOf(25), 1);
		});
	}
	
	public void attach(){
		//put all attaching of views here
		Database.getInstance().attach(InventoryView.KEY, iv);
		Database.getInstance().attach(CartView.KEY, cv);
	}
	
	public void detach(){
		//put all detaching of vies here
		Database.getInstance().detach(InventoryView.KEY);
		Database.getInstance().detach(CartView.KEY);
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
}

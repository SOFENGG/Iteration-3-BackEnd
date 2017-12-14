package view.cashier;

import java.math.BigDecimal;

import controller.CashierViewController;
import javafx.collections.ObservableList;
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
import model.CartItem;
import model.CartItemType;
import model.Database;
import view.View;

public class CashierView extends BorderPane implements View{
	public static final String KEY = "cashierview";
	public static String transaction = "";
	
	private CashierViewController cvc;
	
	private HBox topHBox;
		private HBox menuHBox;
			private ToggleButton menuButton;
		private HBox logoHBox;
			private ImageView logoView;
			private Image logo;
		private HBox helpHBox;
			private Button helpButton;
		
	private VBox leftVBox;
			private Button returnItem;
			private Button serviceWorker;
			private Button endOfDay;
			private Button logout;
			private VBox cashierVBox;
				private HBox cashierHBox;
					private Label cashierNameLabel;
					private Label cashierLabel;
	
	private VBox centerVBox;
		private HBox centerTopHBox;
			private HBox searchHBox;
				private ComboBox<String> filterComboBox;
				private TextField searchTextField;
				private Button searchButton;
			private HBox cartHBox;
				private Button cartButton;
		private InventoryViewTabPane ivtp;
			
	private VBox rightVBox;
		private CartViewTabPane cvtp;
		private HBox checkoutHBox;
			private HBox checkoutLeftHBox;
				private Label totalLabel;
			private HBox checkoutRightHBox;
				private Button clearCartButton;
				private Button checkoutButton;
			private Button resumeButton;
			private Button removeCartButton;
		private HBox cartOptionsHBox;
			private HBox cartOptionsLeftHBox;
				private Button holdButton;
				private Button overridePriceButton;
				private Button clearItemButton;
			private HBox cartOptionsRightHBox;
				private ToggleGroup toggleGroup;
					private ToggleButton retailButton;
					private ToggleButton wholeSaleButton;
			
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
		//menuHBox.setSpacing (50);
		menuHBox.setAlignment (Pos.CENTER_LEFT);
		
			menuButton = new ToggleButton ();
			menuButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			menuButton.getStyleClass().add("MenuButton");
			menuButton.setMinSize(50, 50);
			menuButton.setSelected(true);
			
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
		
			helpButton = new Button ();
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
		leftVBox = new VBox ();
		leftVBox.setId("LeftVbox");
			
				returnItem = new Button ("Return Item");
				returnItem.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
				returnItem.getStyleClass().add("LeftButton");
				
				serviceWorker = new Button ("Service Workers");
				serviceWorker.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
				serviceWorker.getStyleClass().add("LeftButton");
				
				endOfDay = new Button ("End of Day");
				endOfDay.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
				endOfDay.getStyleClass().add("LeftButton");
				
				logout = new Button ("Logout");
				logout.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
				logout.getStyleClass().add("LeftButton");
				
				cashierVBox = new VBox ();
				cashierVBox.setAlignment(Pos.BOTTOM_CENTER);
				
					cashierHBox = new HBox ();
					cashierHBox.setId("CashierHBox");
					
					cashierNameLabel = new Label ();
					cashierNameLabel.setId("DefaultLabel");
					
					cashierLabel = new Label ("Cashier");
					cashierLabel.setId("DefaultLabel");
					
					cashierHBox.getChildren().addAll(cashierNameLabel, cashierLabel);
				
				cashierVBox.getChildren().addAll(cashierHBox);
				
		leftVBox.getChildren().addAll(returnItem, serviceWorker, endOfDay, logout);
		
		VBox.setVgrow(cashierVBox, Priority.ALWAYS);
		VBox.setVgrow (leftVBox, Priority.ALWAYS);
	}

	private void initCenter() {
		centerVBox = new VBox (10);
		centerVBox.setId("CenterVbox");
		
			centerTopHBox = new HBox ();
		
				searchHBox = new HBox ();
				searchHBox.setAlignment(Pos.CENTER_LEFT);
				
					filterComboBox = new ComboBox<String> ();
					filterComboBox.getStyleClass().add("ComboBox");
					filterComboBox.getItems().add("Item Code");
					filterComboBox.getItems().add("Description");
					filterComboBox.getItems().add("Service");
					
					filterComboBox.getSelectionModel ().selectFirst ();
					
					searchTextField = new TextField ();
					searchTextField.setId ("TextField");
					searchTextField.setPromptText("Search...");
					
					searchButton = new Button ();
					searchButton.getStyleClass ().add("SearchButton");
					searchButton.setMinSize(40, 40);
						
				searchHBox.getChildren().addAll(searchTextField, searchButton);
				
				cartHBox = new HBox();
				cartHBox.setAlignment(Pos.CENTER_RIGHT);
				
					cartButton = new Button ("Add to cart");
					cartButton.getStyleClass ().add("GreenButton");
				
				cartHBox.getChildren().addAll(cartButton);
		
			centerTopHBox.getChildren().addAll(searchHBox, cartHBox);
			
			ivtp = new InventoryViewTabPane (cvc);
			
		centerVBox.getChildren().addAll(centerTopHBox, ivtp);
		
		HBox.setHgrow(searchHBox, Priority.ALWAYS);
		HBox.setHgrow(cartHBox, Priority.ALWAYS);
		VBox.setVgrow (centerVBox, Priority.ALWAYS);
	}

	private void initRight() {
		rightVBox = new VBox (15);
		rightVBox.setId("RightVbox");
			
			checkoutHBox = new HBox ();
			checkoutHBox.setAlignment(Pos.TOP_CENTER);
				
				checkoutLeftHBox = new HBox ();
				checkoutLeftHBox.setAlignment(Pos.CENTER_LEFT);
				
					totalLabel = new Label ("Total: ");
					totalLabel.setId ("DefaultLabel");
				
				checkoutLeftHBox.getChildren().addAll(totalLabel);
				
				checkoutRightHBox = new HBox (10);
				checkoutRightHBox.setAlignment(Pos.CENTER_RIGHT);
				
					clearCartButton = new Button ("Clear Cart");
					clearCartButton.getStyleClass().add("RedButton");
				
					checkoutButton = new Button ("Checkout");
					checkoutButton.getStyleClass().add("GreenButton");
				
				checkoutRightHBox.getChildren().addAll(clearCartButton, checkoutButton);
				
			checkoutHBox.getChildren ().addAll (checkoutLeftHBox, checkoutRightHBox);
			
			cartOptionsHBox = new HBox ();
			cartOptionsHBox.setAlignment(Pos.CENTER);
			
				cartOptionsLeftHBox = new HBox (10);
				cartOptionsLeftHBox.setAlignment(Pos.CENTER_LEFT);
				
					holdButton = new Button ("Hold Cart");
					holdButton.getStyleClass().add("Button");
					
					overridePriceButton = new Button ("Override Price");
					overridePriceButton.getStyleClass().add("Button");
					
					clearItemButton = new Button ("Clear Item");
					clearItemButton.getStyleClass().add("Button");
					
				cartOptionsLeftHBox.getChildren().addAll(holdButton, overridePriceButton, clearItemButton);
				
				cartOptionsRightHBox = new HBox ();
				cartOptionsRightHBox.setAlignment(Pos.CENTER_RIGHT);
					
					toggleGroup = new ToggleGroup ();
					
					retailButton = new ToggleButton ("Retail Sale");
					retailButton.getStyleClass().add("RetailButton");
					retailButton.setToggleGroup(toggleGroup);
					retailButton.setSelected(true);
					transaction = "retail";
					
					wholeSaleButton = new ToggleButton ("Whole Sale");
					wholeSaleButton.getStyleClass().add("WholesaleButton");
					wholeSaleButton.setToggleGroup(toggleGroup);
				
				cartOptionsRightHBox.getChildren().addAll(retailButton, wholeSaleButton);
				
				resumeButton = new Button ("Resume Cart");
				resumeButton.getStyleClass().add("GreenButton");
				
				removeCartButton = new Button ("Remove Cart");
				removeCartButton.getStyleClass().add("RedButton");
						
			cartOptionsHBox.getChildren ().addAll (cartOptionsLeftHBox, cartOptionsRightHBox);
						
			cvtp = new CartViewTabPane (cvc);
			cvtp.setView(this);
			
		rightVBox.getChildren ().addAll (cvtp, cartOptionsHBox, checkoutHBox);
		
		HBox.setHgrow (checkoutHBox, Priority.ALWAYS);
		HBox.setHgrow (checkoutLeftHBox, Priority.ALWAYS);
		HBox.setHgrow (checkoutRightHBox, Priority.ALWAYS);
		HBox.setHgrow (cartOptionsLeftHBox, Priority.ALWAYS);
		HBox.setHgrow (cartOptionsRightHBox, Priority.ALWAYS);
		VBox.setVgrow (rightVBox, Priority.ALWAYS);
	}
	
	private void initHandlers () {
		menuButton.setOnAction(e -> {
			if (menuButton.isSelected())
				setLeft (leftVBox);
			else
				setLeft(null);
		});
		
		
		//center
		/*filterComboBox.setOnAction(e -> {
			switch(filterComboBox.getValue()){
				case "Item Code": 
				case "Description": cvc.getAllItems(new String[]{InventoryView.KEY});
					break;
				case "Service": cvc.getAllServices();
					break;
			}
		});*/
		
		searchButton.setOnAction(e -> {
			if(!searchTextField.getText().equals("")){
				if(ivtp.getTab() == 0){
					//inventory
					cvc.searchItem(new String[]{InventoryView.KEY}, searchTextField.getText());
				}else{
					//service
					cvc.searchService(searchTextField.getText());
				}
			}else{
				new AlertBoxPopup("Search", "No matches found.");
			}
		});
		
		cartButton.setOnAction(e -> {
			ObservableList<String> row;
			if(ivtp.getTab() == 0)
				row = ivtp.getInventoryView().getSelectedItem();
			else
				row = ivtp.getServiceView().getSelectedItem();
			
			if(row != null){
				if(ivtp.getTab() == 0){
					CartPopup popup = null;
					//item is selected
					if(Integer.parseInt(row.get(InventoryView.STOCK)) > 0) {
						popup = new CartPopup (cvc, row);
						if(popup.getQuantity() <= Integer.parseInt(row.get(InventoryView.STOCK))){
							ivtp.getInventoryView().minusStock(row.get(InventoryView.ITEM_CODE), popup.getQuantity());
						}
					}else
						new AlertBoxPopup("Stock", "There is currently 0 stock of this item.");
					
				}else{
					//service is seleted
					ServicePopup popup = new ServicePopup (cvc);
					if(!popup.isCancel()){
						if(popup.isSuccess()){
							cvc.addToCart(CartItemType.SERVICE,
									Integer.parseInt(row.get(ServiceView.SERVICE_ID)),
									popup.getWorkerId(),
									row.get(ServiceView.SERVICE_NAME) + " ("+ popup.getWorkerName() + ")",
									BigDecimal.valueOf(Double.parseDouble(row.get(ServiceView.SERVICE_PRICE))),
									1);
						}
					}
				}
			}else{
				new AlertBoxPopup("Error", "No selected item.");
			}
		});
		
		//left
		returnItem.setOnAction(e -> {
			new ReturnItemPopup (cvc, ivtp.getInventoryView());
		});
		
		serviceWorker.setOnAction(e -> {
			new ServiceWorkerPopup (cvc);
		});
		
		endOfDay.setOnAction(e -> {
			new EndOfDayPopup (cvc);
		});
		
		logout.setOnAction(e -> {
			cvc.logout();
		});                   
		
		//right
		retailButton.setOnAction(e -> {
			retailButton.setSelected(true);
			transaction = "retail";
		});
		
		wholeSaleButton.setOnAction(e -> {
			wholeSaleButton.setSelected(true);
			transaction = "wholesale";
		});
		
		clearItemButton.setOnAction(e -> {
			ObservableList<String> row = cvtp.getOngoingCartView().getSelectedItem();
			ClearItemPopup popup;
			if(row != null){
				if(CartItemType.valueOf(row.get(CartView.TYPE)) == CartItemType.ITEM){
					popup = new ClearItemPopup(cvc, row.get(CartView.ITEM_CODE));
					ivtp.getInventoryView().addStock(row.get(CartView.ITEM_CODE), popup.getQuantity());
				}else
					cvc.removeCartItem(Integer.parseInt(row.get(CartView.ITEM_CODE)), 1);
			}else{
				new AlertBoxPopup("Error", "No selected cart item.");
			}

		});
		
		clearCartButton.setOnAction(e -> {
			//ui
			for(CartItem item : cvc.getCart().getCartItems())
				if(item.getType() == CartItemType.ITEM)
					ivtp.getInventoryView().addStock(item.getItemCode(), item.getQuantity());
			//backend
			cvc.clearCart();
			new AlertBoxPopup("Cart", "Cart is cleared.");
		});
		
		checkoutButton.setOnAction(e -> {
			if(!cvc.getCart().getCartItems().isEmpty())
				new CheckoutPopup (cvc);
			else
				new AlertBoxPopup("Cart", "Cart is empty, can't proceed to checkout.");
		});
		
		overridePriceButton.setOnAction(e -> {
			
				ObservableList<String> row = cvtp.getOngoingCartView().getSelectedItem();
				if(row.get(CartView.TYPE).equals("ITEM")){
					if(row != null){
						ManagerPopup pop = new ManagerPopup (cvc);
						if(pop.getAccess()){
							new OverridePricePopup(cvc, row.get(CartView.ITEM_CODE));
						}else{
							if(!pop.isCanceled())
								new AlertBoxPopup("Access", "Access Denied.");
						}
					}else{
						new AlertBoxPopup("Error", "No selected cart item.");
					}
				}else{
					new AlertBoxPopup("Error", "Can't override services.");
				}
		
		});
		
		holdButton.setOnAction(e -> {
			new CustomerConfirmationPopup(cvc);
		});
		
		resumeButton.setOnAction(e -> {
			ObservableList<String> row = cvtp.getHoldView().getSelectedItem();
			
			if(row != null){
				cvc.restoreCart(Integer.parseInt(row.get(HoldView.NUMBER)) - 1);
				new AlertBoxPopup("Success", "Cart was resumed.");
			}else{
				new AlertBoxPopup("Error", "No selected cart.");
			}
		});
		
		removeCartButton.setOnAction(e -> {
			ObservableList<String> row = cvtp.getHoldView().getSelectedItem();
			
			if(row != null){
				cvc.removeHeldCart(Integer.parseInt(row.get(HoldView.NUMBER)) - 1);
				new AlertBoxPopup("Success", "Cart was removed.");
			}else{
				new AlertBoxPopup("Error", "No selected cart.");
			}
		});
		
	}
	
	public void attach(){
		//put all attaching of views here
		Database.getInstance().attach(KEY, this);
		Database.getInstance().attach(InventoryView.KEY, ivtp.getInventoryView());
		Database.getInstance().attach(ServiceView.KEY, ivtp.getServiceView());
		cvtp.attach();
	}
	
	public void detach(){
		//put all detaching of vies here
		Database.getInstance().detach(KEY);
		Database.getInstance().detach(InventoryView.KEY);
		Database.getInstance().detach(ServiceView.KEY);
		cvtp.detach();
	}
	
	@Override
	public void update() {
		BigDecimal totalPrice = BigDecimal.valueOf(0);
		
		for (CartItem item : cvc.getCart().getCartItems()) {
			totalPrice = totalPrice.add(item.getPriceSold().multiply(BigDecimal.valueOf(item.getQuantity())));
		}
		
		totalLabel.setText("Total: PHP " + totalPrice.toString());
		
		if(cvtp.getTab() == 0) {
			if (!cartOptionsHBox.getChildren().isEmpty())
				cartOptionsHBox.getChildren().removeAll(cartOptionsHBox.getChildren());
			
			if (!checkoutHBox.getChildren().isEmpty())
				checkoutHBox.getChildren().removeAll(checkoutHBox.getChildren());
			
			cartOptionsHBox.getChildren ().addAll (cartOptionsLeftHBox, cartOptionsRightHBox);
			checkoutHBox.getChildren ().addAll (checkoutLeftHBox, checkoutRightHBox);
		}
		else if (cvtp.getTab() == 1) {
			if (!cartOptionsHBox.getChildren().isEmpty())
				cartOptionsHBox.getChildren().removeAll(cartOptionsHBox.getChildren());
			
			if (!checkoutHBox.getChildren().isEmpty())
				checkoutHBox.getChildren().removeAll(checkoutHBox.getChildren());
			
			cartOptionsHBox.getChildren ().addAll (removeCartButton, resumeButton);
		}
		
		if(cvc.getCart().getTransactionType().equals("retail")){
			retailButton.setSelected(true);
		}else{
			wholeSaleButton.setSelected(true);
		}
		
		cvc.getMainStage().sizeToScene();
	}
	
}

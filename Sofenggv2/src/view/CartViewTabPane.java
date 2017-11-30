package view;

import controller.CashierViewController;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import model.Database;

public class CartViewTabPane extends TabPane implements View {

	private CashierViewController cvc;
	
	private CartView cartViewOngoing;
	//private HoldView cartViewHold;
	
	private Tab ongoingTab;
	private Tab holdTab;
	private ToggleGroup toggleGroup;
		private ToggleButton retailButton;
		private ToggleButton wholeSaleButton;
		
	public CartViewTabPane (CashierViewController cvc) {
		super();
		
		this.cvc = cvc;
		
		initTabPane();
		initHanders();
	}
	
	private void initTabPane() {
		initCartViews();
		initTabs ();
		
		getTabs().addAll(ongoingTab, holdTab);
		setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		setId ("Pane");
	}
	
	private void initCartViews() {
		cartViewOngoing = new CartView (cvc);
		//cartViewHold = new HoldView (cvc);
	}

	private void initTabs() {
		ongoingTab = new Tab ("Ongoing");
		ongoingTab.getStyleClass().add("Tab");
		ongoingTab.setContent(cartViewOngoing);
		
		holdTab = new Tab ("Hold");
		holdTab.getStyleClass().add("Tab");
		//holdTab.setContent(cartViewHold);
	}
	
	private void initHanders() {
	
	}
	
	public void attach(){
		Database.getInstance().attach(CartView.KEY, cartViewOngoing);
	}
	
	public void detach(){
		Database.getInstance().detach(CartView.KEY);
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

}

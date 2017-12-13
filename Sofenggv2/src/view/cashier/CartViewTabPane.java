package view.cashier;

import controller.CashierViewController;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import model.Database;
import view.View;

public class CartViewTabPane extends TabPane implements View {

	private CashierViewController cvc;
	
	private CartView cartViewOngoing;
	private HoldView cartViewHold;
	
	private Tab ongoingTab;
	private Tab holdTab;
	
	private View view;
		
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
		cartViewHold = new HoldView (cvc);
	}

	private void initTabs() {
		ongoingTab = new Tab ("Ongoing");
		ongoingTab.getStyleClass().add("Tab");
		ongoingTab.setContent(cartViewOngoing);
		
		holdTab = new Tab ("Hold");
		holdTab.getStyleClass().add("Tab");
		holdTab.setContent(cartViewHold);
	}
	
	private void initHanders() {
		ongoingTab.setOnSelectionChanged(e -> {
			view.update();
		});
		holdTab.setOnSelectionChanged(e -> {
			view.update();
		});
	}
	
	public void setView(View view) {
		this.view = view;
	}
	
	public void attach(){
		Database.getInstance().attach(CartView.KEY, cartViewOngoing);
		Database.getInstance().attach(HoldView.KEY, cartViewHold);
	}
	
	public void detach(){
		Database.getInstance().detach(CartView.KEY);
		Database.getInstance().detach(HoldView.KEY);
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
	public CartView getOngoingCartView(){
		return cartViewOngoing;
	}
	
	public HoldView getHoldView(){
		return cartViewHold;
	}
	
	public int getTab () {
		return getSelectionModel().getSelectedIndex();
	}

}

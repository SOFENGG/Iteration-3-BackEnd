package view.cashier;

import controller.CashierViewController;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import view.View;

public class InventoryViewTabPane extends TabPane implements View {
	
	private CashierViewController cvc;
	
	private InventoryView iv;
	private ServiceView sv;
	
	private Tab inventoryTab;
	private Tab serviceTab;

	public InventoryViewTabPane (CashierViewController cvc) {
		super ();
		
		this.cvc = cvc;
		initTabPane();
		initHanders();
	}
	
	private void initTabPane() {
		initInventoryViews();
		initTabs ();
		
		getTabs().addAll(inventoryTab, serviceTab);
		setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		setId ("Pane");
	}
	
	private void initInventoryViews() {
		iv = new InventoryView (cvc);
		sv = new ServiceView (cvc);
	}

	private void initTabs() {
		inventoryTab = new Tab ("Inventory");
		inventoryTab.getStyleClass().add("Tab");
		inventoryTab.setContent(iv);
		
		serviceTab = new Tab ("Service");
		serviceTab.getStyleClass().add("Tab");
		serviceTab.setContent(sv);
	}
	
	private void initHanders() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
	public InventoryView getInventoryView () {
		return iv;
	}

}

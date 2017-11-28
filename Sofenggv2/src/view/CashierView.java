package view;

import controller.CashierViewController;
import javafx.scene.layout.BorderPane;
import model.Database;

public class CashierView extends BorderPane implements View{
	
	private CashierViewController cvc;
	
	private InventoryView iv;
	
	public CashierView (CashierViewController cvc) {
		super ();
		this.cvc = cvc;
		
		Database.getInstance().attach(this);
		
		initPane ();
	}

	private void initPane() {
		setMaxSize (Double.MAX_VALUE, Double.MAX_VALUE);
		initCenter();
		setCenter(iv);
	}

	private void initCenter() {
		iv = new InventoryView (cvc);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

}

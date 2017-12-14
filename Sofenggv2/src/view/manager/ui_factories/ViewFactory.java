package view.manager.ui_factories;

import controller.ManagerViewController;
import model.Database;
import view.manager.ui.CustomerView;
import view.manager.ui.InventoryView;
import view.manager.ui.PurchaseOrderView;
import view.manager.ui.SalesReportView;
import view.manager.ui.SupplierView;
import view.manager.ui.TransactionView;
import view.manager.ui.MainView;

public class ViewFactory {
	
	private final static int TRANSACTION_CODE = 00;
	private final static int CUSTOMER_CODE = 01;
	private final static int INVENTORY_CODE = 02;
	private final static int SUPPLIER_CODE = 03;
	private final static int PURCHASE_ORDER_CODE = 04;
	private final static int SALES_REPORT_CODE = 05;
	
	public static MainView getView(int code, ManagerViewController mvc) {
		switch(code) {
		case TRANSACTION_CODE:
			//tries to detach view first (if there are any previous transaction view)
			Database.getInstance().detach(InventoryView.KEY);
			
			TransactionView tv = new TransactionView(mvc);
			
			//attach view to database
			Database.getInstance().attach(TransactionView.KEY, tv);
			
			//puts the inital contents of the table
			mvc.getCurrentTransactions(new String[]{TransactionView.KEY});
			return tv;
		case CUSTOMER_CODE:
			//tries to detach view first (if there are any previous customer view)
			Database.getInstance().detach(CustomerView.KEY);
			
			CustomerView cv = new CustomerView(mvc);
			
			//attach view to database
			Database.getInstance().attach(CustomerView.KEY, cv);
			
			//puts the inital contents of the table
			mvc.getAllCustomers(new String[]{CustomerView.KEY});
			return cv;
		case INVENTORY_CODE:
			//tries to detach view first (if there are any previous inventory view)
			Database.getInstance().detach(InventoryView.KEY);
			
			InventoryView iv = new InventoryView(mvc);
			
			//attach view to database
			Database.getInstance().attach(InventoryView.KEY, iv);
			
			//puts the inital contents of the table
			mvc.getAllItems(new String[]{InventoryView.KEY});
			return iv;
		case SUPPLIER_CODE:
			//tries to detach view first (if there are any previous supplier view)
			Database.getInstance().detach(SupplierView.KEY);
			
			SupplierView sv = new SupplierView(mvc);
			
			//attach view to database
			Database.getInstance().attach(SupplierView.KEY, sv);
			
			//puts the initial contents of the table
			mvc.getAllSuppliers(new String[]{SupplierView.KEY});
			return sv;
		case PURCHASE_ORDER_CODE:
			return new PurchaseOrderView(mvc);
		case SALES_REPORT_CODE:
			return new SalesReportView(mvc);
		default:
			return new TransactionView(mvc);
		}
	}
}

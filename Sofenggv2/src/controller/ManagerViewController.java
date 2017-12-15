package controller;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Customer;
import model.Database;
import model.Item;
import model.ItemLog;
import model.ItemOrder;
import model.PurchaseOrder;
import model.Service;
import model.Supplier;
import model.Transaction;
import model.User;
import model.Worker;
import view.manager.ui.ManagerView;

public class ManagerViewController {

	private MainController mc;
	private ManagerView mv;
	
	private User manager;
	
	private String filter = "" + Transaction.COLUMN_DATE_SOLD + " > date_add(curdate(), interval -7 day) order by " + Transaction.COLUMN_TRANSACTION_ID + " ASC;";
	
	public ManagerViewController (MainController mc) {
		this.mc = mc;
		mv = new ManagerView (this);
	}
	
	public Pane getView (int view) {
		switch(view) {
			case Code.MANAGER_VIEW: return mv;
		}
		return null;
	}
	
	public Stage getMainStage () {
		return mc.getStage ();
	}
	
	public MainController getMainController() {
		return mc;
	}
	
	public void changeControl (int requestCode, int view) {
		mc.setScene(requestCode, view);
	}
	
	public void attach(){
		/*
		 * attach all manager related views here
		 */
		//mv.attach();
	}
	
	public void detach(){
		//mv.detach();
	}
	
	public void setUser(User manager){
		this.manager = manager;
		System.out.println("Welcome Manager " + manager.getName());
	}
	
	public void logout(){
		this.manager = null;
		changeControl(Code.LC_CODE, Code.LOGIN_VIEW);
	}
	
	public User getUser() {
		return manager;
	}
	
	//manager view services
	
	//customers
		public void getAllCustomers(String[] keys){
			Database.getInstance().query(keys,
					"select * from " + Customer.TABLE);
		}
		
		public void searchCustomer(String[] keys, int accountId){
			String sql = "";
			sql = "select * from " + Customer.TABLE +
					" where " + Customer.COLUMN_ACCOUNT_ID + " = " + accountId + ";";
			Database.getInstance().query(keys,
					sql);
		}
		
		public void searchCustomerName(String[] keys, String search){
			String sql = "";
			sql = "select * from " + Customer.TABLE +
					" where " + Customer.COLUMN_NAME + " like '" + search + "%' OR "+Customer.COLUMN_NAME+" like '% "+search+"%';";
			Database.getInstance().query(keys,
					sql);
		}

		public void searchCustomerAddress(String[] keys, String search){
			String sql = "";
			sql = "select * from " + Customer.TABLE +
					" where " + Customer.COLUMN_ADDRESS + " like '" + search + "%' OR "+Customer.COLUMN_ADDRESS+" like '% "+search+"%';";
			Database.getInstance().query(keys,
					sql);
		}
		
		public void addCustomer(String name, String contactNumber, String address, BigDecimal debtLimit){
			String sql = "insert into " + Customer.TABLE + " ("+Customer.COLUMN_NAME+
					", "+Customer.COLUMN_ADDRESS+
					", "+Customer.COLUMN_CONTACT_NUMBER+
					", "+Customer.COLUMN_TOTAL_VISITS+
					", "+Customer.COLUMN_DEBT+
					", "+Customer.COLUMN_DEBT_LIMIT+") values (?, ?, ?, ?, ?, ?)";
			
			try{
				PreparedStatement statement = Database.getInstance().getConnection().prepareStatement(sql);
				statement.setString(1, name); 
				statement.setString(2, address);
				statement.setString(3, contactNumber);
				statement.setInt(4, 0);
				statement.setBigDecimal(5, BigDecimal.valueOf(0));
				statement.setBigDecimal(6, debtLimit);
				Database.getInstance().execute(statement);
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		

		//updates the debt limit of the customer
		public void updateDebtLimit(int accountId, BigDecimal debtLimit){
			String update = "update " + Customer.TABLE + 
					" set "+ Customer.COLUMN_DEBT_LIMIT + " = ?"+
					" where " + Customer.COLUMN_ACCOUNT_ID + " = ?";
			
			try{
				PreparedStatement statement = Database.getInstance().getConnection().prepareStatement(update);
				statement.setBigDecimal(1, debtLimit); 
				statement.setInt(2, accountId);
				Database.getInstance().executeUpdate(statement);
			}catch(SQLException e){
				e.printStackTrace();
			}

		}
		
		//updates the current debt of the customer
		public void updateDebt(int accountId, BigDecimal debt){
			String update = "update " + Customer.TABLE + 
					" set "+ Customer.COLUMN_DEBT + " = ?"+
					" where " + Customer.COLUMN_ACCOUNT_ID + " = ?";
			
			try{
				PreparedStatement statement = Database.getInstance().getConnection().prepareStatement(update);
				statement.setBigDecimal(1, debt); 
				statement.setInt(2, accountId);
				Database.getInstance().executeUpdate(statement);
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		
		//items
		public void getAllItems(String[] keys) {
			Database.getInstance().query(keys,
					"select * from " + Item.TABLE);
		}
		
		public void searchItem(String[] keys, String search) {
			Database.getInstance().query(keys,
					"select * from "+Item.TABLE+" where concat("+Item.COLUMN_ITEM_CODE+", "+Item.COLUMN_NAME+", "+Item.COLUMN_DESCRIPTION+", "+Item.COLUMN_CATEGORY+", "+Item.COLUMN_MANUFACTURER+") like '%" + search + "%';");
		}
		
		public void editItem(String itemCode, String description, String supplierCode, BigDecimal price){
			String update = "update " + Item.TABLE + 
					" set "+ Item.COLUMN_DESCRIPTION + " = ?"+
					", " + Item.COLUMN_SUPPLIER_CODE + " = ?"+
					", " + Item.COLUMN_PRICE_CUSTOMER + " = ?" +
					" where " + Item.COLUMN_ITEM_CODE + " = ?";
			
			try{
				PreparedStatement statement = Database.getInstance().getConnection().prepareStatement(update);
				statement.setString(1, description); 
				statement.setString(2, supplierCode);
				statement.setBigDecimal(3, price);
				statement.setString(4, itemCode);
				Database.getInstance().executeUpdate(statement);
			}catch(SQLException e){
				e.printStackTrace();
			}

			
		}

		//services
		public void getAllServices(String[] keys){
			Database.getInstance().query(keys,
					"select * from " + Service.TABLE);
		}
		
		public void searchService(String[] keys, String search){
			Database.getInstance().query(keys,
					"select * from "+Service.TABLE+" where concat("+Service.COLUMN_SERVICE_NAME+", "+ Service.COLUMN_PRICE + ") like '" + search + "%' OR concat("+Service.COLUMN_SERVICE_NAME+", "+ Service.COLUMN_PRICE + ") like '% "+search+"%';");
		}
		
		public void getAllSerivceWorkers(String[] keys){
			Database.getInstance().query(keys,
					"select * from " + Worker.TABLE);
		}
		
		public void getServiceWorkerWithID(String[] keys, int id){
			Database.getInstance().query(keys,
					"select * from " + Worker.TABLE +
					" where " + Worker.COLUMN_WORKER_ID + " = " + id + ";");
		}
		
		public void getServiceWorkerWithName(String[] keys, String name){
			Database.getInstance().query(keys,
					"select * from " + Worker.TABLE +
					" where " + Worker.COLUMN_NAME + " like '" + name + "%' OR "+Worker.COLUMN_NAME+" like '% "+name+"%';");
		}
	
	//supplier
	public void getAllSuppliers(String[] keys){
		Database.getInstance().query(keys, 
				"select * from " + Supplier.TABLE);
	}
	
	public void addSupplier(Supplier supplier){
		String sql = "insert into " + Supplier.TABLE + " ("+Supplier.COLUMN_SUPPLIER_CODE+
				", "+Supplier.COLUMN_NAME+
				", "+Supplier.COLUMN_CONTACT_PERSON+
				", "+Supplier.COLUMN_CONTACT_NUMBER+
				", "+Supplier.COLUMN_TAX_ID+") values (?, ?, ?, ?, ?)";
		
		try{
			PreparedStatement statement = Database.getInstance().getConnection().prepareStatement(sql);
			statement.setString(1, supplier.getSupplierCode()); 
			statement.setString(2, supplier.getName());
			statement.setString(3, supplier.getContactPerson());
			statement.setString(4, supplier.getContactNumber());
			statement.setString(5, supplier.getTaxID());
			Database.getInstance().execute(statement);
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void searchSupplierByCode(String[] keys, String supplierCode){
		Database.getInstance().query(keys,
				"select * from " + Supplier.TABLE +
				" where " + Supplier.COLUMN_SUPPLIER_CODE + " like '" + supplierCode + "%';");
	}
	
	public void searchSupplierByName(String[] keys, String name){
		Database.getInstance().query(keys,
				"select * from " + Supplier.TABLE +
				" where " + "concat("+ Supplier.COLUMN_NAME + ", " + Supplier.COLUMN_CONTACT_PERSON + ")" + " like '%" + name + "%';");
	}
	
	//transactions
	public void getCurrentTransactions(String[] keys){
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Database.getInstance().query(keys, 
				"select * from " + Transaction.TABLE +
				" where " + Transaction.COLUMN_DATE_SOLD + " like '" + sdf.format(now) + "'" +
				" order by " + Transaction.COLUMN_TRANSACTION_ID + " ASC;");
	}
	
	public void searchCurrentTransactionsByTransactionID(String[] keys, int transactionID){
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Database.getInstance().query(keys,
				"select * from " + Transaction.TABLE +
				" where " + Transaction.COLUMN_DATE_SOLD + " like '" + sdf.format(now) + "' and " + Transaction.COLUMN_TRANSACTION_ID + " like '" + transactionID + "'" +
				" order by " + Transaction.COLUMN_TRANSACTION_ID + " ASC;");
	}
	
	
	public void searchCurrentTransactionsByUserID(String[] keys, int userID){
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		 		
		Database.getInstance().query(keys,
		 		"select * from " + Transaction.TABLE +
		 		" where " + Transaction.COLUMN_DATE_SOLD + " like '" + sdf.format(now) + "' and " + Transaction.COLUMN_USER_ID + " like '" + userID + "'" +
		 		" order by " + Transaction.COLUMN_TRANSACTION_ID + " ASC;");
 	}
		 	
	public void searchCurrentTransactionsByTransactionType(String[] keys, String transactionType){
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Database.getInstance().query(keys,
				"select * from " + Transaction.TABLE +
				" where " + Transaction.COLUMN_DATE_SOLD + " like '" + sdf.format(now) + "' and " + Transaction.COLUMN_TRANSACTION_TYPE + " like '%" + transactionType + "%'" +
				" order by " + Transaction.COLUMN_TRANSACTION_ID + " ASC;");
	}
	
	public void searchCurrentTransactionsByIsLoan(String[] keys, int isLoan){
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Database.getInstance().query(keys,
				"select * from " + Transaction.TABLE +
				" where " + Transaction.COLUMN_DATE_SOLD + " like '" + sdf.format(now) + "' and " + Transaction.COLUMN_IS_LOAN + " like '" + isLoan + "'" +
				" order by " + Transaction.COLUMN_TRANSACTION_ID + " ASC;");
	}
	
	public void searchCurrentTransactionsByDateSold(String[] keys, String dateSold){
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Database.getInstance().query(keys,
				"select * from " + Transaction.TABLE +
				" where " + Transaction.COLUMN_DATE_SOLD + " like '" + sdf.format(now) + "' and " + Transaction.COLUMN_DATE_SOLD + " like '%" + dateSold + "%'" +
				" order by " + Transaction.COLUMN_TRANSACTION_ID + " ASC;");
	}
	
	public void searchCurrentTransactionsByTotalPrice(String[] keys, BigDecimal totalPrice){
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Database.getInstance().query(keys,
				"select * from " + Transaction.TABLE +
				" where " + Transaction.COLUMN_DATE_SOLD + " like '" + sdf.format(now) + "' and " + Transaction.COLUMN_TOTAL_PRICE + " = " + totalPrice.toString() +
				" order by " + Transaction.COLUMN_TRANSACTION_ID + " ASC;");
	}
		 	
	public void setFilter(ArrayList<Object> filters) {
		int i = 0;
		StringBuilder sb = new StringBuilder();
		
		//parse where clause
		//parse whether preset or custom is selected
		//0 = preset, 1 = custom
		if ((Integer) filters.get(i) == 0) {
			i++;
			//preset selected
			//get index of selected time frame
			//0 = 7 days, 1 = 5 days, 2 = 3 months, 3 = 6 months, 4 = 1 year
			switch ((Integer) filters.get(i)) {
			case 0:
				sb.append("" + Transaction.COLUMN_DATE_SOLD + " > date_add(curdate(), interval -7 day)");
				break;
			case 1:
				sb.append("" + Transaction.COLUMN_DATE_SOLD + " > date_add(curdate(), interval -5 day)");
				break;
			case 2:
				sb.append("" + Transaction.COLUMN_DATE_SOLD + " > date_add(curdate(), interval -3 month)");
				break;
			case 3:
				sb.append("" + Transaction.COLUMN_DATE_SOLD + " > date_add(curdate(), interval -6 month)");
				break;
			case 4:
				sb.append("" + Transaction.COLUMN_DATE_SOLD + " > date_add(curdate(), interval -1 year)");
				break;
			}
			
			//append default order by clause
			//ascending transaction #
			sb.append(" order by " + Transaction.COLUMN_TRANSACTION_ID + " ASC;");
		} else {
			i++;
			//custom selected
			//get start and end date - using prefix increment - then set as bounds for inclusive between
			sb.append("" + Transaction.COLUMN_DATE_SOLD + " between '" + filters.get(i) + "' and '" + filters.get(++i) + "'");
			i++;
			//parse whether all week or select days is selected
			//0 = all week, 1 = select days
			if ((Integer) filters.get(i) == 0) {
				//all week selected
				//do nothing, since all days are included
			} else {
				i++;
				//select days selected
				//add in clause with day indeces to be used with sql dayofweek()
				@SuppressWarnings("unchecked")
				ArrayList<Integer> selectedDays = (ArrayList<Integer>) filters.get(i);
				sb.append(" and dayofweek(" + Transaction.COLUMN_DATE_SOLD + ") in (");
				
				for (int j = 0; j < selectedDays.size(); j++) {
					sb.append(selectedDays.get(j));
					if ((j + 1) != selectedDays.size())
						sb.append(", ");
				}
				sb.append(")");
			}
			
			i++;
			sb.append(" order by ");
			//parse order by clause
			//parse whether ascending or descending is selected
			//0 = ascending, 1 = descending
			if ((Integer) filters.get(i) == 0) {
				i++;
				//ascending selected
				//get criterion
				////0 = month, 1 = year, 2 = cost, 3 = transaction #
				switch ((Integer) filters.get(i)) {
				case 0:
					sb.append("month(" + Transaction.COLUMN_DATE_SOLD + ")");
					break;
				case 1:
					sb.append("" + Transaction.COLUMN_DATE_SOLD);
					break;
				case 2:
					sb.append("" + Transaction.COLUMN_TOTAL_PRICE);
					break;
				case 3:
					sb.append("" + Transaction.COLUMN_TRANSACTION_ID);
					break;
				}
				
				sb.append(" ASC;");
			} else {
				i++;
				//descending selected
				//get criterion
				////0 = month, 1 = year, 2 = cost, 3 = transaction #
				switch ((Integer) filters.get(i)) {
				case 0:
					sb.append("month(" + Transaction.COLUMN_DATE_SOLD + ")");
					break;
				case 1:
					sb.append("" + Transaction.COLUMN_DATE_SOLD);
					break;
				case 2:
					sb.append("" + Transaction.COLUMN_TOTAL_PRICE);
					break;
				case 3:
					sb.append("" + Transaction.COLUMN_TRANSACTION_ID);
					break;
				}
				
				sb.append(" DESC;");
			}
		}
		
		filter = sb.toString();
	}
	
	public void getFilteredTransactions(String[] keys){
		Database.getInstance().query(keys, 
			"select * from " + Transaction.TABLE +
			" where " + filter);
	}
	
	public void searchFilteredTransactionsByTransactionID(String[] keys, int transactionID){
		Database.getInstance().query(keys,
			"select * from " + Transaction.TABLE +
			" where " + Transaction.COLUMN_TRANSACTION_ID + " like '%" + transactionID + "%' and " + filter);
	}
	
	public void searchFilteredTransactionsByUserID(String[] keys, int userID){
		 Database.getInstance().query(keys,
		 	"select * from " + Transaction.TABLE +
		 	" where " + Transaction.COLUMN_USER_ID + " like '%" + userID + "%' and " + filter);
	}
		 	
 	public void searchFilteredTransactionsByTransactionType(String[] keys, String transactionType){
 		Database.getInstance().query(keys,
 			"select * from " + Transaction.TABLE +
 			" where " + Transaction.COLUMN_TRANSACTION_TYPE + " like '%" + transactionType + "%' and " + filter);
 	}
 	
 	public void searchFilteredTransactionsByIsLoan(String[] keys, int isLoan){
 		Database.getInstance().query(keys,
 			"select * from " + Transaction.TABLE +
 			" where " + Transaction.COLUMN_IS_LOAN + " = " + isLoan + " and " + filter);
 	}
 	
 	public void searchFilteredTransactionsByDateSold(String[] keys, String dateSold){
 		Database.getInstance().query(keys,
 			"select * from " + Transaction.TABLE +
 			" where " + Transaction.COLUMN_DATE_SOLD + " like '%" + dateSold + "%' and " + filter);
 	}
 	
 	public void searchFilteredTransactionsByTotalPrice(String[] keys, BigDecimal totalPrice){
 		Database.getInstance().query(keys,
 			"select * from " + Transaction.TABLE +
 			" where " + Transaction.COLUMN_TOTAL_PRICE + " = " + totalPrice.toString() + " and " + filter);
 	}
	
	public void getTransactionDetails(String[] keys, int transactionID) {
		Database.getInstance().query(keys,
			"select * from " + ItemLog.TABLE +
			" where " + ItemLog.COLUMN_TRANSACTION_ID + " like '%" + transactionID + "%'" +
			" order by " + ItemLog.COLUMN_TRANSACTION_ID + ";");
	}
	
	public void searchTransactionDetails(String[] keys, int transactionID, String search) {
		Database.getInstance().query(keys,
			"select * from " + ItemLog.TABLE +
			" where " + ItemLog.COLUMN_TRANSACTION_ID + " like '%" + transactionID + "%'" +
			"' and concat("+ItemLog.COLUMN_SALE_ID+", "+ItemLog.COLUMN_ITEM_CODE+", "+ItemLog.COLUMN_TYPE+", "+ItemLog.COLUMN_TRANSACTION_ID+", "+ItemLog.COLUMN_QUANTITY_SOLD+", "+ItemLog.COLUMN_ORIGINAL_SOLD+", " +ItemLog.COLUMN_PRICE_SOLD+") like '%" + search + "%';");
	}
	
	//purchase order
	public void getAllPurchaseOrders(String[] keys){
		Database.getInstance().query(keys, 
				"select * from " + PurchaseOrder.TABLE);
	}
	
	public void searchPurchaseOrdersByOrderID(String[] keys, int orderID){
		Database.getInstance().query(keys,
			"select * from " + PurchaseOrder.TABLE +
			" where " + PurchaseOrder.COLUMN_ORDER_ID + " = " + orderID + ";");
	}
	
	public void searchPurchaseOrdersBySupplierCode(String[] keys, String supplierCode){
		Database.getInstance().query(keys,
			"select * from " + PurchaseOrder.TABLE +
			" where " + PurchaseOrder.COLUMN_SUPPLIER_CODE + " like '%" + supplierCode + "%';");
	}
	
	public void searchPurchaseOrdersByTotalPrice(String[] keys, BigDecimal totalPrice){
		Database.getInstance().query(keys,
			"select * from " + PurchaseOrder.TABLE +
			" where " + PurchaseOrder.COLUMN_TOTAL_PRICE + " = " + totalPrice.toString() + ";");
	}
	
	public void searchPurchaseOrdersByDateOrdered(String[] keys, String dateOrdered){
		Database.getInstance().query(keys,
			"select * from " + PurchaseOrder.TABLE +
			" where " + PurchaseOrder.COLUMN_DATE_ORDERED + " like '%" + dateOrdered + "%';");
	}
	
	public void searchPurchaseOrdersByIsPending(String[] keys, int isPending){
		Database.getInstance().query(keys,
			"select * from " + PurchaseOrder.TABLE +
			" where " + PurchaseOrder.COLUMN_IS_PENDING + " = " + isPending + ";");
	}
	
	public void searchPurchaseOrdersByDateReceived(String[] keys, String dateReceived){
		Database.getInstance().query(keys,
			"select * from " + PurchaseOrder.TABLE +
			" where " + PurchaseOrder.COLUMN_DATE_RECEIVED + " like '%" + dateReceived + "%';");
	}
	
	public void addPurchaseOrder(PurchaseOrder purchaseOrder){
		String sql = "insert into " + PurchaseOrder.TABLE + " ("+PurchaseOrder.COLUMN_ORDER_ID+
				", "+PurchaseOrder.COLUMN_SUPPLIER_CODE+
				", "+PurchaseOrder.COLUMN_TOTAL_PRICE+
				", "+PurchaseOrder.COLUMN_DATE_ORDERED+
				", "+PurchaseOrder.COLUMN_IS_PENDING+
				", "+PurchaseOrder.COLUMN_DATE_RECEIVED+") values (?, ?, ?, ?, ?, ?)";
		
		try{
			PreparedStatement statement = Database.getInstance().getConnection().prepareStatement(sql);
			statement.setInt(1, purchaseOrder.getOrderID()); 
			statement.setString(2, purchaseOrder.getSupplierCode());
			statement.setBigDecimal(3, purchaseOrder.getTotalPrice());
			statement.setDate(4, purchaseOrder.getDateOrdered());
			statement.setInt(5, purchaseOrder.getIsPending());
			statement.setDate(6, purchaseOrder.getDateReceived());
			Database.getInstance().execute(statement);
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void addItemOrder(ItemOrder itemOrder) {
		String sql = "insert into " + PurchaseOrder.TABLE + " ("+ItemOrder.COLUMN_ORDER_ID+
				", "+ItemOrder.COLUMN_ITEM_CODE+
				", "+ItemOrder.COLUMN_QUANTITY+") values (?, ?, ?)";
		
		try{
			PreparedStatement statement = Database.getInstance().getConnection().prepareStatement(sql);
			statement.setInt(1, itemOrder.getOrderID()); 
			statement.setString(2, itemOrder.getItemCode());
			statement.setInt(3, itemOrder.getQuantity());
			Database.getInstance().execute(statement);
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void addItem(Item item){
		String sql = "insert into " + Item.TABLE + " ("+Item.COLUMN_ITEM_CODE+
				", "+Item.COLUMN_NAME+
				", "+Item.COLUMN_DESCRIPTION+
				", "+Item.COLUMN_CATEGORY+
				", "+Item.COLUMN_MANUFACTURER+
				", "+Item.COLUMN_SUPPLIER_CODE+
				", "+Item.COLUMN_STOCK+
				", "+Item.COLUMN_DATE_PURCHASE+
				", "+Item.COLUMN_PRICE_SUPPLIER+
				", "+Item.COLUMN_PRICE_CUSTOMER+") values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		try{
			PreparedStatement statement = Database.getInstance().getConnection().prepareStatement(sql);
			statement.setString(1, item.getItemCode()); 
			statement.setString(2, item.getName());
			statement.setString(3, item.getDescription());
			statement.setString(4, item.getCategory());
			statement.setString(5, item.getManufacturer());
			statement.setString(6, item.getSupplierCode());
			statement.setInt(7, item.getStock());
			statement.setDate(8, item.getDatePurchase());
			statement.setBigDecimal(9, item.getPriceSupplier());
			statement.setBigDecimal(10, item.getPriceCustomer());
			Database.getInstance().execute(statement);
		}catch(SQLException e){
			e.printStackTrace();
		}
	}

	public void getSupplierItems(String[] strings, String supplierCode) {
		// TODO Auto-generated method stub
		
	}
	
}

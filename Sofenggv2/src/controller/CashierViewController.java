package controller;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Cart;
import model.CartItem;
import model.CartItemType;
import model.Customer;
import model.DailyIncome;
import model.Database;
import model.Item;
import model.ItemLog;
import model.Service;
import model.ServiceLog;
import model.Transaction;
import model.User;
import model.Worker;
import util.CommonQuery;
import util.Query;
import view.CartView;
import view.CashierView;
import view.CustomerView;
import view.HoldView;
import view.InventoryView;
import view.ServiceWorkerView;

public class CashierViewController {

	private MainController mc;
	private CashierView cv;
	
	private User cashier;
	
	private Cart activeCart;
	//private ArrayList<CartItem> cartItems;
	private ArrayList<Cart> cartBuffer;
	
	public CashierViewController (MainController mc) {
		this.mc = mc;
		cv = new CashierView (this);
		
		//cartItems = new ArrayList<CartItem>();
		activeCart = new Cart("", new ArrayList<CartItem>(), "");
		cartBuffer = new ArrayList<Cart>();
	}
	
	public Pane getView (int view) {
		switch(view) {
			case Code.CASHER_VIEW: return cv;
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
		 * attaches all cashier related views to the model
		 */
		//note I made an attach method in CashierView because CashierView contains multiple Views
		cv.attach();
	}
	
	public void detach(){
		/*
		 * detaches all cashier related views in the model
		 */
		cv.detach();
	}
	 
	public void setUser(User cashier){
		this.cashier = cashier;
		System.out.println("Welcome Cashier " + cashier.getName());
	}
	
	public void logout(){
		this.cashier = null;
		changeControl(Code.LC_CODE, Code.LOGIN_VIEW);
	}
	
	public Cart getCart(){
		return activeCart;
	}
	
	//cashier view services
	//customers
	public void getAllCustomers(){
		Database.getInstance().query(new String[]{CustomerView.KEY},
				"select * from " + Customer.TABLE);
	}
	
	public void searchCustomer(int accountId){
		String sql = "";
		sql = "select * from " + Customer.TABLE +
				" where " + Customer.COLUMN_ACCOUNT_ID + " = " + accountId + ";";
		Database.getInstance().query(new String[]{CustomerView.KEY},
				sql);
	}
	
	public void searchCustomerName(String search){
		String sql = "";
		sql = "select * from " + Customer.TABLE +
				" where " + Customer.COLUMN_NAME + " like '" + search + "%';";
		Database.getInstance().query(new String[]{CustomerView.KEY},
				sql);
	}

	public void searchCustomerAddress(String search){
		String sql = "";
		sql = "select * from " + Customer.TABLE +
				" where " + Customer.COLUMN_ADDRESS + " like '%" + search + "%';";
		Database.getInstance().query(new String[]{CustomerView.KEY},
				sql);
	}
	
	//items
	public void getAllItems(String[] keys) {
		Database.getInstance().query(keys,
				"select " + Item.COLUMN_ITEM_CODE + ", "
							+ Item.COLUMN_STOCK + ", " 
							+ Item.COLUMN_NAME + ", "
							+ Item.COLUMN_DESCRIPTION + ", "
							+ Item.COLUMN_CATEGORY + ", "
							+ Item.COLUMN_MANUFACTURER + ", "
							+ Item.COLUMN_PRICE_CUSTOMER + " from " + Item.TABLE);
	}
	
	public void searchItem(String[] keys, String search) {
		Database.getInstance().query(keys,
				"select " + Item.COLUMN_ITEM_CODE + ", "
						+ Item.COLUMN_STOCK + ", " 
						+ Item.COLUMN_NAME + ", "
						+ Item.COLUMN_DESCRIPTION + ", "
						+ Item.COLUMN_CATEGORY + ", "
						+ Item.COLUMN_MANUFACTURER + ", "
						+ Item.COLUMN_PRICE_CUSTOMER + " from "+Item.TABLE+" where concat("+Item.COLUMN_ITEM_CODE+", "+Item.COLUMN_NAME+", "+Item.COLUMN_DESCRIPTION+", "+Item.COLUMN_CATEGORY+", "+Item.COLUMN_MANUFACTURER+") like '%" + search + "%';");
	}
	
	public void searchItemByCode(String[] keys, String code){
		Database.getInstance().query(keys,
				"select " + Item.COLUMN_ITEM_CODE + ", "
						+ Item.COLUMN_STOCK + ", " 
						+ Item.COLUMN_NAME + ", "
						+ Item.COLUMN_DESCRIPTION + ", "
						+ Item.COLUMN_CATEGORY + ", "
						+ Item.COLUMN_MANUFACTURER + ", "
						+ Item.COLUMN_PRICE_CUSTOMER + " from "+Item.TABLE+" where "+Item.COLUMN_ITEM_CODE+" = '"+code+"';");
	}
	
	//services
	public void getAllServices(){
		Database.getInstance().query(new String[] {InventoryView.KEY},
				"select * from " + Service.TABLE);
	}
	
	public void searchService(String search){
		Database.getInstance().query(new String[] {InventoryView.KEY},
				"select * from "+Service.TABLE+" where concat("+Service.COLUMN_SERVICE_NAME+", "+ Service.COLUMN_PRICE + ") like '%" + search + "%';");
	}
	
	public void getAllSerivceWorkers(){
		Database.getInstance().query(new String[]{ServiceWorkerView.KEY},
				"select * from " + Worker.TABLE);
	}
	
	public void getServiceWorkerWithID(int id){
		Database.getInstance().query(new String[]{ServiceWorkerView.KEY},
				"select * from " + Worker.TABLE +
				" where " + Worker.COLUMN_WORKER_ID + " = " + id + ";");
	}
	
	public void getServiceWorkerWithName(String name){
		Database.getInstance().query(new String[]{ServiceWorkerView.KEY},
				"select * from " + Worker.TABLE +
				" where " + Worker.COLUMN_NAME + " like '" + name + "%';");
	}
	
	//manager access -> called when action requires manager password
	public boolean managerAccess(String managerPassword){
		boolean accessGranted = false;
		
		/*if(Query.getInstance().userQuery().size() > 0){
			accessGranted = true;
		}*/
		ResultSet rs = Database.getInstance().query(new String[]{}, "select * from " + User.TABLE + " where "+ User.COLUMN_PASSWORD + " = '"+ managerPassword + "' and " + User.COLUMN_USER_LEVEL + " = " + User.MANAGER_LEVEL + ";");
		try {
			rs.last();
			if(rs.getRow() > 0)
				accessGranted = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return accessGranted;
	}
	
	//return
	public void returnItem(String itemCode, int quantity, BigDecimal price){
		String updateReserved = "update " + Item.TABLE + 
				" set " + Item.COLUMN_STOCK + " = " + Item.COLUMN_STOCK + " + ?" + 
				" where " + Item.COLUMN_ITEM_CODE + " = ?";
		
		String item_log = "insert into " + ItemLog.TABLE + " ("+ItemLog.COLUMN_ITEM_CODE+
				", "+ItemLog.COLUMN_TYPE+
				", "+ItemLog.COLUMN_TRANSACTION_ID+
				", "+ItemLog.COLUMN_QUANTITY_SOLD+
				", "+ItemLog.COLUMN_ORIGINAL_SOLD+
				", "+ItemLog.COLUMN_PRICE_SOLD+") values (?, ?, ?, ?, ?, ?)";
		
		try {
			PreparedStatement ps = Database.getInstance().getConnection().prepareStatement(updateReserved);
			ps.setInt(1, quantity);
			ps.setString(2, itemCode);
			
			PreparedStatement log = Database.getInstance().getConnection().prepareStatement(item_log);
			log = Database.getInstance().getConnection().prepareStatement(item_log);
			log.setString(1, itemCode);
			log.setString(2, "returned");
			log.setInt(3, -1);
			log.setInt(4, quantity);
			log.setBigDecimal(5, price.multiply(BigDecimal.valueOf(quantity)));
			log.setBigDecimal(6, price.multiply(BigDecimal.valueOf(quantity)));
			
			Database.getInstance().executeUpdate(log);
			Database.getInstance().executeUpdate(ps);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//override price
	public void overridePrice(String itemCode, BigDecimal newPrice){
		for(CartItem item : activeCart.getCartItems()){
			if(item.getItemCode().equals(itemCode)){
				item.setPriceSold(newPrice);
				break;
			}
		}
		
		Database.getInstance().updateViews(new String[]{CartView.KEY, CashierView.KEY});
	}
	
	//end of day
	public boolean endOfDay(BigDecimal total_amount){
		if(total_amount.doubleValue() <= 0)
			return false;
		
		String daily_income = "insert into " + DailyIncome.TABLE + " ("+ DailyIncome.COLUMN_USER_ID +
				", "+DailyIncome.COLUMN_DATE+
				", "+DailyIncome.COLUMN_TOTAL_AMOUNT+") values (?, ?, ?)";
		
		try {
			PreparedStatement di = Database.getInstance().getConnection().prepareStatement(daily_income);
			di.setInt(1, cashier.getID());
			di.setDate(2, new java.sql.Date(new java.util.Date().getTime()));
			di.setBigDecimal(3, total_amount);
			Database.getInstance().executeUpdate(di);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}
	
	//<-- transaction functions -->
	//add to cart item for TYPE ITEM
	public boolean addToCart(CartItemType type, String code, String name, BigDecimal price, int quantity){
		boolean found = false;
		
		for(CartItem item : activeCart.getCartItems()){
			if(item.getType() == type
					&& item.getItemCode().equals(code)){
				item.setQuantity(item.getQuantity() + quantity);
				found = true;
			}
		}
		if(!found)
			activeCart.getCartItems().add(new CartItem(type, code, name, price, quantity));
		
		//updates all the views needed
		Database.getInstance().notifyViews(new String[]{CartView.KEY, CashierView.KEY});
		
		return true;
	}
	
	//add to cart item for TYPE SERVICE
	public boolean addToCart(CartItemType type, int serviceId, int workerId, String name, BigDecimal price, int quantity){
		activeCart.getCartItems().add(new CartItem(type, serviceId, workerId, name, price, quantity));
		
		//updates all the views needed
		Database.getInstance().notifyViews(new String[]{InventoryView.KEY, CartView.KEY, CashierView.KEY});
		
		return true;
	}
	
	public void clearCart(){
		activeCart.getCartItems().clear();
		
		//updates all the views needed
		Database.getInstance().notifyViews(new String[]{CartView.KEY, CashierView.KEY});
	}
	
	//remove cart item for TYPE ITEM
	public void removeCartItem(String itemCode, int quantity){
		CartItem ci = null;
		
		for(CartItem item : activeCart.getCartItems()){
			if(item.getType() == CartItemType.ITEM
					&& item.getItemCode().equals(itemCode))
				ci = item;
		}
		
		if(ci.getQuantity() - quantity <= 0)
			activeCart.getCartItems().remove(ci);
		else
			ci.setQuantity(ci.getQuantity() - quantity);
		
		//updates all the views needed
		Database.getInstance().notifyViews(new String[]{CartView.KEY, CashierView.KEY});
	}
	
	//remove cart item for TYPE SERVJCE
	public void removeCartItem(int itemCode, int quantity){
			CartItem ci = null;
			
			for(CartItem item : activeCart.getCartItems()){
				if(item.getType() == CartItemType.SERVICE
						&& item.getServiceId() == itemCode)
					ci = item;
			}
			//it's a service
			activeCart.getCartItems().remove(ci);
			//updates all the views needed
			Database.getInstance().notifyViews(new String[]{CartView.KEY, CashierView.KEY});
	}
	
	//hold/unhold cart methods - anj
	public boolean holdCart(String owner, String transactionType) {
		if(activeCart.getCartItems().isEmpty())
			return false;
		
		activeCart.setName(owner);
		activeCart.setTransactionType(transactionType);
		
		cartBuffer.add(activeCart);
		activeCart = new Cart("", new ArrayList<CartItem>(), "");
		
		Database.getInstance().updateViews(new String[]{CartView.KEY, HoldView.KEY, CashierView.KEY});
		return true;
	}
	
	public ArrayList<Cart> getCartBuffer() {
		return cartBuffer;
	}
	
	public void restoreCart(int index) {
		activeCart = cartBuffer.get(index);
		//set cart to either whole sale or retail sale (see CartPane commented out function) - anj
		//CartPane.setWoR(cartBuffer.get(index).getTransactionType();
		cartBuffer.remove(index);
		
		Database.getInstance().updateViews(new String[]{CartView.KEY, HoldView.KEY, CashierView.KEY});
	}
	
	public void removeHeldCart(int index){
		cartBuffer.remove(index);
		Database.getInstance().updateViews(new String[]{HoldView.KEY});
	}
	
	public boolean buyItems(String transactionType, boolean isloan, Customer customer){	
		String item_log = "insert into " + ItemLog.TABLE + " ("+ItemLog.COLUMN_ITEM_CODE+
															", "+ItemLog.COLUMN_TYPE+
															", "+ItemLog.COLUMN_TRANSACTION_ID+
															", "+ItemLog.COLUMN_QUANTITY_SOLD+
															", "+ItemLog.COLUMN_ORIGINAL_SOLD+
															", "+ItemLog.COLUMN_PRICE_SOLD+") values (?, ?, ?, ?, ?, ?)";
		
		String service_log = "insert into " + ServiceLog.TABLE + " ("+ServiceLog.COLUMN_SERVICE_ID+
																", "+ServiceLog.COLUMN_WORKER_ID+
																", "+ServiceLog.COLUMN_TRANSACTION_ID+") values (?, ?, ?)";
		
		String updateReserved = "update " + Item.TABLE + 
								" set "+ Item.COLUMN_STOCK + " = " + Item.COLUMN_STOCK + " - ?"+
								" where " + Item.COLUMN_ITEM_CODE + " = ?";
		
		String transaction = "insert into " + Transaction.TABLE + " (" + Transaction.COLUMN_TRANSACTION_ID+ 
																	", " + Transaction.COLUMN_USER_ID + 
																	", " + Transaction.COLUMN_TRANSACTION_TYPE + 
																	", " + Transaction.COLUMN_IS_LOAN + 
																	", " + Transaction.COLUMN_DATE_SOLD + 
																	", " + Transaction.COLUMN_TOTAL_PRICE + ") values (?, ?, ?, ?, ?, ?)";
		
		//gets total price of the item
		BigDecimal totalPrice = BigDecimal.valueOf(0);
		for(CartItem item : activeCart.getCartItems()){
			//calculates for total price of whole cart
			totalPrice = totalPrice.add(item.getPriceSold().multiply(BigDecimal.valueOf(item.getQuantity())));
		}

		//checks if debt + total price of transaction is less than debt limit
		if(isloan){
			//update customer debt
			String customerUpdate = "update " + Customer.TABLE + 
					" set " + Customer.COLUMN_DEBT + " = " + Customer.COLUMN_DEBT + " + ?"+
					", " + Customer.COLUMN_TOTAL_VISITS + " = " + Customer.COLUMN_TOTAL_VISITS + " + 1"+
					" where " + Customer.COLUMN_ACCOUNT_ID + " = ? and " + 
						Customer.COLUMN_DEBT + " + ? <= " + Customer.COLUMN_DEBT_LIMIT + ";";
			
			try {
				PreparedStatement cus = Database.getInstance().getConnection().prepareStatement(customerUpdate);
				cus.setBigDecimal(1, totalPrice);
				cus.setInt(2, customer.getAccount_id());
				cus.setBigDecimal(3, totalPrice);
				
				if(Database.getInstance().executeUpdate(cus) == 0){
					return false;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		Calendar cal = Calendar.getInstance();
		Date today = new Date(cal.getTime().getTime());
		
		String transactionCount = "select * from " + Transaction.TABLE;
		ResultSet rs = Database.getInstance().query(new String[]{}, transactionCount);
		
		int transactionId = 0;
		
		try{
			rs.last();
			transactionId = rs.getRow();
			rs.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		for(CartItem ci : activeCart.getCartItems()){
			try {
				PreparedStatement log = null;
				if(ci.getType() == CartItemType.ITEM){
					//item_log
					log = Database.getInstance().getConnection().prepareStatement(item_log);
					log.setString(1, ci.getItemCode());
					log.setString(2, "sold");
					log.setInt(3, transactionId);
					log.setInt(4, ci.getQuantity());
					log.setBigDecimal(5, ci.getOriginalPrice().multiply(BigDecimal.valueOf(ci.getQuantity())));
					log.setBigDecimal(6, ci.getPriceSold().multiply(BigDecimal.valueOf(ci.getQuantity())));
				}else{
					//service log
					log = Database.getInstance().getConnection().prepareStatement(service_log);
					log.setInt(1, ci.getServiceId()); //service id
					log.setInt(2, ci.getWorkerId());//service worker
					log.setInt(3, transactionId);		
				}
				//update item reserved
				PreparedStatement reserved = Database.getInstance().getConnection().prepareStatement(updateReserved); 
				reserved.setInt(1, ci.getQuantity());
				reserved.setString(2, ci.getItemCode());
					
				Database.getInstance().executeUpdate(log);
				Database.getInstance().executeUpdate(reserved);
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		//transaction
		try {
			PreparedStatement ps = Database.getInstance().getConnection().prepareStatement(transaction);
			ps.setInt(1, transactionId);
			ps.setInt(2, cashier.getID());
			ps.setString(3, transactionType);
			ps.setBoolean(4, isloan);
			ps.setDate(5, today);
			ps.setBigDecimal(6, totalPrice);
			Database.getInstance().executeUpdate(ps);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		activeCart.getCartItems().clear();
		Database.getInstance().updateViews(new String[]{CashierView.KEY, CartView.KEY});
		return true;
	}
	
}

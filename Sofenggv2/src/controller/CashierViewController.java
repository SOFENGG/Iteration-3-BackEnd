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
import model.Customer;
import model.Database;
import model.Item;
import model.ItemLog;
import model.Service;
import model.ServiceLog;
import model.Transaction;
import model.User;
import util.CommonQuery;
import util.Query;
import view.CartView;
import view.CashierView;
import view.InventoryView;

public class CashierViewController {

	private MainController mc;
	private CashierView cv;
	
	private User cashier;
	private Customer customer;
	
	private ArrayList<CartItem> cartItems;
	private ArrayList<Cart> cartBuffer;
	
	public CashierViewController (MainController mc) {
		this.mc = mc;
		cv = new CashierView (this);
		
		cartItems = new ArrayList<CartItem>();
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
	
	public Customer getCustomer(){
		return customer;
	}
	
	public ArrayList<CartItem> getCartItems(){
		return cartItems;
	}
	
	//cashier view services
	
	//items
	public void getAllItems() {
		Database.getInstance().query(new String[] {InventoryView.KEY},
				"select " + Item.COLUMN_ITEM_CODE + ", "
							+ "(" + Item.COLUMN_STOCK + " - " + Item.COLUMN_RESERVED + ") as stock, " 
							+ Item.COLUMN_NAME + ", "
							+ Item.COLUMN_DESCRIPTION + ", "
							+ Item.COLUMN_CATEGORY + ", "
							+ Item.COLUMN_MANUFACTURER + ", "
							+ Item.COLUMN_PRICE_CUSTOMER + " from " + Item.TABLE);
	}
	
	public void searchItem(String search) {
		Database.getInstance().query(new String[] {InventoryView.KEY},
				"select " + Item.COLUMN_ITEM_CODE + ", "
						+ "(" + Item.COLUMN_STOCK + " - " + Item.COLUMN_RESERVED + ") as stock, " 
						+ Item.COLUMN_NAME + ", "
						+ Item.COLUMN_DESCRIPTION + ", "
						+ Item.COLUMN_CATEGORY + ", "
						+ Item.COLUMN_MANUFACTURER + ", "
						+ Item.COLUMN_PRICE_CUSTOMER + " from "+Item.TABLE+" where concat("+Item.COLUMN_NAME+", "+Item.COLUMN_DESCRIPTION+", "+Item.COLUMN_CATEGORY+", "+Item.COLUMN_MANUFACTURER+") like '%" + search + "%';");
	}
	
	public void searchItemByCode(String code){
		Database.getInstance().query(new String[] {InventoryView.KEY},
				"select " + Item.COLUMN_ITEM_CODE + ", "
						+ "(" + Item.COLUMN_STOCK + " - " + Item.COLUMN_RESERVED + ") as stock, " 
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
	
	//manager access -> called when action requires manager password
	public boolean managerAccess(String managerPassword){
		boolean accessGranted = false;
		
		/*if(Query.getInstance().userQuery("select * from " + User.TABLE + " where "+ User.COLUMN_PASSWORD + " = '"+ managerPassword + "' and " + User.COLUMN_USER_LEVEL + " = " + User.MANAGER_LEVEL + ";").size() > 0){
			accessGranted = true;
		}*/
		
		return accessGranted;
	}
	
	public void setCustomer(int accountId){
		//customer = CommonQuery.getCustomerWithId(accountId);
	}
	
	public void removeCustomer(){
		customer = null;
	}
	
	//return
	public void returnItem(String itemCode, int quantity){
		String updateReserved = "update " + Item.TABLE + 
				" set " + Item.COLUMN_RESERVED + " = " + Item.COLUMN_RESERVED + " + ?" + 
				" where " + Item.COLUMN_ITEM_CODE + " = '?'";

		try {
			PreparedStatement ps = Database.getInstance().getConnection().prepareStatement(updateReserved);
			ps.setInt(1, quantity);
			ps.setString(2, itemCode);
			Database.getInstance().executeUpdate(ps);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//override price
	public void overridePrice(String itemCode, BigDecimal newPrice){
		for(CartItem item : cartItems){
			if(item.getItemCode().equals(itemCode)){
				item.setPriceSold(newPrice);
				break;
			}
		}
		
		Database.getInstance().updateViews(new String[]{CartView.KEY, CashierView.KEY});
	}
	
	//insert servicelog
	public void service(int workerId, int serviceId){
		//add service id and worker id to service log
		String service_log = "insert into " + ServiceLog.TABLE + " ("+ServiceLog.COLUMN_SERVICE_ID+
																", "+ServiceLog.COLUMN_WORKER_ID+
																", "+ServiceLog.COLUMN_DATE+") values (?, ?, ?)";
		
		Calendar cal = Calendar.getInstance();
		Date today = new Date(cal.getTime().getTime());
		
		try {
			PreparedStatement ps = Database.getInstance().getConnection().prepareStatement(service_log);
			ps.setInt(1, serviceId);
			ps.setInt(2, workerId);
			ps.setDate(3, today);
			Database.getInstance().executeUpdate(ps);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//<-- transaction functions -->
	
	public boolean addToCart(String itemCode, String name, BigDecimal price, int quantity){
		String updateReserved = "update " + Item.TABLE + 
						" set " + Item.COLUMN_RESERVED + " =  " + Item.COLUMN_RESERVED + " + ? " + 
						" where " + Item.COLUMN_ITEM_CODE + " = ? and " + Item.COLUMN_STOCK + " - " + Item.COLUMN_RESERVED + "  >= ? ;";
		
		boolean found = false;
		try {
			PreparedStatement ps = Database.getInstance().getConnection().prepareStatement(updateReserved);
			ps.setInt(1, quantity);
			ps.setString(2, itemCode);
			ps.setInt(3, quantity);
			if(Database.getInstance().executeUpdate(ps) == 0)
				return false; //not enough stock
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		for(CartItem item : cartItems){
			if(item.getItemCode().equals(itemCode)){
				item.setQuantity(item.getQuantity() + quantity);
				found = true;
			}
		}
		if(!found)
			cartItems.add(new CartItem(itemCode, name, price, quantity));
		
		//updates all the views needed
		Database.getInstance().notifyViews(new String[]{CartView.KEY, CashierView.KEY});
		
		return true;
	}
	
	public void clearCart(){
		String updateReserved = "update " + Item.TABLE + 
								" set " + Item.COLUMN_RESERVED + " = " + Item.COLUMN_RESERVED + " - ?" + 
								" where " + Item.COLUMN_ITEM_CODE + " = ?";
		
		for(CartItem ci : cartItems){
			try {
				PreparedStatement ps = Database.getInstance().getConnection().prepareStatement(updateReserved);
				ps.setInt(1, ci.getQuantity());
				ps.setString(2, ci.getItemCode());
				Database.getInstance().executeUpdate(ps);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		cartItems.clear();
		
		//updates all the views needed
		Database.getInstance().notifyViews(new String[]{CartView.KEY});
	}
	
	public void removeCartItem(String itemCode, int quantity){
		String updateReserved = "update " + Item.TABLE + 
								" set " + Item.COLUMN_RESERVED + " = " + Item.COLUMN_RESERVED + " - ?" + 
								" where " + Item.COLUMN_ITEM_CODE + " = ?";
		
		CartItem ci = null;
		
		for(CartItem item : cartItems){
			if(item.getItemCode().equals(itemCode))
				ci = item;
		}
		
		try {
			PreparedStatement ps = Database.getInstance().getConnection().prepareStatement(updateReserved);
			ps.setInt(1, quantity);
			ps.setString(2, ci.getItemCode());
			Database.getInstance().executeUpdate(ps);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(ci.getQuantity() - quantity <= 0)
			cartItems.remove(ci);
		else
			ci.setQuantity(ci.getQuantity() - quantity);
		
		//updates all the views needed
		Database.getInstance().notifyViews(new String[]{CartView.KEY});
	}
	
	//hold/unhold cart methods - anj
	public void holdCart(String transactionType) {
		cartBuffer.add(new Cart(cartItems, transactionType));
		cartItems.clear();
	}
	
	public ArrayList<Cart> getCartBuffer() {
		return cartBuffer;
	}
	
	public void restoreCart(int index) {
		cartItems.clear();
		cartItems = cartBuffer.get(index).getCartItems();
		//set cart to either whole sale or retail sale (see CartPane commented out function) - anj
		//CartPane.setWoR(cartBuffer.get(index).getTransactionType();
		cartBuffer.remove(index);
	}
	
	public void buyItems(String transactionType, boolean isloan){	
		String item_log = "insert into " + ItemLog.TABLE + " ("+ItemLog.COLUMN_ITEM_CODE+
															", "+ItemLog.COLUMN_TRANSACTION_ID+
															", "+ItemLog.COLUMN_QUANTITY_SOLD+
															", "+ItemLog.COLUMN_ORIGINAL_SOLD+
															", "+ItemLog.COLUMN_PRICE_SOLD+") values (?, ?, ?, ?, ?)";
		
		String updateReserved = "update " + Item.TABLE + 
								" set " + Item.COLUMN_RESERVED + " = " + Item.COLUMN_RESERVED + " - ?"+
								", " + Item.COLUMN_STOCK + " = " + Item.COLUMN_STOCK + " - ?"+
								" where " + Item.COLUMN_ITEM_CODE + " = ?";
		
		String transaction = "insert into " + Transaction.TABLE + " (" + Transaction.COLUMN_TRANSACTION_ID+ 
																	", " + Transaction.COLUMN_USER_ID + 
																	", " + Transaction.COLUMN_TRANSACTION_TYPE + 
																	", " + Transaction.COLUMN_IS_LOAN + 
																	", " + Transaction.COLUMN_DATE_SOLD + 
																	", " + Transaction.COLUMN_TOTAL_PRICE + ") values (?, ?, ?, ?, ?, ?)";
		
		Calendar cal = Calendar.getInstance();
		Date today = new Date(cal.getTime().getTime());
		BigDecimal totalPrice = BigDecimal.valueOf(0);
		
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
		
		for(CartItem ci : cartItems){
			try {
				
				//item_log
				PreparedStatement log = Database.getInstance().getConnection().prepareStatement(item_log);
				log.setString(1, ci.getItemCode());
				log.setInt(2, transactionId);
				log.setInt(3, ci.getQuantity());
				log.setBigDecimal(4, ci.getOriginalPrice().multiply(BigDecimal.valueOf(ci.getQuantity())));
				log.setBigDecimal(5, ci.getPriceSold().multiply(BigDecimal.valueOf(ci.getQuantity())));
				
				//update item reserved
				PreparedStatement reserved = Database.getInstance().getConnection().prepareStatement(updateReserved); 
				reserved.setInt(1, ci.getQuantity());
				reserved.setInt(2, ci.getQuantity());
				reserved.setString(3, ci.getItemCode());
				
				//calculates for total price of whole cart
				totalPrice = totalPrice.add(ci.getPriceSold().multiply(BigDecimal.valueOf(ci.getQuantity())));
					
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
		
		if(isloan){
			//insert into customer debt
		}
		
		cartItems.clear();
		Database.getInstance().updateViews(new String[]{CashierView.KEY, CartView.KEY});
	}
	
}

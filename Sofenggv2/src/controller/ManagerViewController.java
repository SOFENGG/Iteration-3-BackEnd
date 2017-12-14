package controller;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Customer;
import model.Database;
import model.Item;
import model.Service;
import model.ServiceLog;
import model.Supplier;
import model.Transaction;
import model.User;
import model.Worker;
import util.Query;
import view.cashier.CashierView;
import view.cashier.CustomerView;
import view.cashier.ServiceView;
import view.cashier.ServiceWorkerView;
import view.manager.ui.ManagerView;

public class ManagerViewController {

	private MainController mc;
	private ManagerView mv;
	
	private User user;
	
	private String filter = "";
	
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
	
	public void setUser(User user){
		this.user = user;
		System.out.println("Welcome Manager " + user.getName());
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
					" where " + Customer.COLUMN_NAME + " like '" + search + "%';";
			Database.getInstance().query(keys,
					sql);
		}

		public void searchCustomerAddress(String[] keys, String search){
			String sql = "";
			sql = "select * from " + Customer.TABLE +
					" where " + Customer.COLUMN_ADDRESS + " like '%" + search + "%';";
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
					"select * from "+Service.TABLE+" where concat("+Service.COLUMN_SERVICE_NAME+", "+ Service.COLUMN_PRICE + ") like '%" + search + "%';");
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
					" where " + Worker.COLUMN_NAME + " like '" + name + "%';");
		}
		
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
				" order by ASC " + Transaction.COLUMN_TRANSACTION_ID + ";");
	}
	
	public void searchCurrentTransactionsByNumber(String[] keys, int transactionID){
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Database.getInstance().query(keys,
				"select * from " + Supplier.TABLE +
				" where " + Transaction.COLUMN_DATE_SOLD + " like '" + sdf.format(now) + "' and " + Transaction.COLUMN_TRANSACTION_ID + " like '" + transactionID + "'" +
				" order by ASC " + Transaction.COLUMN_TRANSACTION_ID + ";");
	}
	
	//todo filters w/ jesin
	public void setFilter() {
		
	}
	
	public void getFilteredTransactions(String[] keys){
		Database.getInstance().query(keys, 
				"select * from " + Transaction.TABLE);
	}
	
	public void searchFilteredTransactionsByNumber(String[] keys, int transactionID){
		Database.getInstance().query(keys,
				"select * from " + Transaction.TABLE +
				" where " + Transaction.COLUMN_TRANSACTION_ID + " like '" + transactionID + "';");
	}
	
	//
}

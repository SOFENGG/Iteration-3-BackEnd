package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Database;
import model.User;
import util.Query;
import view.cashier.AlertBoxPopup;
import view.cashier.InventoryView;
import view.cashier.LoginView;

public class LoginController {
	private LoginView lv;
	private MainController mc;
	
	public LoginController (MainController mc) {
		this.mc = mc;
		lv = new LoginView(this);
	}
	
	public Pane getView (int view) {
		switch(view) {
			case Code.LOGIN_VIEW: return lv;
		}
		return null;
	}
	
	public Stage getMainStage () {
		return mc.getStage ();
	}
	
	public MainController getMainController() {
		return mc;
	}
	
	public void changeControl (int controllerCode, int view) {
		mc.setScene (controllerCode, view);
	}
	
	public void attach(){
		/*
		 * attach all login related views here
		 */
		Database.getInstance().attach(LoginView.KEY, lv);
	}
	
	public void detach(){
		/*
		 * detach all login related views here
		 */
		Database.getInstance().detach(LoginView.KEY);
	}
	
	//login view services
	
	public void logIn(String user, String pass){
		ResultSet rs = Database.getInstance().query(new String[] {LoginView.KEY},
				"select * from "+User.TABLE+" where "+User.COLUMN_USERNAME+" = '"+user+"' and "+User.COLUMN_PASSWORD+" = '"+pass+"';");
		
		User loginUser = null;
		
		try {
			if(rs.next())
				 loginUser = new User(rs.getInt(User.COLUMN_USER_ID), rs.getString(User.COLUMN_NAME), rs.getString(User.COLUMN_USERNAME), rs.getString(User.COLUMN_PASSWORD), rs.getInt(User.COLUMN_USER_LEVEL));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(loginUser != null) {
			if(loginUser.getAccessLevel() == 1){
				//cashier view
				mc.passUser(Code.CVC_CODE, loginUser);
				changeControl (Code.CVC_CODE, Code.CASHER_VIEW);
			}else{
				//manager view
				mc.passUser(Code.MVC_CODE, loginUser);
				changeControl (Code.MVC_CODE, Code.MANAGER_VIEW);
			}
			System.out.println("LOGGED IN");
		}else {
			new AlertBoxPopup("Authentication Error", "Wrong Password/Username");
		}
	}

}

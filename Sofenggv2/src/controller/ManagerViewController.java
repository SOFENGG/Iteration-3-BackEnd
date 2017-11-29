package controller;

import java.sql.ResultSet;
import java.util.ArrayList;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Database;
import model.Item;
import model.User;
import util.Query;
import view.CashierView;
import view.ManagerView;

public class ManagerViewController {

	private MainController mc;
	private ManagerView mv;
	
	private User user;
	
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
		mv.attach();
	}
	
	public void detach(){
		mv.detach();
	}
	
	public void setUser(User user){
		this.user = user;
		System.out.println("Welcome Manager " + user.getName());
	}
	//manager view services
	
	//no filter/search
	public ResultSet allItems(){
		return Database.getInstance().query(new String[] {}, "select * from items;");
	}
	
	//search
	public ResultSet searchItems(String search){
		return Database.getInstance().query(new String[] {}, "select * from items where concat(name, description, category, manufacturer) like '%" + search + "%';");
	}
	
}

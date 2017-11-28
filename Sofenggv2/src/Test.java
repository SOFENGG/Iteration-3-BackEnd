import java.util.Scanner;

import controller.CashierViewController;
import model.Database;

public class Test {
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		CashierViewController cvc = new CashierViewController(null);
		
		Database.getInstance().connect();
		
		String password = "xd";
		while(!cvc.managerAccess(password)){
			System.out.println("access denied: try again!");
			password = sc.nextLine();
		}
		
		Database.getInstance().close();
	}
}

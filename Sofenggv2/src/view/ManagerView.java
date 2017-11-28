package view;

import controller.CashierViewController;
import controller.ManagerViewController;
import javafx.scene.layout.BorderPane;

public class ManagerView extends BorderPane implements View{

	private ManagerViewController mvc;
	
	public ManagerView (ManagerViewController mvc) {
		this.mvc = mvc;
	}
	
	public void attach(){
		//do all attaching of views here
		
	}
	
	public void detach(){
		//do all detaching of views here
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
}

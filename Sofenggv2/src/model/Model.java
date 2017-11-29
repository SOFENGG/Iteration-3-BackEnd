package model;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import view.View;

public abstract class Model {
	
	protected Map <String, View> views = new HashMap <String, View> ();
	
	public void attach (String key, View v) {
		views.put(key,  v);
	}
	
	public void detach (String key) {
		views.remove(key);
	}
	
	public void notifyViews (String[] keys) {
		for (String key : keys)
			views.get(key).update();
	}
	
}
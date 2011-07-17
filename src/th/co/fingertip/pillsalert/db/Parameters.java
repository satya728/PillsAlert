package th.co.fingertip.pillsalert.db;

import java.util.HashMap;
import java.util.Iterator;

import th.co.fingertip.pillsalert.PillsAlertEnum;
import android.content.ContentValues;

public class Parameters {
	HashMap<String, Object> elements;
	static int SELECT = 0;
	public int mode;
	
	public Parameters(int mode){
		super();
		this.mode = mode;
		elements = new HashMap<String, Object>();
	}
	public Parameters(HashMap<String, Object> params, int mode) {
		super();
		this.elements = params;
		this.mode = mode;
	}
	public void put(String key, Object object){
		elements.put(key, object);
	}
	
	public Object get(String key){
		return elements.get(key);
	}
	
	public ContentValues getContentValues(){
		ContentValues content_values = new ContentValues();
		String[] schema = null;
		switch(mode){
			case PillsAlertEnum.Model.PILL:
				schema = DatabaseConfiguration.PILL_SCHEMA_KEYS;
				break;
			case PillsAlertEnum.Model.NOTIFICATION:
				schema = DatabaseConfiguration.NOTIFICATION_SCHEMA_KEYS;
				break;
			case PillsAlertEnum.Model.PERIOD:
				schema = DatabaseConfiguration.PERIOD_SCHEMA_KEYS;
				break;
		}

		//may use generic
		String text = "";
		Iterator iterator = elements.keySet().iterator();
		while(iterator.hasNext()){
			String column_name = (String) iterator.next();
			Object tmp_object = elements.get(column_name);
			if( tmp_object != null){
				if(tmp_object.getClass() == String.class){
					content_values.put(
						column_name, 
						(String)tmp_object
					);
				}
				else if(tmp_object.getClass() == Integer.class){
					Integer value = (Integer)elements.get(column_name);
					content_values.put(
						column_name, 
						(Integer)value
					);
				}
			}
			text = text+"["+column_name+":"+tmp_object.toString()+"]";
		}

		System.out.print(text);
		return content_values;
	}
}

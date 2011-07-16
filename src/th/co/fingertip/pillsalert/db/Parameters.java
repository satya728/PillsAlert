package th.co.fingertip.pillsalert.db;

import java.util.HashMap;
import java.util.Map;

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
		ContentValues content_values = null;
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
		for(int i = 0 ; i < elements.size() ; i++){
			if(elements.get(schema[i]).getClass() == String.class){
				String value = (String)elements.get(schema[i]);
				content_values.put(
					schema[i], 
					value
				);
			}
			else if(elements.get(schema[i]).getClass() == Integer.class){
				Integer value = (Integer)elements.get(schema[i]);
				content_values.put(
					schema[i], 
					value
				);
			}
		}
		return content_values;
	}
}

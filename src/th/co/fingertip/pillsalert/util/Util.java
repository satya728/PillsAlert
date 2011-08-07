package th.co.fingertip.pillsalert.util;

import android.content.Context;
import android.widget.Toast;

public class Util {
	public static final int LONG_TRACE  = Toast.LENGTH_LONG;
	public static final int SHORT_TRACE = Toast.LENGTH_SHORT;
	public static void put(Context context,String text,int duration){
		Toast.makeText(context, text, duration).show();
	}
	public static boolean integer_to_boolean(Integer i){
		if(i.intValue() == 1){
			return true;
		}
		return false;
	}
	public static boolean int_to_boolean(int i){
		if(i == 1){
			return true;
		}
		return false;
	}
}

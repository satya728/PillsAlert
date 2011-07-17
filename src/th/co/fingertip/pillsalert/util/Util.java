package th.co.fingertip.pillsalert.util;

import android.content.Context;
import android.widget.Toast;

public class Util {
	public static final int LONG_TRACE  = Toast.LENGTH_LONG;
	public static final int SHORT_TRACE = Toast.LENGTH_SHORT;
	public static void put(Context context,String text,int duration){
		Toast.makeText(context, text, duration).show();
	}
}

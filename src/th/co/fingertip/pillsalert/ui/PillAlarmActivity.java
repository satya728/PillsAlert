package th.co.fingertip.pillsalert.ui;

import java.util.Calendar;

import th.co.fingertip.pillsalert.R;
import th.co.fingertip.pillsalert.TimeAlarm;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class PillAlarmActivity extends Activity {

	AlarmManager alarm_manager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_test);
		alarm_manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
		setAlarm(10000);
	}
	
	private void setAlarm (int delay)  {
		Intent intent = new Intent(this, TimeAlarm.class);
		intent.putExtra("ALARM", "Hello from PillAlarmActivity");
		PendingIntent pending_intent = PendingIntent.getBroadcast(getBaseContext(), 0, intent, 
										PendingIntent.FLAG_UPDATE_CURRENT);
		alarm_manager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), delay, pending_intent);
		
	}
	
	
}

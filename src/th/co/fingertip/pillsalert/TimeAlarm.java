package th.co.fingertip.pillsalert;

import th.co.fingertip.pillsalert.util.Util;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class TimeAlarm extends BroadcastReceiver {

	NotificationManager notification_manager;
	@Override
	public void onReceive(Context context, Intent intent) {
		try {
			Bundle b = intent.getExtras();
			String message = b.getString("ALARM");
			Util.put(context, message, Util.SHORT_TRACE);
			notification_manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
			Notification notification = new Notification(R.drawable.icon, "test", System.currentTimeMillis());
			PendingIntent content_intent = PendingIntent.getActivity(context, 0, new Intent(), 0);
			notification.setLatestEventInfo(context, "My notification", "Hello World!", content_intent);
			notification_manager.notify(1, notification);
		} catch (Exception e) {
			Util.put(context, "ERROR is found! e: " + e.toString(), Util.SHORT_TRACE);
		}
	}

}

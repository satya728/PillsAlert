package th.co.fingertip.pillsalert;

import java.util.Timer;
import java.util.TimerTask;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class TimeService extends IntentService {
	private Timer timer;
	private long delay;
	
	public TimeService(int sec) {
		super(null);
		this.delay = sec;
	}

	public class RemindTask extends TimerTask {
		
		public void run() {
			timer.cancel();
			stopSelf();
			
		}
		
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		
		return null;
	}

	@Override
	public void onCreate() {
		
		super.onCreate();
		Toast.makeText(this, "Service is created ...", Toast.LENGTH_SHORT).show();
		
		
		startTimer();
	}

	private void startTimer() {
		timer = new Timer();
		timer.schedule(new RemindTask(), delay);
	}

	@Override
	public void onDestroy() {
		
		super.onDestroy();
		Toast.makeText(this, "Service is destroyed ...", Toast.LENGTH_SHORT).show();
		
	}

	@Override
	protected void onHandleIntent(Intent arg0) {
		// TODO Auto-generated method stub
		
	}

}

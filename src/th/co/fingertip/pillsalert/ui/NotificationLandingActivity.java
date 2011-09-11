package th.co.fingertip.pillsalert.ui;

import th.co.fingertip.pillsalert.PillsAlertEnum;
import th.co.fingertip.pillsalert.R;
import th.co.fingertip.pillsalert.db.Notification;
import th.co.fingertip.pillsalert.db.Period;
import th.co.fingertip.pillsalert.db.Pill;
import th.co.fingertip.pillsalert.factory.ImageFactory;
import android.app.Activity;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class NotificationLandingActivity extends Activity{//

	private Pill[] pills;
	private int period_id;
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ListView list_view = new ListView(this);
		setContentView(list_view);
		
		Notification.init(this);
		Pill.init(this);
		Period.init(this);

		period_id = Period.find(Period.ALL)[0].id;//savedInstanceState.getInt("period_id");
		Pill[] pills = Notification.findPillByPeriod(period_id);
		CustomAdapter c = new CustomAdapter(this, R.layout.notification_landing_row, R.id.notification_landing_pill_title, pills);
		list_view.setAdapter(c);
	}
	
	private class CustomAdapter extends ArrayAdapter<Pill>{

		public CustomAdapter(Context context, int resource, int textViewResourceId, Pill[] objects) {
			super(context, resource, textViewResourceId, objects);

		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View row = convertView;
			if(row == null){
				row = getLayoutInflater().inflate(R.layout.notification_landing_row, parent, false);
			}
			
			Pill pill = getItem(position);
			ImageView pill_image = (ImageView)row.findViewById(R.id.notification_landing_pill_image);
			TextView pill_title = (TextView)row.findViewById(R.id.notification_landing_pill_title);
			
			pill_title.setText(pill.title);
			if (pill.image.equals(PillsAlertEnum.FileName.PILL_DUMMY_FILENAME)) {
				pill_image.setImageResource(R.drawable.dummy_pill);
			} else {
				pill_image.setImageBitmap(ImageFactory.get_bitmap(pill.image));
			}
			
			return row;
		}
		
		
	}

}

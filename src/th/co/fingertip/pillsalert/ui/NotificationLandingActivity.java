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
import android.database.Cursor;
import android.os.Bundle;
import android.text.format.Time;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class NotificationLandingActivity extends Activity{//extends ListActivity

	private Pill[] pills;
	private int period_id;
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notification_landing);
		Notification.init(this);
		Pill.init(this);
		Period.init(this);
		//registerForContextMenu(getListView());
		
//		Period[] periods = Period.find(Period.ALL);
//		period_id = periods[0].id;
		
		period_id = savedInstanceState.getInt("period_id");
		
		
		
		pills = Notification.findPillByPeriod(period_id);
		
		LinearLayout ll = new LinearLayout(this);
		
		for(int i = 0 ; i < pills.length ; i++){
			LinearLayout row = new LinearLayout(this);
			//row.setLayoutParams(new La)
			LinearLayout.LayoutParams l_params = 
				new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.MATCH_PARENT,
						LinearLayout.LayoutParams.WRAP_CONTENT
				);
			
			//row.setLayoutParams(l_params);			
			TextView tv = new TextView(this);
			tv.setText(pills[i].title);
			ImageView iv = new ImageView(this);
			
			if (pills[i].image.equals(PillsAlertEnum.FileName.PILL_DUMMY_FILENAME)) {
				iv.setImageResource(R.drawable.dummy_pill);
			} else {
				iv.setImageBitmap(ImageFactory.get_bitmap(pills[i].image));
			}
			
			row.addView(iv);
			row.addView(tv);
			ll.addView(row);
		}
		
		setContentView(ll);
		
		//fill_data();
		
		
	}


//	private void fill_data() {
//		//periods = Period.find(Period.ALL);
//		Cursor period_cursor = Period.find_cursor(Period.ALL);
//		period_cursor.moveToFirst();
//		startManagingCursor(period_cursor);
//		String[] from_column = {Period.TITLE};
//		int[] to_view = {R.id.notification_landing_row_item};
//		SimpleCursorAdapter period_cursor_adapter = new SimpleCursorAdapter(
//			this, 
//			R.layout.period_setting_row, 
//			period_cursor, 
//			from_column, 
//			to_view
//		);
//		setListAdapter(period_cursor_adapter);
//	}
	
}

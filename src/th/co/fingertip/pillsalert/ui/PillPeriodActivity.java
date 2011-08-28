package th.co.fingertip.pillsalert.ui;

import java.util.Iterator;
import java.util.Vector;

import th.co.fingertip.pillsalert.PillsAlertEnum;
import th.co.fingertip.pillsalert.R;
import th.co.fingertip.pillsalert.TimeService;
import th.co.fingertip.pillsalert.adapter.ImageSpinnerAdapter;
import th.co.fingertip.pillsalert.db.DatabaseConfiguration;
import th.co.fingertip.pillsalert.db.Notification;
import th.co.fingertip.pillsalert.db.NotificationDatabaseAdapter;
import th.co.fingertip.pillsalert.db.Parameters;
import th.co.fingertip.pillsalert.db.Period;
import th.co.fingertip.pillsalert.db.PeriodDatabaseAdapter;
import th.co.fingertip.pillsalert.db.Pill;
import th.co.fingertip.pillsalert.db.PillDatabaseAdapter;
import th.co.fingertip.pillsalert.dragndrop.DragDropGallery;
import th.co.fingertip.pillsalert.dragndrop.DragLayer;
import th.co.fingertip.pillsalert.util.Util;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class PillPeriodActivity extends Activity implements OnClickListener {

	
	private DragDropGallery pill_gallery;
	private DragDropGallery period_gallery;
	private DragLayer drag_layer;

	private TextView period_title;
	
	private Button previous_button;
	private Button next_button;
	
	private ImageSpinnerAdapter pill_spinner;
	private ImageSpinnerAdapter period_spinner;

	private int period_index;
	
	private Pill[] pills;
	private Period[] periods;
	private Notification[] notifications;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pill_period);
		
		pill_gallery = (DragDropGallery)findViewById(R.id.pill_gallery);
		period_gallery = (DragDropGallery)findViewById(R.id.period_gallery);
		
		drag_layer = (DragLayer)findViewById(R.id.drag_layer);
		
		period_title = (TextView)findViewById(R.id.period_title);
		
		previous_button = (Button)findViewById(R.id.previous_period_button);
		next_button = (Button)findViewById(R.id.next_period_button);
		
		Pill.init(this);
		Period.init(this);
		Notification.init(this);
		
		pills = Pill.find(Pill.ALL);
		periods = Period.find(Period.ALL);

		//set period index = 0 as we begin using the first period 
		period_index = 0;
		
		//set period title
		period_title.setText(periods[0].title);
		
		//get notification cursor for first period entry
		
		notifications = Notification.find("period_id = ?", new String[]{""+periods[0].id});
		

		pill_spinner = new ImageSpinnerAdapter(
						this,false,pills,PillsAlertEnum.Model.PILL);
		period_spinner = new ImageSpinnerAdapter(
						this,true,notifications,period_id,PillsAlertEnum.Model.NOTIFICATION);
		
		pill_gallery.setAdapter(pill_spinner);
		period_gallery.setAdapter(period_spinner);
		
		pill_gallery.setDragger(drag_layer);
		period_gallery.setDragger(drag_layer);
		
		previous_button.setOnClickListener(this);
		next_button.setOnClickListener(this);	
	}

	
	private void updateNotification(ImageSpinnerAdapter adapter){
		//notification_database.sqlite_db_instance.execSQL("delete from notifications where _id > 0 ");
		//Cursor existing_notification_cursor = notification_database.selectRowWhere("period_id = " + adapter.period_id);
		//Vector<Long> existing_notification_vector = Util.flatten_id(existing_notification_cursor);
		
//		Iterator<Long> new_iterator = adapter.ids.iterator();
//		Iterator<Long> existing_iterator = existing_notification_vector.iterator();
//		//delete existing notification
//		while(existing_iterator.hasNext()){
//			Long old_id = (Long)existing_iterator.next();
//			if(!adapter.ids.contains(old_id)){
//				//delete
//				notification_database.deleteRow(old_id);
//			}
//		}
//		//add new notification
//		int i = 0;
//		while(new_iterator.hasNext()){
//			Long id = (Long)new_iterator.next();
//			if(!existing_notification_vector.contains(id)){
//				Parameters p = new Parameters(PillsAlertEnum.Model.NOTIFICATION);
//				p.put(DatabaseConfiguration.NOTIFICATION_SCHEMA_KEYS[1], id);
//				p.put(DatabaseConfiguration.NOTIFICATION_SCHEMA_KEYS[2], adapter.period_id);
//				p.put(DatabaseConfiguration.NOTIFICATION_SCHEMA_KEYS[3], adapter.images.get(i));
//				notification_database.insertRow(p);
//				i = i + 1;
//			}
//			
//		}

	}
	
	@Override
	public void onBackPressed() {
		//move to first
//		if (period_cursor.getCount()!=0) {
//			period_cursor.move(-1);
//			for (;period_cursor.moveToNext();) {
//				String st = period_cursor.getString(period_cursor.getColumnIndex(
//						DatabaseConfiguration.PERIOD_SCHEMA_KEYS[2]));
//				Util.put(this, "start service" + st , Util.SHORT_TRACE);
//				
//				startService(new Intent(this, TimeService.class));
//			}
//		}

		super.onBackPressed();
	}

	@Override
	public void onClick(View view) {
		
		int resourceIsClicked = view.getId();
		int model = PillsAlertEnum.Model.NOTIFICATION;
		
		switch (resourceIsClicked) {
		
		case R.id.previous_period_button:
			updateNotification(period_spinner);
			try{
				period_index = period_index - 1;
				Period current_period = periods[period_index];
				period_title.setText(current_period.title);
				notifications = Notification.find("period_id=?", new String[]{current_period.id+""});
				//pill_spinner.updateItem(notification_cursor, model);
			}
			catch(ArrayIndexOutOfBoundsException e){
				Util.put(getApplicationContext(), "no previous period", Util.SHORT_TRACE);
			}
			break;
		
		case R.id.next_period_button:
			updateNotification(period_spinner);
			try{
				period_index = period_index + 1;
				Period current_period = periods[period_index];
				period_title.setText(current_period.title);
				notifications = Notification.find("period_id=?", new String[]{current_period.id+""});
				//pill_spinner.updateItem(notification_cursor, model);
			}
			catch(ArrayIndexOutOfBoundsException e){
				Util.put(getApplicationContext(), "no previous period", Util.SHORT_TRACE);
			}
			break;
		}
	}	
}
	
	

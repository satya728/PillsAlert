package th.co.fingertip.pillsalert.ui;

import java.util.Vector;

import th.co.fingertip.pillsalert.PillsAlertEnum;
import th.co.fingertip.pillsalert.R;
import th.co.fingertip.pillsalert.adapter.ImageSpinnerAdapter;
import th.co.fingertip.pillsalert.adapter.NotificationImageSpinnerAdapter;
import th.co.fingertip.pillsalert.adapter.PillImageSpinnerAdapter;
import th.co.fingertip.pillsalert.db.Notification;
import th.co.fingertip.pillsalert.db.Period;
import th.co.fingertip.pillsalert.db.Pill;
import th.co.fingertip.pillsalert.dragndrop.DragLayer;
import th.co.fingertip.pillsalert.dragndrop.SourceDragDropGallery;
import th.co.fingertip.pillsalert.dragndrop.TargetDragDropGallery;
import th.co.fingertip.pillsalert.util.Util;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class PillPeriodActivity extends Activity implements OnClickListener, OnItemClickListener, OnItemLongClickListener {

	
	private SourceDragDropGallery pill_gallery;
	private TargetDragDropGallery period_gallery;
	private DragLayer drag_layer;

	private TextView period_title;
	
	private Button previous_button;
	private Button next_button;
	
	private PillImageSpinnerAdapter pill_spinner;
	private NotificationImageSpinnerAdapter period_spinner;

	private int period_index;
	
	private Pill[] pills;
	private Vector<Pill> pills_in_period;
	private Period[] periods;
	private Notification[] notifications;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pill_period);
		
		pill_gallery = (SourceDragDropGallery)findViewById(R.id.pill_gallery);
		period_gallery = (TargetDragDropGallery)findViewById(R.id.period_gallery);
		
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
		
		//find pill in particular periods
		pills_in_period = new Vector<Pill>();
		for (Notification n : notifications) {
			Pill p = Pill.find(n.pill_id);
			pills_in_period.add(p);
		}
		
		pill_spinner = new PillImageSpinnerAdapter(getBaseContext(), pills);
		period_spinner = new NotificationImageSpinnerAdapter (getBaseContext(), pills_in_period);
		
		pill_gallery.setAdapter(pill_spinner);
		period_gallery.setAdapter(period_spinner);
		
		pill_gallery.setDragger(drag_layer);
		
		previous_button.setOnClickListener(this);
		next_button.setOnClickListener(this);
		
		pill_gallery.setOnItemClickListener(this);
		period_gallery.setOnItemClickListener(this);
		
		period_gallery.setOnItemLongClickListener(this);
	}

	
	private void updateNotification(NotificationImageSpinnerAdapter adapter){
		Period current_period = periods[period_index];
		Notification[] current_period_notification = Notification.find("period_id = ?", new String[]{current_period.id+""});
		
		for(int i=0; i<current_period_notification.length; i++){
			int uid =current_period_notification[i].id;
			Notification.delete(current_period_notification[i].id);
		}
		
		for(int j=0; j<adapter.getCount(); j++){
			Pill current_pill = Pill.find(adapter.pill_ids.get(j));
			Notification new_notification = new Notification( current_pill.id, current_period.id, current_pill.image);
			new_notification.save();
		}
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
		
		Pill[] local_pills;
		switch (resourceIsClicked) {
		
			case R.id.previous_period_button:
				updateNotification(period_spinner);
				try{
					period_index = period_index - 1;
					Period current_period = periods[period_index];
					period_title.setText(current_period.title);
					local_pills = Notification.findPillByPeriod(current_period.id);
					period_spinner.updateItem(local_pills);
				}
				catch(ArrayIndexOutOfBoundsException e){
					Util.put(getApplicationContext(), "no previous period", Util.SHORT_TRACE);
					period_index = 0;
				}
				break;
			
			case R.id.next_period_button:
				updateNotification(period_spinner);
				try{
					period_index = period_index + 1;
					Period current_period = periods[period_index];
					period_title.setText(current_period.title);
					local_pills = Notification.findPillByPeriod(current_period.id);
					period_spinner.updateItem(local_pills);
				}
				catch(ArrayIndexOutOfBoundsException e){
					Util.put(getApplicationContext(), "no next period", Util.SHORT_TRACE);
					period_index = periods.length - 1;
				}
				break;
		}
	}

	
	@Override
	public void onItemClick(AdapterView<?> adapter_view, View view, int position, long id) {
		int pill_id = (Integer)view.getTag(R.id.image_id);
		Bundle pill_bundle = new Bundle();
		Pill pill = Pill.find(pill_id);
		pill_bundle.putInt(Pill.ID, pill.id);
		pill_bundle.putString(Pill.TITLE, pill.title);
		pill_bundle.putString(Pill.NOTE, pill.note);
		pill_bundle.putString(Pill.IMAGE, pill.image);
		pill_bundle.putInt("request_code",  PillsAlertEnum.Request.PILL_READ);
		Intent view_pill_intent = new Intent(getApplicationContext(), PillEditorActivity.class);
		view_pill_intent.putExtras(pill_bundle);
		startActivityForResult(view_pill_intent, PillsAlertEnum.Request.PILL_READ);
	}


	@Override
	public boolean onItemLongClick(AdapterView<?> adapter_view, View view, int position, long id) {
		int pill_id = (Integer)view.getTag(R.id.image_id);
		Pill.delete(pill_id);
		return true;
	}	
}
	
	

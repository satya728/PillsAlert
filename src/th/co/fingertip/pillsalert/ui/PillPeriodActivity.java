package th.co.fingertip.pillsalert.ui;

import java.util.Vector;

import th.co.fingertip.pillsalert.PillsAlertEnum;
import th.co.fingertip.pillsalert.R;
import th.co.fingertip.pillsalert.adapter.ImageSpinnerAdapter;
import th.co.fingertip.pillsalert.db.DatabaseConfiguration;
import th.co.fingertip.pillsalert.db.NotificationDatabaseAdapter;
import th.co.fingertip.pillsalert.db.NotificationDatabaseConnector;
import th.co.fingertip.pillsalert.db.PeriodDatabaseAdapter;
import th.co.fingertip.pillsalert.db.PillDatabaseAdapter;
import th.co.fingertip.pillsalert.dragndrop.DragDropGallery;
import th.co.fingertip.pillsalert.dragndrop.DragLayer;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.sax.TextElementListener;
import android.widget.TextView;

public class PillPeriodActivity extends Activity {

	
	private DragDropGallery pill_gallery;
	private DragDropGallery period_gallery;
	private DragLayer drag_layer;
	
	private PillDatabaseAdapter pill_database;
	private PeriodDatabaseAdapter period_database;
	private NotificationDatabaseAdapter notification_database;
	
	private Cursor pill_cursor;
	private Cursor period_cursor;
	private Cursor notification_cursor;
	
	private TextView period_title;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pill_period);
		
		drag_layer = (DragLayer)findViewById(R.id.drag_layer);
		
		pill_gallery = (DragDropGallery)findViewById(R.id.pill_gallery);
		period_gallery = (DragDropGallery)findViewById(R.id.period_gallery);
		
		period_title = (TextView)findViewById(R.id.period_title);
		
		
		pill_database = new PillDatabaseAdapter(this);
		period_database = new PeriodDatabaseAdapter(this);
		notification_database = new NotificationDatabaseAdapter(this);
		
		pill_database.connect();
		period_database.connect();
		notification_database.connect();
		
		pill_cursor = pill_database.selectRow(null);
		period_cursor = period_database.selectRow(null);
		
		period_cursor.moveToFirst();
		//set period title
		period_title.setText(
			period_cursor.getString(
				period_cursor.getColumnIndex(
					DatabaseConfiguration.PERIOD_SCHEMA_KEYS[1]
				)
			)
		);
		
		//get notification cursor for first period entry
		notification_cursor = notification_database.selectRowWhere(
			"period_id = " + 
			period_cursor.getInt(
				period_cursor.getColumnIndex(
					DatabaseConfiguration.PERIOD_SCHEMA_KEYS[0]
				)	
			)
		);
		
		pill_gallery.setAdapter(new ImageSpinnerAdapter(this,false,pill_cursor,PillsAlertEnum.Model.PILL));
		period_gallery.setAdapter(new ImageSpinnerAdapter(this,true,notification_cursor,PillsAlertEnum.Model.NOTIFICATION));
		
		
		pill_gallery.setDragger(drag_layer);
		period_gallery.setDragger(drag_layer);
		
//		pill_gallery.setOnDropEnd(new DragContrller(){
//			@Override
//			public void onDragEnd(){
//				
//			}
//		});
		
		
	}
	
	private void update_notification(Long period_id, Vector<Long> pill_ids){
		
	}
}
	
	

package th.co.fingertip.pillsalert.ui;

import th.co.fingertip.pillsalert.PillsAlertEnum;
import th.co.fingertip.pillsalert.R;
import th.co.fingertip.pillsalert.adapter.ImageAdapter;
import th.co.fingertip.pillsalert.db.DatabaseConfiguration;
import th.co.fingertip.pillsalert.db.Parameters;
import th.co.fingertip.pillsalert.db.PillDatabaseAdapter;
import th.co.fingertip.pillsalert.factory.ImageFactory;
import th.co.fingertip.pillsalert.util.Util;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends Activity {
	private String[] images = {"test.PNG"};
	private ImageAdapter image_adapter;

	PillDatabaseAdapter pill_database;
	Cursor pill_cursor;
	SimpleCursorAdapter pill_cursor_adapter;
	GridView gridview;
	
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		pill_database.close();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		pill_database = new PillDatabaseAdapter(this);
		pill_database.connect();
		
		gridview = (GridView)findViewById(R.id.main_ui_gridview);
		
		gridview.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
				Bundle pill_data = new Bundle();
				pill_cursor.moveToPosition(position);
				pill_data.putLong(DatabaseConfiguration.PILL_SCHEMA_KEYS[0], id);
				pill_data.putString(
					DatabaseConfiguration.PILL_SCHEMA_KEYS[1], 
					pill_cursor.getString(pill_cursor.getColumnIndex(DatabaseConfiguration.PILL_SCHEMA_KEYS[1]))
				);
				pill_data.putString(
					DatabaseConfiguration.PILL_SCHEMA_KEYS[2], 
					pill_cursor.getString(pill_cursor.getColumnIndex(DatabaseConfiguration.PILL_SCHEMA_KEYS[2]))
				);
				String image_filename = pill_cursor.getString(pill_cursor.getColumnIndex(DatabaseConfiguration.PILL_SCHEMA_KEYS[3]));
				pill_data.putString(
					DatabaseConfiguration.PILL_SCHEMA_KEYS[3], 
					image_filename
				);
				
				Intent edit_note_intent = new Intent(getApplicationContext(), PillEditorActivity.class);
				edit_note_intent.putExtras(pill_data);
				startActivityForResult(edit_note_intent, PillsAlertEnum.Request.PERIOD_UPDATE);  
			}
			
		});
		fill_data();
		registerForContextMenu(gridview);
	}
	
	private void fill_data(){
		pill_cursor = pill_database.selectRow(null);
		if(pill_cursor.getCount() != 0){
			image_adapter = new ImageAdapter(this, pill_cursor); //images);
			gridview.setAdapter(image_adapter);
		}
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_context_menu, menu);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_option_menu, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		super.onMenuItemSelected(featureId, item);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch(item.getItemId()){
			case R.id.main_add_pill:
				Intent create_intent = new Intent(this, PillEditorActivity.class);
				startActivityForResult(create_intent, PillsAlertEnum.Request.PERIOD_CREATE);
				break;
			case R.id.main_set_period:
				Intent period_setting_intent = new Intent(this, PeriodSettingActivity.class);
				startActivity(period_setting_intent);
				break;
			case R.id.main_assign_period:
				Intent assign_period_intent = new Intent(this, PillPeriodActivity.class);
				startActivity(assign_period_intent);
				break;
		}
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		Bundle bundle_data = data.getExtras();
		Parameters new_pill_data = new Parameters(PillsAlertEnum.Model.PILL);
		String pill_image = null;
		
		new_pill_data.put(
			DatabaseConfiguration.PILL_SCHEMA_KEYS[1],
			bundle_data.getString(DatabaseConfiguration.PILL_SCHEMA_KEYS[1])
		);
		new_pill_data.put(
			DatabaseConfiguration.PILL_SCHEMA_KEYS[2],
			bundle_data.getString(DatabaseConfiguration.PILL_SCHEMA_KEYS[2])
		);
		pill_image =bundle_data.getString(DatabaseConfiguration.PILL_SCHEMA_KEYS[3]);
		new_pill_data.put(
			DatabaseConfiguration.PILL_SCHEMA_KEYS[3],
			pill_image
		);
		Bitmap pill_image_bitmap = bundle_data.getParcelable("image_bitmap");
		Util.put(this, "result code = " + resultCode, Util.LONG_TRACE);
		switch(resultCode){
			case PillsAlertEnum.Result.PILL_CREATE:
				Long new_pill_id = pill_database.insertRow(new_pill_data);
				
				if((new_pill_id != -1) && (pill_image_bitmap!=null) ){
					Util.put(this, "sefefsdfsd", Util.SHORT_TRACE);
					ImageFactory.save_bitmap(pill_image_bitmap, new_pill_id+".PNG");
					Parameters new_pill_image = new Parameters(PillsAlertEnum.Model.PILL);
					new_pill_image.put(DatabaseConfiguration.PILL_SCHEMA_KEYS[3], new_pill_id+".PNG");
					pill_database.updateRow(new_pill_image,new_pill_id);
				}
				
				break;
			case PillsAlertEnum.Result.PILL_UPDATE:
				Long row_id = bundle_data.getLong(DatabaseConfiguration.PILL_SCHEMA_KEYS[0]);
				boolean is_updated = pill_database.updateRow(new_pill_data, row_id);
				
				if(is_updated && (pill_image_bitmap!=null)){
					ImageFactory.save_bitmap(pill_image_bitmap, row_id+".PNG");
				}
				
				break;
			default:
				break;
		}
		fill_data();
		
	}
	
	
}

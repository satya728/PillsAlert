package th.co.fingertip.pillsalert.ui;

import java.security.KeyStore.LoadStoreParameter;

import th.co.fingertip.pillsalert.PillsAlertEnum;
import th.co.fingertip.pillsalert.R;
import th.co.fingertip.pillsalert.adapter.PillImageAdapter;
import th.co.fingertip.pillsalert.db.DatabaseConfiguration;
import th.co.fingertip.pillsalert.db.Notification;
import th.co.fingertip.pillsalert.db.Parameters;
import th.co.fingertip.pillsalert.db.Period;
import th.co.fingertip.pillsalert.db.Pill;
import th.co.fingertip.pillsalert.db.PillDatabaseAdapter;
import th.co.fingertip.pillsalert.factory.ImageFactory;
import th.co.fingertip.pillsalert.util.Util;
import android.app.Activity;
import android.content.Context;
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
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends Activity {
	
	private PillImageAdapter image_adapter;
	private SimpleCursorAdapter pill_cursor_adapter;
	private GridView gridview;
	private Pill[] pills;
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Pill.init(this);
		gridview = (GridView)findViewById(R.id.main_ui_gridview);
		
		fill_data();
		registerForContextMenu(gridview);
		
		gridview.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
				Bundle pill_data = new Bundle();
				pill_data.putInt(  Pill.ID,    pills[position].id);
				pill_data.putString(Pill.TITLE, pills[position].title);
				pill_data.putString(Pill.NOTE,  pills[position].note);
				pill_data.putString(Pill.IMAGE, pills[position].image);
				Intent edit_pill_intent = new Intent(getApplicationContext(), PillEditorActivity.class);
				edit_pill_intent.putExtras(pill_data);
				startActivityForResult(edit_pill_intent, PillsAlertEnum.Request.PERIOD_UPDATE);
			}
		});
	}

	private void fill_data(){
		pills = Pill.find(Pill.ALL); 
		if(pills.length != 0){
			PillImageAdapter adapter = new PillImageAdapter(getApplicationContext(), pills);
			gridview.setAdapter(adapter);
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
				//startActivity(create_intent);
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
	public boolean onContextItemSelected(MenuItem item) {
		super.onContextItemSelected(item);
		switch(item.getItemId()){
			case R.id.main_context_menu_delete_pill:
				AdapterContextMenuInfo context_menu_info = (AdapterContextMenuInfo)item.getMenuInfo();
				int id = (int) context_menu_info.id;
				
				Notification[] notification = Notification.find("pill_id = ?", new String[]{id+""});
				if(notification.length != 0){
					for(int i=0;i<notification.length;i++){
						Notification.delete(notification[i].id);
					}
				}
				Pill to_be_deleted_pill = Pill.find(id);
				Pill.delete(id);
				fill_data();
				break;
		}
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(data == null){
			return;
		}
		Bundle pill_data = data.getExtras();
		Pill local_pill = null;
		
		String pill_image = null;
		Bitmap pill_image_bitmap = pill_data.getParcelable("image_bitmap");
		
		switch(resultCode){
			case PillsAlertEnum.Result.PILL_CREATE:
				local_pill = new Pill(
					pill_data.getString(Pill.TITLE),
					pill_data.getString(Pill.NOTE),
					pill_data.getString(Pill.IMAGE)
				);
				local_pill.save();
				if(pill_image_bitmap!=null){
					local_pill.save();
					local_pill.image = local_pill.id+".PNG";
					local_pill.save();
					ImageFactory.save_bitmap(pill_image_bitmap, local_pill.image);
				}
				break;
			case PillsAlertEnum.Result.PILL_UPDATE:
				local_pill = Pill.find(pill_data.getInt(Pill.ID));
				local_pill.title = pill_data.getString(Pill.TITLE);
				local_pill.note = pill_data.getString(Pill.NOTE);
				local_pill.image = pill_data.getString(Pill.IMAGE);
				local_pill.save();
				if(pill_image_bitmap!=null){
					ImageFactory.save_bitmap(pill_image_bitmap, local_pill.image);
				}
				break;
			case RESULT_CANCELED:
				break;
			default:
				break;
		}
		fill_data();
	}
}

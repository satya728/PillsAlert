package th.co.fingertip.pillsalert.ui;

import th.co.fingertip.pillsalert.PillsAlertEnum;
import th.co.fingertip.pillsalert.R;
import th.co.fingertip.pillsalert.db.DatabaseConfiguration;
import th.co.fingertip.pillsalert.db.Parameters;
import th.co.fingertip.pillsalert.db.Period;
import th.co.fingertip.pillsalert.db.PeriodDatabaseAdapter;
import th.co.fingertip.pillsalert.util.Util;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.SimpleCursorAdapter;


public class PeriodSettingActivity extends ListActivity{

	private Period[] periods;
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.period_setting);
		registerForContextMenu(getListView());
		
		Period.init(this);
		fill_data();
	}


	private void fill_data() {
		periods = Period.find(Period.ALL);
		Cursor period_cursor = Period.find_cursor(Period.ALL);
		period_cursor.moveToFirst();
		startManagingCursor(period_cursor);
		String[] from_column = {Period.TITLE};
		int[] to_view = {R.id.period_setting_row_item};
		SimpleCursorAdapter period_cursor_adapter = new SimpleCursorAdapter(
			this, 
			R.layout.period_setting_row, 
			period_cursor, 
			from_column, 
			to_view
		);
		setListAdapter(period_cursor_adapter);
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(data != null){
			Bundle period_data = data.getExtras();
			int aa =period_data.getInt(Period.ID);
			Period local_period = null;
			switch(resultCode){
				case PillsAlertEnum.Result.PERIOD_CREATE:
					//long aa = period_database.insertRow(period_data_parameters);
					local_period = new Period(
						period_data.getString(Period.TITLE),
						period_data.getString(Period.TIME)
					);
					break;
				case PillsAlertEnum.Result.PERIOD_UPDATE:
					local_period = Period.find(period_data.getInt(Period.ID));
					local_period.title = period_data.getString(Period.TITLE);
					local_period.time = period_data.getString(Period.TIME);
					break;
				default:
					break;
			}
			if(local_period != null){
				local_period.save();
			}
			
			fill_data();
		}
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.period_setting_context_menu, menu);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.period_setting_option_menu, menu);
		return true;
	}


	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		super.onMenuItemSelected(featureId, item);
		switch(item.getItemId()){
			case R.id.period_setting_option_menu_create_period:
				Intent create_intent = new Intent(this, PeriodEditorActivity.class);
				startActivityForResult(create_intent, PillsAlertEnum.Request.PERIOD_CREATE);
				break;
		}
		return true;
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		super.onContextItemSelected(item);
		switch(item.getItemId()){
			case R.id.period_setting_context_menu_delete_period:
				AdapterContextMenuInfo context_menu_info = (AdapterContextMenuInfo)item.getMenuInfo();
				Period.delete((int) context_menu_info.id);
				fill_data();
				break;
		}
		return true;
	}


	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Period period = periods[position];
		Intent intent = new Intent(this, PeriodEditorActivity.class);
		Bundle period_data = new Bundle();
		period_data.putInt(Period.ID, (int)id);
		period_data.putString(Period.TITLE,period.title);
		period_data.putString(Period.TIME,period.time);
		intent.putExtras(period_data);
		startActivityForResult(intent, PillsAlertEnum.Request.PERIOD_UPDATE);
	}
	
	
}

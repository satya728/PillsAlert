package th.co.fingertip.pillsalert.ui;

import th.co.fingertip.pillsalert.PillsAlertEnum;
import th.co.fingertip.pillsalert.R;
import th.co.fingertip.pillsalert.db.DatabaseConfiguration;
import th.co.fingertip.pillsalert.db.Parameters;
import th.co.fingertip.pillsalert.db.PeriodDatabaseAdapter;
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

	PeriodDatabaseAdapter period_database;
	Cursor period_cursor;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.period_setting);
		
		period_database = new PeriodDatabaseAdapter(this);
		period_database.connect();

		fill_data();
	}


	private void fill_data() {
		period_cursor = period_database.selectRow(null);
		period_cursor.moveToFirst();
		startManagingCursor(period_cursor);
		String[] from_column = {DatabaseConfiguration.PERIOD_SCHEMA_KEYS[1]};
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
			Parameters period_data_parameters = new Parameters(PillsAlertEnum.Model.PERIOD);
			if(period_data != null){
				period_data_parameters.put(
					DatabaseConfiguration.PERIOD_SCHEMA_KEYS[1],
					period_data.getString(DatabaseConfiguration.PERIOD_SCHEMA_KEYS[1])
				);
				period_data_parameters.put(
					DatabaseConfiguration.PERIOD_SCHEMA_KEYS[2],
					period_data.getString(DatabaseConfiguration.PERIOD_SCHEMA_KEYS[2])
				);
			}
	
			switch(resultCode){
				case PillsAlertEnum.Result.PERIOD_CREATE:
					long aa = period_database.insertRow(period_data_parameters);
					System.out.println(aa+"");
					break;
				case PillsAlertEnum.Result.PERIOD_UPDATE:
					Long r_id = period_data.getLong(DatabaseConfiguration.PERIOD_SCHEMA_KEYS[0]);
					period_database.updateRow(period_data_parameters, r_id);
					break;
				default:
					break;
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
				Intent create_intent = new Intent(this,PeriodEditorActivity.class);
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
				Long row_id = context_menu_info.id;
				period_database.deleteRow(row_id);
				Toast.makeText(this,"row deleted" , Toast.LENGTH_SHORT).show();
				fill_data();
				break;
		}
		return true;
	}


	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		period_cursor.moveToPosition(position);
		
		Intent intent = new Intent(this, PeriodEditorActivity.class);
		
		Bundle note_data = new Bundle();
		//pass id
		note_data.putLong(DatabaseConfiguration.PERIOD_SCHEMA_KEYS[0], id);
		
		//pass title
		note_data.putString(
			DatabaseConfiguration.PERIOD_SCHEMA_KEYS[1], 
			period_cursor.getString(period_cursor.getColumnIndex(DatabaseConfiguration.PERIOD_SCHEMA_KEYS[1]))
		);
		//pass time
		note_data.putString(
			DatabaseConfiguration.PERIOD_SCHEMA_KEYS[2], 
			period_cursor.getString(period_cursor.getColumnIndex(DatabaseConfiguration.PERIOD_SCHEMA_KEYS[2]))
		);
		intent.putExtras(note_data);
		startActivityForResult(intent, PillsAlertEnum.Request.PERIOD_UPDATE);
	}
	
	
}

package th.co.fingertip.pillsalert.ui;

import th.co.fingertip.pillsalert.R;
import th.co.fingertip.pillsalert.db.PeriodDatabaseAdapter;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
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
		String[] from_column = {};
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
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
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
	
	
}

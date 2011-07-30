package th.co.fingertip.pillsalert.ui;

import th.co.fingertip.pillsalert.PillsAlertEnum;
import th.co.fingertip.pillsalert.R;
import th.co.fingertip.pillsalert.adapter.ImageAdapter;
import th.co.fingertip.pillsalert.db.PillDatabaseAdapter;
import th.co.fingertip.pillsalert.factory.ImageFactory;
import th.co.fingertip.pillsalert.util.Util;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
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
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		pill_database = new PillDatabaseAdapter(this);
		pill_database.connect();
		
		gridview = (GridView)findViewById(R.id.main_ui_gridview);
		
		gridview.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
				
				
			}
			
		});
		registerForContextMenu(gridview);
	}
	
	private void fill_data(){
		pill_cursor = pill_database.selectRow(null);
		
		image_adapter = new ImageAdapter(this, pill_cursor); //images);
		gridview.setAdapter(image_adapter);
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
		}
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch(resultCode){
			case PillsAlertEnum.Result.PILL_CREATE:
				Util.put(this, "create returned", Util.SHORT_TRACE);
				
				break;
		}
		
	}
	
	
}

package th.co.fingertip.pillsalert.ui;

import th.co.fingertip.pillsalert.PillsAlertEnum;
import th.co.fingertip.pillsalert.R;
import th.co.fingertip.pillsalert.db.DatabaseConfiguration;
import th.co.fingertip.pillsalert.db.Period;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

public class PeriodEditorActivity extends Activity {

	EditText period_title;
	TimePicker period_time;
	Button save_button;
	Integer row_id;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.period_setting_editor);
		
		period_title = (EditText) findViewById(R.id.period_editor_title);
		period_time  = (TimePicker) findViewById(R.id.period_editor_time_pick);
		save_button  = (Button) findViewById(R.id.period_editor_save_button);
		
		Bundle period_data = getIntent().getExtras();
		if(period_data != null){
			row_id = period_data.getInt(Period.ID);
			period_title.setText(period_data.getString(Period.TITLE));
			String[] period_time_text = period_data.getString(Period.TIME).split("-");
			period_time.setCurrentHour(Integer.parseInt(period_time_text[0]));
			period_time.setCurrentMinute(Integer.parseInt(period_time_text[1]));
		}
		
		save_button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Bundle new_period_data = new Bundle();
				new_period_data.putString(
					Period.TITLE, 
					period_title.getText().toString()
				);
				new_period_data.putString(
					Period.TIME,
					period_time.getCurrentHour().toString()+"-"+period_time.getCurrentMinute().toString()
				);
				if(row_id==null){
					Intent create_intent = new Intent();
					create_intent.putExtras(new_period_data);
					setResult(PillsAlertEnum.Result.PERIOD_CREATE, create_intent);
				}
				else{
					new_period_data.putInt(
						Period.ID, 
						row_id
					);
					Intent update_intent = new Intent();
					update_intent.putExtras(new_period_data);
					setResult(PillsAlertEnum.Result.PERIOD_UPDATE, update_intent);
				}
				finish();
			}
		});
	}
}

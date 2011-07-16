package th.co.fingertip.pillsalert.ui;

import th.co.fingertip.pillsalert.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PillEditorActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pill_editor);
		
		Button save_pill = (Button) findViewById(R.id.save_pill);
		save_pill.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
			 
				
			}
		});
	}
	
}

package th.co.fingertip.pillsalert;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.Toast;

public class Main extends Activity {
	private Button start_button;
	private Button stop_button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		start_button = (Button) findViewById(R.id.start);
		
		stop_button = (Button) findViewById(R.id.stop);
		start_button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(Main.this, "Start service!!", Toast.LENGTH_SHORT).show();
				Log.d("SERVICE", "onclick starting service");
				startService(new Intent(Main.this, TimeService.class));
			}
		});
		
		stop_button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(Main.this, "Stop service!!", Toast.LENGTH_SHORT).show();
				Log.d("SERVICE", "onclick stopping service");
				stopService(new Intent(Main.this, TimeService.class));
			}
		});
		
		
	}
	
}

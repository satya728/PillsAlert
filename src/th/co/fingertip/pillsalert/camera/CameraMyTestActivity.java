package th.co.fingertip.pillsalert.camera;

import th.co.fingertip.pillsalert.R;
import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class CameraMyTestActivity extends Activity {
	private Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		//prepare window
		getWindow().setFormat(PixelFormat.TRANSLUCENT);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		context = this;
		//set view
		setContentView(R.layout.my_camera);
	}

}

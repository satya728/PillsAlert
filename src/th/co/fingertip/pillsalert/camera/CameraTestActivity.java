package th.co.fingertip.pillsalert.camera;

import java.util.List;

import th.co.fingertip.pillsalert.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class CameraTestActivity extends Activity implements SurfaceHolder.Callback {

	Context m_context;
	String TAG = "pillsalert";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFormat(PixelFormat.TRANSLUCENT);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		m_context = this;
		setContentView(R.layout.pon_camera);
		SurfaceView sv = (SurfaceView) findViewById(R.id.surface_view);
		SurfaceHolder sv_holder = sv.getHolder();
		sv_holder.addCallback(this);
		sv_holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	Camera camera = null;
	boolean preview_is_running = false;
	
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		camera = Camera.open();
		camera.setDisplayOrientation(90);
		Log.d(TAG,"yeah!");
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		if(preview_is_running){
			camera.stopPreview();
		}
		
		Camera.Parameters p = camera.getParameters();
		//p.setPreviewSize(width, height);
//		p.set("orientation","portrait");
//		p.setRotation(90);
		
		List<Camera.Size> supported_preview_size = p.getSupportedPreviewSizes();
		Camera.Size size = supported_preview_size.get(0);
		p.setPreviewSize(size.width, size.height);
		
		try{
			camera.setParameters(p); //////\
		}
		catch(Exception e){
			Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
		}
		try{
			camera.setPreviewDisplay(holder);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		camera.startPreview();
		preview_is_running = true;
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		preview_is_running = false;
		camera.stopPreview();
		camera.release();
	}
	
	
	
	Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
		public void onPictureTaken(byte[] imageData, Camera c) {

			if (imageData != null) {

				Intent mIntent = new Intent();

//				StoreByteImage(m_context, imageData, 50,
//						"ImageName");
				camera.startPreview();

				//setResult(FOTO_MODE, mIntent);
				finish();

			}
		}
	};
	
}

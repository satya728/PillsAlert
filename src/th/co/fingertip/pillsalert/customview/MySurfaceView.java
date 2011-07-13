package th.co.fingertip.pillsalert.customview;

import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {

	Camera camera = null;
	boolean preview_is_running = false;
	Paint paint;
	SurfaceHolder holder;

	public MySurfaceView(Context context) {
		super(context);
		initialize(context);
	}
	
	

	public MySurfaceView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initialize(context);
	}



	public MySurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initialize(context);
	}



	private void initialize(Context context) {
		// TODO Auto-generated method stub
		holder = getHolder();
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		holder.addCallback(this);
		//setDrawProperties();
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		camera = Camera.open();
		camera.setDisplayOrientation(90);
		
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		
		if(preview_is_running){
			camera.stopPreview();
		}
		
		Camera.Parameters p = camera.getParameters();
		
		List<Camera.Size> supported_preview_size = p.getSupportedPreviewSizes();
		Camera.Size size = supported_preview_size.get(0);
		p.setPreviewSize(size.width, size.height);
		
		try{
			camera.setParameters(p); 
		}
		catch(Exception e){
			e.printStackTrace();
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
		// TODO Auto-generated method stub
		preview_is_running = false;
		camera.stopPreview();
		camera.release();
	}
	
//	Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
//		public void onPictureTaken(byte[] imageData, Camera c) {
//
//			if (imageData != null) {
//
//				Intent mIntent = new Intent();
//
////				StoreByteImage(m_context, imageData, 50,
////						"ImageName");
//				camera.startPreview();
//
//				//setResult(FOTO_MODE, mIntent);
//				finish();
//
//			}
//		}
//	};
	
}

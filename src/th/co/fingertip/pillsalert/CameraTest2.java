package th.co.fingertip.pillsalert;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class CameraTest2 extends Activity {
	
	  Button snap_button;
	  ImageButton image_button;
	  FileManager fm = new FileManager();
	  
	  private static final int CAM_REQUEST = 1313;
	  private static final int CAMERA_PIC_REQUEST = 1414;


	  @Override
	  public void onCreate(Bundle savedInstanceState) {
	      super.onCreate(savedInstanceState);
	      setContentView(R.layout.cameratest);
	      snap_button = (Button) findViewById(R.id.button1);
	      image_button = (ImageButton) findViewById(R.id.imageView1);
	      snap_button.setOnClickListener(new btnTakePhotoClicker());
	  }

	  @Override
	  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	      super.onActivityResult(requestCode, resultCode, data);
	      Bitmap m = null;
	      
	      if (requestCode == CAMERA_PIC_REQUEST) {
	    	  Bundle b = data.getExtras();
	    	  Uri u = data.getData();
			try{
				if(u!=null) {
					m = MediaStore.Images.Media.getBitmap(getContentResolver(), u);
				}
				else {
					Bitmap mu = (Bitmap) data.getExtras().get("data");
					m = mu;
				}
				//m = getResizedBitmap(m, 100, 100);
				m = Bitmap.createScaledBitmap(m, 120, 120, true);
			}
			catch(Exception e){
				Toast.makeText(this, "error", Toast.LENGTH_LONG).show();
			}
			    image_button.setImageBitmap(m);
			}
	        //write to sd card folder
	        if (FileManager.writeToFile(m, FileManager.buildFullPath("", "test6.PNG"))) {
	        	 Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show();
	        }
	       
//	        image_button.setImageBitmap(
//	        		BitmapFactory.decodeStream(FileManager.readFromFile("test.PNG")));
//	        
//	        Toast.makeText(this, "YOYOYOYO", Toast.LENGTH_LONG).show();
	        	        
	  }
	  
//	  public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
//		  int width = bm.getWidth();
//		  int height = bm.getHeight();
//		  float scaleWidth = ((float) newWidth) / width;
//		  float scaleHeight = ((float) newHeight) / height;
//	
//		  // create a matrix for the manipulation
//		  Matrix matrix = new Matrix();
//	
//		  // resize the bit map
//		  matrix.postScale(scaleWidth, scaleHeight);
//	
//		  // recreate the new Bitmap
//		  Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
//	
//		  return resizedBitmap;
//	  }

	  class btnTakePhotoClicker implements Button.OnClickListener
	  {
	      @Override
	      public void onClick(View v) {	          
	    	  Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
	          startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
	      }
	  }
}

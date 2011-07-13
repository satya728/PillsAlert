package th.co.fingertip.pillsalert.camera;

import java.io.File;
import java.io.IOException;

import th.co.fingertip.pillsalert.R;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.ImageButton;
import android.widget.Toast;

public class NativeCameraActivity extends Activity implements OnClickListener{
	
	private ImageButton image_button;
	private String file_name = "test_pic.jpg";
	private Uri image_uri;
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 9;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.native_camera);
		image_button = (ImageButton) findViewById(R.id.image_button);
		image_button.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.image_button:
			Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
			startCameraActivity();
		}
	}

	private void startCameraActivity() {
		ContentValues values = new ContentValues();
		values.put(MediaStore.Images.Media.TITLE, file_name);
		values.put(MediaStore.Images.Media.DESCRIPTION, "taken by camera");
		image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
		intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
		startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				String path = convertImageUriToPath(image_uri, this);
				Toast.makeText(this, "Result is OK, path:" + path, 10000).show();
				Drawable d = Drawable.createFromPath(path);
				image_button.setImageDrawable(d);
				
			} else if (resultCode == RESULT_CANCELED) {
				Toast.makeText(this, "Result is canceled", Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	public static String convertImageUriToPath (Uri imageUri, Activity activity)  {
		Cursor cursor = null;
		try {
		    String [] proj= { MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID, MediaStore.Images.ImageColumns.ORIENTATION};
		    cursor = activity.managedQuery( imageUri,
		            proj, // Which columns to return
		            null,       // WHERE clause; which rows to return (all rows)
		            null,       // WHERE clause selection arguments (none)
		            null); // Order-by clause (ascending by name)
		    int file_ColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		    int orientation_ColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.ORIENTATION);
		    if (cursor.moveToFirst()) {
		        String orientation = cursor.getString(orientation_ColumnIndex);
		        return cursor.getString(file_ColumnIndex);
		    }
		    return null;
		} finally {
		    if (cursor != null) {
		        cursor.close();
		    }
		}
	}

}

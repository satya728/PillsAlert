package th.co.fingertip.pillsalert.ui;

import th.co.fingertip.pillsalert.FileManager;
import th.co.fingertip.pillsalert.PillsAlertEnum;
import th.co.fingertip.pillsalert.R;
import th.co.fingertip.pillsalert.db.DatabaseConfiguration;
import th.co.fingertip.pillsalert.db.Pill;
import th.co.fingertip.pillsalert.factory.ImageFactory;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class PillEditorActivity extends Activity {

	Button save_pill_button;
	EditText pill_title;
	EditText pill_note;
	ImageButton pill_image_button;
	Bitmap pill_bitmap;
	Integer row_id;
	String image;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pill_editor);
		
		image = null;
		pill_bitmap = null;
		save_pill_button = (Button) findViewById(R.id.save_pill);
		pill_title = (EditText) findViewById(R.id.pill_title);
		pill_note = (EditText) findViewById(R.id.pill_note);
		pill_image_button = (ImageButton) findViewById(R.id.pill_image);
		
		Bundle pill_data = getIntent().getExtras();
		if(pill_data != null ){
			row_id = pill_data.getInt(Pill.ID);
			pill_title.setText(pill_data.getString(Pill.TITLE));
			pill_note.setText(pill_data.getString(Pill.NOTE));
			image = pill_data.getString(Pill.IMAGE);
			if(!image.equals(PillsAlertEnum.FileName.PILL_DUMMY_FILENAME)){
				pill_image_button.setImageBitmap(ImageFactory.get_bitmap(image));
			}
		}
		
		save_pill_button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle pill_data = new Bundle();
				
				pill_data.putString(Pill.TITLE, pill_title.getText().toString());
				pill_data.putString(Pill.NOTE, pill_note.getText().toString());
				
				//add new pill
				if(row_id == null){
					Intent create_pill_intent = new Intent();
					if(pill_bitmap != null){
						pill_data.putString(Pill.IMAGE,"");
						pill_data.putParcelable("image_bitmap", pill_bitmap); //pill image
					}else{
						pill_data.putString(Pill.IMAGE,PillsAlertEnum.FileName.PILL_DUMMY_FILENAME);
					}
					create_pill_intent.putExtras(pill_data);
					setResult(PillsAlertEnum.Result.PILL_CREATE,create_pill_intent);
				}
				//update pill
				else{
					Intent update_pill_intent = new Intent();
					if(image.equals(PillsAlertEnum.FileName.PILL_DUMMY_FILENAME) ){ //no image at first
						if(pill_bitmap != null){
							pill_data.putString(Pill.IMAGE,row_id+".PNG");
							pill_data.putParcelable("image_bitmap", pill_bitmap); //pill image
						}
						else{
							pill_data.putString(Pill.IMAGE,PillsAlertEnum.FileName.PILL_DUMMY_FILENAME);
							pill_data.putParcelable("image_bitmap", null);
						}
					}else{ //the image existed
						//new image
						if(pill_bitmap != null){
							pill_data.putString(Pill.IMAGE,row_id+".PNG");
							pill_data.putParcelable("image_bitmap", pill_bitmap); //pill image
						}
						//use existing image
						else{
							pill_data.putParcelable("image_bitmap", null);
						}
					}
					pill_data.putInt(Pill.ID, row_id); //pill row id
					update_pill_intent.putExtras(pill_data);
					setResult(PillsAlertEnum.Result.PILL_UPDATE, update_pill_intent);
				}
				finish();
			}
		});
		
		pill_image_button.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				Intent camera_intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		        startActivityForResult(camera_intent, PillsAlertEnum.Request.CAMERA_PIC_REQUEST);
			}
		});
	}
	
	@Override
	  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	      super.onActivityResult(requestCode, resultCode, data);
	      Bitmap m = null;
	      if (requestCode == PillsAlertEnum.Request.CAMERA_PIC_REQUEST) {
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
				m = Bitmap.createScaledBitmap(m, 120, 120, true);
			}
			catch(Exception e){
				Toast.makeText(this, "error", Toast.LENGTH_LONG).show();
			}
			    pill_image_button.setImageBitmap(m);
			    pill_bitmap = m;
	      }        
	  }
	
}

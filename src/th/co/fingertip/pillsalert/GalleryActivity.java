package th.co.fingertip.pillsalert;

import th.co.fingertip.pillsalert.adapter.ImageSpinnerAdapter;
import th.co.fingertip.pillsalert.dragndrop.DragDropGallery;
import th.co.fingertip.pillsalert.dragndrop.DragLayer;
import th.co.fingertip.pillsalert.util.Util;
import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;

public class GalleryActivity extends Activity {
	private DragLayer drag_layer1;
	//private DragLayer drag_layer2;
	private DragDropGallery gallery1;
	private DragDropGallery gallery2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gallerytest);
		drag_layer1 = (DragLayer) findViewById(R.id.drag_layer1);
		//drag_layer2 = (DragLayer) findViewById(R.id.drag_layer2);
		
		gallery1 = (DragDropGallery)findViewById(R.id.gallery1);
		gallery2 = (DragDropGallery)findViewById(R.id.gallery2);
		
		gallery1.setAdapter(new ImageSpinnerAdapter(this));
		gallery1.setDragger(drag_layer1);
		
		gallery2.setAdapter(new ImageSpinnerAdapter(this));
		gallery2.setDragger(drag_layer1);
		
	}

}

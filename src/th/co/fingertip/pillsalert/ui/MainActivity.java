package th.co.fingertip.pillsalert.ui;

import th.co.fingertip.pillsalert.FileManager;
import th.co.fingertip.pillsalert.R;
import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Toast;

public class MainActivity extends Activity {
	
//	Integer[] image_ids = {
//			R.drawable.sample_0, R.drawable.sample_1, R.drawable.sample_2,
//			R.drawable.sample_3, R.drawable.sample_4, R.drawable.sample_5,
//			R.drawable.sample_6, R.drawable.sample_7, R.drawable.sample_0,
//			R.drawable.sample_1, R.drawable.sample_2, R.drawable.sample_3,
//	};
	
	String[] images = {
			"test.PNG", "test2.PNG", "test3.PNG", "test4.PNG", "test5.PNG",
			"test6.PNG", "test.PNG", "test2.PNG", "test3.PNG", "test4.PNG", "test5.PNG",
			"test6.PNG", "test.PNG", "test2.PNG", "test3.PNG", "test4.PNG", "test5.PNG",
			"test6.PNG", "test.PNG", "test2.PNG", "test3.PNG", "test4.PNG", "test5.PNG",
			"test6.PNG", "test.PNG", "test2.PNG", "test3.PNG", "test4.PNG", "test5.PNG",
			"test6.PNG"
	};
	
	public class ImageAdapter extends BaseAdapter {
		
		private Context context;
		
		public ImageAdapter (Context context) {
			this.context = context;
		}
		
		@Override
		public int getCount() {
			
			return images.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return images[position];
		}

		@Override
		public long getItemId(int position) {
			
			return position;
		}

		@Override
		public View getView(int position, View convert_view, ViewGroup parent) {
			ImageView image_view;
			if (convert_view == null) {
				image_view = new ImageView(context);
				image_view.setLayoutParams(new GridView.LayoutParams(80,80));
				image_view.setScaleType(ScaleType.CENTER_CROP);
				image_view.setPadding(3, 3, 3, 3);
			} else {
				image_view = (ImageView) convert_view;
			}
//			image_view.setImageResource(image_ids[position]);
			image_view.setImageBitmap(BitmapFactory.decodeStream(
					FileManager.readFromFile(images[position])));
			return image_view;
		}
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		GridView gridview = (GridView)findViewById(R.id.main_ui_gridview);
		gridview.setAdapter(new ImageAdapter(this));
		gridview.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				Toast.makeText(getBaseContext(), Integer.toString(position), Toast.LENGTH_LONG).show();
				
			}
			
		});
	}
	

	
}

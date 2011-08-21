package th.co.fingertip.pillsalert.adapter;

import th.co.fingertip.pillsalert.FileManager;
import th.co.fingertip.pillsalert.PillsAlertEnum;
import th.co.fingertip.pillsalert.R;
import th.co.fingertip.pillsalert.db.DatabaseConfiguration;
import th.co.fingertip.pillsalert.factory.ImageFactory;
import android.R.drawable;
import android.content.Context;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class ImageAdapter extends BaseAdapter {

	private Context context;
	public String[] images;
	public Long[] ids;
	public Cursor pill_cursor;
	
	
	//public ImageAdapter (Context context, String[] images) {
	public ImageAdapter (Context context, Cursor pill_cursor) {
		this.context = context;
		this.pill_cursor = pill_cursor;
		//this.images = images;
		fill_data();
	}
	
	private void fill_data(){
		images = new String[pill_cursor.getCount()];
		ids = new Long[pill_cursor.getCount()];
		pill_cursor.moveToFirst();
		int i = 0;
		do{
			ids[i] = pill_cursor.getLong(
				pill_cursor.getColumnIndex(DatabaseConfiguration.PILL_SCHEMA_KEYS[0])
			);
		
			images[i] = pill_cursor.getString(
				pill_cursor.getColumnIndex(DatabaseConfiguration.PILL_SCHEMA_KEYS[3])
			);
			i = i+1;
		}while(pill_cursor.moveToNext());
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
		
		return ids[position];
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
//		image_view.setImageResource(image_ids[position]);
		if(!images[position].equals( PillsAlertEnum.FileName.PILL_DUMMY_FILENAME)){
			image_view.setImageBitmap(ImageFactory.get_bitmap(images[position]));
		}
		else{
			image_view.setImageResource(R.drawable.dummy_pill);
		}
		return image_view;
	}

}

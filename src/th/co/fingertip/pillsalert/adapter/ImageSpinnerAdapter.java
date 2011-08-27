package th.co.fingertip.pillsalert.adapter;

import java.util.Vector;

import th.co.fingertip.pillsalert.PillsAlertEnum;
import th.co.fingertip.pillsalert.R;
import th.co.fingertip.pillsalert.db.DatabaseConfiguration;
import th.co.fingertip.pillsalert.factory.ImageFactory;
import th.co.fingertip.pillsalert.util.Util;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class ImageSpinnerAdapter extends BaseAdapter {

	private Context context;
	int gallery_item_background;
	private boolean adjustable_flag;
	private Cursor cursor;
	private int model;

	private Vector<String> images = new Vector<String>();
	private Vector<Long> ids = new Vector<Long>();
	
	public ImageSpinnerAdapter (Context context) {
		init(context);
	}
	
	public ImageSpinnerAdapter (Context context, boolean flag) {
		init(context);
		adjustable_flag = flag;
	}
	
	public ImageSpinnerAdapter (Context context, boolean flag, Cursor cursor, int model) {
		init(context);
		adjustable_flag = flag;
		this.cursor = cursor;
		this.model = model;
		fillData();
	}
	
	private void fillData() {
		
		for (cursor.moveToFirst(); cursor.isLast(); cursor.moveToNext()) {
			long id = -1;	
			switch(model) {
			case PillsAlertEnum.Model.PILL:
				id = cursor.getInt(cursor.getColumnIndex(DatabaseConfiguration.PILL_SCHEMA_KEYS[0]));
				break;
			case PillsAlertEnum.Model.NOTIFICATION:
				id = cursor.getInt(cursor.getColumnIndex(DatabaseConfiguration.NOTIFICATION_SCHEMA_KEYS[1]));
				break;
			}
			ids.add(id);
			String image_name = cursor.getString(cursor.getColumnIndex
					(DatabaseConfiguration.PILL_SCHEMA_KEYS[3]));
			images.add(image_name);
		}
		
		cursor.close();
	}

	private void init(Context context) {
		this.context = context;
		TypedArray a = context.obtainStyledAttributes(R.styleable.Gallery1);
		gallery_item_background = a.getResourceId(
				R.styleable.Gallery1_android_galleryItemBackground, 0);
		a.recycle();
	}
	
	@Override
	public int getCount() {
		
		return images.size();
	}

	@Override
	public Object getItem(int position) {
		
		return position;
	}

	@Override
	public long getItemId(int position) {
		
		return ids.get(position);
	}
	
	public boolean addItem(View object_info) {
		Long id = (Long) object_info.getTag(R.id.image_id);
		String file_name = (String) object_info.getTag(R.id.image_name);
		if (!adjustable_flag) {
			return false;
		}

		if (!images.contains(file_name)) {
			images.add(file_name);
			ids.add(id);			
		} else {
			return false;
		}
		
		return true;
	}

	@Override
	public View getView(int position, View convert_view, ViewGroup parent) {
		ImageView i = new ImageView(context);
		i.setLayoutParams(new Gallery.LayoutParams(150, 150));
		i.setScaleType(ImageView.ScaleType.FIT_XY);
		i.setBackgroundResource(gallery_item_background);
		String file_name = images.get(position);
		if (file_name.equals(PillsAlertEnum.FileName.PILL_DUMMY_FILENAME)) {
			i.setImageResource(R.drawable.dummy_pill);
		} else {
			i.setImageBitmap(ImageFactory.get_bitmap(images.get(position)));
		}
		i.setTag(R.id.image_id, ids.get(position));
		i.setTag(R.id.image_name, file_name);
		return i;
	}
	
	
	
}
package th.co.fingertip.pillsalert.adapter;

import java.util.Arrays;
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
	private boolean notification_dummy;
	private Cursor cursor;
	private int model;
	
	//private Vector<Integer> image_ids = new Vector<Integer> (Arrays.asList(int_values));
	private Vector<String> images = new Vector<String>();
	private Vector<Boolean> pill_dummy_flags = new Vector<Boolean>(); //for pills
	private Vector<Long> ids = new Vector<Long>();
	//private Vector<Integer> image_id = new Vector<Integer>();
	
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
		notification_dummy = false;
		fillData();
		if (cursor.getCount() == 0 && model == PillsAlertEnum.Model.NOTIFICATION) {
			notification_dummy = true;
			ids.add((long) 0);
			images.add("");
		}
		
	}
	
	private void fillData() {
		if (!cursor.isClosed()) {
			cursor.move(-1);
		} 
		while(cursor.moveToNext()) {
			long id = -1;
			
			switch(model) {
			case PillsAlertEnum.Model.PILL:
				id = cursor.getInt(cursor.getColumnIndex(DatabaseConfiguration.PILL_SCHEMA_KEYS[0]));
				pill_dummy_flags.add((Util.integer_to_boolean(cursor.getInt(
						cursor.getColumnIndex(DatabaseConfiguration.PILL_SCHEMA_KEYS[4])))));
				break;
			case PillsAlertEnum.Model.NOTIFICATION:
				id = cursor.getInt(cursor.getColumnIndex(DatabaseConfiguration.NOTIFICATION_SCHEMA_KEYS[1]));
				break;
			}
			ids.add(id);
			String image_name = Long.toString(id) + ".PNG";
			images.add(image_name);
		}
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
		
		return ids.size();
	}

	@Override
	public Object getItem(int position) {
		
		return position;
	}

	@Override
	public long getItemId(int position) {
		
		return ids.get(position);
	}
	
	public boolean addItem(Object object_info) {
		
		String pic_name = object_info.toString() + ".PNG";
		
		if (!adjustable_flag) {
			return false;
		}
		
		if (!images.contains(pic_name)) {
			images.add(pic_name);
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
		if (model == PillsAlertEnum.Model.NOTIFICATION) {
			if (notification_dummy) {
				i.setImageResource(R.drawable.dummy_pill);
			} else {
				i.setImageBitmap(ImageFactory.get_bitmap(images.get(position)));
			}
		} else {
			if (pill_dummy_flags.get(position)) {
				i.setImageResource(R.drawable.dummy_pill);
			} else {
				i.setImageBitmap(ImageFactory.get_bitmap(images.get(position)));
			}
		}

		i.setTag(ids.get(position));
		
		return i;
	}
	
	
	
}
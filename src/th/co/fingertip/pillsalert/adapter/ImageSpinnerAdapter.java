package th.co.fingertip.pillsalert.adapter;

import java.util.Arrays;
import java.util.Vector;

import th.co.fingertip.pillsalert.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class ImageSpinnerAdapter extends BaseAdapter {


	private Context context;
	int gallery_item_background;
	
	private Integer[] int_values = {
			R.drawable.sample_0, R.drawable.sample_1, R.drawable.sample_2,
			R.drawable.sample_3, R.drawable.sample_4, R.drawable.sample_5,
			R.drawable.sample_6, R.drawable.sample_7,
	};
	
	private Vector<Integer> image_ids = new Vector<Integer> (Arrays.asList(int_values));
	
	public ImageSpinnerAdapter (Context context) {
		this.context = context;
		TypedArray a = context.obtainStyledAttributes(R.styleable.Gallery1);
		gallery_item_background = a.getResourceId(
				R.styleable.Gallery1_android_galleryItemBackground, 0);
		a.recycle();
	}
	@Override
	public int getCount() {
		
		return image_ids.size();
	}

	@Override
	public Object getItem(int position) {
		
		return position;
	}

	@Override
	public long getItemId(int position) {
		
		return position;
	}
	
	public void addItem(Object object_info) {
		String s = object_info.toString();
		image_ids.add(int_values[Integer.parseInt(s)]);
	}

	@Override
	public View getView(int position, View convert_view, ViewGroup parent) {
		ImageView i = new ImageView(context);
		
		i.setImageResource(image_ids.elementAt(position));
		i.setLayoutParams(new Gallery.LayoutParams(150, 150));
		i.setScaleType(ImageView.ScaleType.FIT_XY);
		i.setBackgroundResource(gallery_item_background);
		return i;
	}
	
	
	
	
}
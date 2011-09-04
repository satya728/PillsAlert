package th.co.fingertip.pillsalert.adapter;

import java.util.Arrays;
import java.util.Vector;

import th.co.fingertip.pillsalert.PillsAlertEnum;
import th.co.fingertip.pillsalert.R;
import th.co.fingertip.pillsalert.db.Pill;
import th.co.fingertip.pillsalert.factory.ImageFactory;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class PillImageAdapter extends BaseAdapter {

	private Context context;
	private Vector<Pill> pills;
	
	public PillImageAdapter(Context context, Vector<Pill> pills) {
		this.context = context;
		this.pills = pills;
	}
	
	public PillImageAdapter(Context context, Pill[] pills) {
		this.context = context;
		this.pills = new Vector<Pill>(Arrays.asList(pills));
	}
	
	@Override
	public int getCount() {
	
		return pills.size();
	}

	@Override
	public Object getItem(int position) {
		
		return pills.get(position);
	}
	
	@Override
	public long getItemId(int position) {

		return pills.get(position).id;
	}

	@Override
	public View getView(int position, View convert_view, ViewGroup parent) {
		ImageView image_view;
		
		if (convert_view == null) {
			image_view = new ImageView(context);
			image_view.setLayoutParams(new GridView.LayoutParams(100,100));
			image_view.setScaleType(ScaleType.CENTER_CROP);
			image_view.setPadding(3, 3, 3, 3);
		} else {
			image_view = (ImageView) convert_view;
		}
		
		String file_name = pills.get(position).image;
		if (file_name.equals(PillsAlertEnum.FileName.PILL_DUMMY_FILENAME)) {
			image_view.setImageResource(R.drawable.dummy_pill);
		} else {
			image_view.setImageBitmap(ImageFactory.get_bitmap(file_name));
		}
		
		return image_view;
	}

	public boolean deleteItem(Pill p) {
		
		if (pills.remove(p)) {
			notifyDataSetChanged();
			return true;
		}
		return false;
		
	}
}

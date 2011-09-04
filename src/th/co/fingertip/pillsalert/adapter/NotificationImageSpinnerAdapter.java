package th.co.fingertip.pillsalert.adapter;

import java.util.Arrays;
import java.util.Vector;

import th.co.fingertip.pillsalert.PillsAlertEnum;
import th.co.fingertip.pillsalert.R;
import th.co.fingertip.pillsalert.db.Notification;
import th.co.fingertip.pillsalert.db.Pill;
import th.co.fingertip.pillsalert.factory.ImageFactory;
import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class NotificationImageSpinnerAdapter extends BaseAdapter {

	private Context context;
	private Vector<Pill> pills;
	
	private int gallery_item_background;
	
	private final String DUMMY_STRING = "dummy_pill";
	private final String EMPTY_STRING = "";
	private final Pill DUMMY_PILL = new Pill(DUMMY_STRING, DUMMY_STRING, 
										PillsAlertEnum.FileName.PILL_DUMMY_FILENAME);
	public Vector<Integer> pill_ids = new Vector<Integer>();
	
	public NotificationImageSpinnerAdapter(Context context, Pill[] pills) {
		init(context, pills);
	}
	
	public NotificationImageSpinnerAdapter(Context context, Vector<Pill> pills) {
		init(context, pills);
	}

	private void init(Context context, Pill[] pills) {
		init(context, new Vector<Pill>(Arrays.asList(pills)));
	}
	
	private void init(Context context, Vector<Pill> pills) {
		this.context = context;
		TypedArray typed = context.obtainStyledAttributes(R.styleable.Gallery1);
		gallery_item_background = typed.getResourceId(
				R.styleable.Gallery1_android_galleryItemBackground, 0);
		typed.recycle();
		//check pills whether it is empty or not
		if (pills.size() == 0) {
			this.pills = new Vector<Pill>(Arrays.asList(new Pill[] {DUMMY_PILL}));
		} else {
			this.pills = pills;
		}
		
		//populate pill_ids
		for (Pill p : this.pills) {
			if (p != DUMMY_PILL) { 
				pill_ids.add(p.id);
			} 
		}
		
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

		return position;
	}

	@Override
	public View getView(int position, View convert_view, ViewGroup parent) {
		ImageView image_view;
		Pill pill = pills.get(position);
		String file_name = pill.image;
		if (convert_view == null) {
			image_view = new ImageView(context); 
			image_view.setLayoutParams(new Gallery.LayoutParams(150, 150));
			image_view.setScaleType(ScaleType.FIT_XY);
			image_view.setBackgroundResource(gallery_item_background);
		} else {
			image_view = (ImageView) convert_view;
		}
		//set picture
		if (file_name.equals(PillsAlertEnum.FileName.PILL_DUMMY_FILENAME)) {
			image_view.setImageResource(R.drawable.dummy_pill);
		} else {
			image_view.setImageBitmap(ImageFactory.get_bitmap(file_name));
		}
		image_view.setTag(R.id.image_id, pill.id);
		image_view.setTag(R.id.image_name, file_name);
		
		return image_view;				
	}																				
	
	
	//addItem		
	public boolean addItem(View object_info) {
		
		String file_name = (String) object_info.getTag(R.id.image_name);
		int id = (Integer) object_info.getTag(R.id.image_id);
		
		Pill p = new Pill(EMPTY_STRING, EMPTY_STRING, file_name);
		
		if (pills.contains(DUMMY_PILL)) {
			pills.remove(DUMMY_PILL);
		}
		
		boolean found = false;
		for (Pill temp : pills) {
			if ((temp.image).equals(p.image)) {
				found = true;
				break;
			}
		}
		
		if (!found) {
			pills.addElement(p);
			pill_ids.add(id);
			notifyDataSetChanged();
			return true;
		}
		
		return false;
		
	}
	
	//updateItem
	public void updateItem (Pill[] pills_array) {
		pills.clear();
		pill_ids.clear();
		init(context, pills_array);
		notifyDataSetChanged();
				
	}
	
	public boolean deleteItem(int pill_id, Pill removed_pill) {
		boolean result = pills.remove(removed_pill) && pill_ids.remove((Integer)pill_id);
		notifyDataSetChanged();
		return result;
	}
}

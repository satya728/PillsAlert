package th.co.fingertip.pillsalert.adapter;

import java.util.Arrays;
import java.util.Vector;

import th.co.fingertip.pillsalert.PillsAlertEnum;
import th.co.fingertip.pillsalert.R;
import th.co.fingertip.pillsalert.db.Notification;
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
	private Vector<Notification> notifications;
	private int gallery_item_background;
	
	private final int DUMMY_ID = -1;
	private final Notification DUMMY_NOTIFICATION = new Notification(DUMMY_ID, DUMMY_ID, 
														PillsAlertEnum.FileName.PILL_DUMMY_FILENAME);
	
	public NotificationImageSpinnerAdapter(Context context, Notification[] notifications) {
		init(context, notifications);
	}

	private void init(Context context, Notification[] notifications) {
		this.context = context;
		TypedArray typed = context.obtainStyledAttributes(R.styleable.Gallery1);
		gallery_item_background = typed.getResourceId(
				R.styleable.Gallery1_android_galleryItemBackground, 0);
		typed.recycle();
		//check pills whether it is empty or not
		if (notifications.length == 0) {
			this.notifications = new Vector<Notification>(Arrays.asList(new Notification[] 
			                                                            {DUMMY_NOTIFICATION}));
		} else {
			this.notifications = new Vector<Notification>(Arrays.asList(notifications));
		}
		
	}

	@Override
	public int getCount() {
		
		return notifications.size();
	}

	@Override
	public Object getItem(int position) {
		
		return notifications.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(int position, View convert_view, ViewGroup parent) {
		ImageView image_view;
		Notification notification = notifications.get(position);
		String file_name = notification.image;
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
		
		return image_view;
	}

	
	//addItem
	public boolean addItem(View object_info) {
		
		int add_id = (Integer) object_info.getTag(R.id.image_id);
		
		if (notifications.contains(DUMMY_NOTIFICATION)) {
			notifications.remove(DUMMY_NOTIFICATION);
		}
		if (Notification.find(add_id) == null) {

		}
		
		return false;
		
	}
	
}

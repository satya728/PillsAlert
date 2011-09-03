package th.co.fingertip.pillsalert.adapter;

import th.co.fingertip.pillsalert.PillsAlertEnum;
import th.co.fingertip.pillsalert.R;
import th.co.fingertip.pillsalert.db.Pill;
import th.co.fingertip.pillsalert.factory.ImageFactory;
import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class PillImageSpinnerAdapter extends BaseAdapter {

	private Context context;
	private int gallery_item_background;
	
	private Pill[] pills;

	private final String DUMMY_STRING = "dummy_pill";
	private final Pill DUMMY_PILL = new Pill(DUMMY_STRING, DUMMY_STRING, 
										PillsAlertEnum.FileName.PILL_DUMMY_FILENAME);
	
	public PillImageSpinnerAdapter(Context context, Pill[] pills) {
		init(context, pills);
	}

	private void init(Context context, Pill[] pills) {
		this.context = context;
		TypedArray typed = context.obtainStyledAttributes(R.styleable.Gallery1);
		gallery_item_background = typed.getResourceId(
				R.styleable.Gallery1_android_galleryItemBackground, 0);
		typed.recycle();
		//check pills whether it is empty or not
		if (pills.length == 0) {
			this.pills = new Pill[] {DUMMY_PILL};
		} else {
			this.pills = pills;
		}
	}
	
	@Override
	public int getCount() {
		
		return pills.length;
	}

	@Override
	public Object getItem(int position) {

		return pills[position];
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(int position, View convert_view, ViewGroup parent) {
		ImageView image_view;
		Pill pill = pills[position];
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

}

package th.co.fingertip.pillsalert.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Gallery;

public class DragDropGallery extends Gallery {

	public DragDropGallery(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initialize(context);
	}

	public DragDropGallery(Context context, AttributeSet attrs) {
		super(context, attrs);
		initialize(context);
	}

	public DragDropGallery(Context context) {
		super(context);
		initialize(context);
	}
	
	private void initialize(Context context) {
		// TODO Auto-generated method stub
		
	}
}

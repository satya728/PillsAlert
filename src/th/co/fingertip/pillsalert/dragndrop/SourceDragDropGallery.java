package th.co.fingertip.pillsalert.dragndrop;

import th.co.fingertip.pillsalert.adapter.PillImageSpinnerAdapter;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Gallery;
import android.widget.AdapterView;

public class SourceDragDropGallery extends Gallery implements DragSource, AdapterView.OnItemClickListener,  
	AdapterView.OnItemLongClickListener{

	private DragController dragger_controller;
	//private PillImageSpinnerAdapter pill_adapter;
	
	public SourceDragDropGallery(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}

	public SourceDragDropGallery(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SourceDragDropGallery(Context context) {
		super(context);
	}
	
	
	@Override
	protected void onFinishInflate() {
		setOnItemClickListener(this);
		setOnItemLongClickListener(this);
	}
	
	//drag source interface
	@Override
	public void setDragger(DragController controller) {
		// TODO Auto-generated method stub
		dragger_controller = controller;
	}

	@Override
	public void onDropCompleted(View target, boolean success) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position,
			long id) {
		if (!view.isInTouchMode()) {
			return false;
		}
		dragger_controller.startDrag(view, this, view, DragController.DRAG_ACTION_MOVE);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}

}

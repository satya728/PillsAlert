package th.co.fingertip.pillsalert.dragndrop;

import th.co.fingertip.pillsalert.R;
import th.co.fingertip.pillsalert.adapter.ImageSpinnerAdapter;
import th.co.fingertip.pillsalert.adapter.NotificationImageSpinnerAdapter;
import th.co.fingertip.pillsalert.adapter.PillImageSpinnerAdapter;
import th.co.fingertip.pillsalert.util.Util;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.SpinnerAdapter;

public class DragDropGallery extends Gallery implements AdapterView.OnItemClickListener, 
	AdapterView.OnItemLongClickListener, DragSource, DropTarget{

	private DragController dragger_controller;
	private ImageSpinnerAdapter image_adapter;
	private PillImageSpinnerAdapter pill_adapter;
	private NotificationImageSpinnerAdapter notification_adapter;
	
	public DragDropGallery(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public DragDropGallery(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public void setAdapter(SpinnerAdapter adapter) {
		//image_adapter = (ImageSpinnerAdapter) adapter;
		super.setAdapter(image_adapter);
	}
	
	public void setAdapter(PillImageSpinnerAdapter pill_adapter) {
		this.pill_adapter = pill_adapter;
		super.setAdapter(pill_adapter);
	}
	
	public void setAdapter(NotificationImageSpinnerAdapter notification_adapter) {
		this.notification_adapter = notification_adapter;
		super.setAdapter(notification_adapter);
	}

	public DragDropGallery(Context context) {
		super(context);
	}
	
	@Override
	protected void onFinishInflate() {
		setOnItemClickListener(this);
		setOnItemLongClickListener(this);
	}
	
	//Drag interfaces
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
		Util.put(getContext(), "long click at:" + position, Util.SHORT_TRACE);
		dragger_controller.startDrag(view, this, view, DragController.DRAG_ACTION_MOVE);
		
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		
		Util.put(getContext(), position+"", Util.SHORT_TRACE);
		
	}
	
	//Drop interfaces
	@Override
	public void onDrop(DragSource source, int x, int y, int xOffset,
			int yOffset, Object dragInfo) {
		
		Util.put(getContext(), "onDrop" + dragInfo.toString(), Util.SHORT_TRACE);
		View v = (View)dragInfo;
		String st = (String) v.getTag(R.id.image_name);
		notification_adapter.addItem(v);
		
		notification_adapter.notifyDataSetChanged();

	}

	@Override
	public void onDragEnter(DragSource source, int x, int y, int xOffset,
			int yOffset, Object dragInfo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDragOver(DragSource source, int x, int y, int xOffset,
			int yOffset, Object dragInfo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDragExit(DragSource source, int x, int y, int xOffset,
			int yOffset, Object dragInfo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean acceptDrop(DragSource source, int x, int y, int xOffset,
			int yOffset, Object dragInfo) {
		// TODO Auto-generated method stub
		Util.put(getContext(), "acceptDrop" + dragInfo.toString(), Util.SHORT_TRACE);
		return true;
	}

}

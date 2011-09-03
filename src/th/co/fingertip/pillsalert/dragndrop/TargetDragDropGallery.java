package th.co.fingertip.pillsalert.dragndrop;

import th.co.fingertip.pillsalert.adapter.NotificationImageSpinnerAdapter;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.SpinnerAdapter;

public class TargetDragDropGallery extends Gallery implements
		OnItemClickListener, OnItemLongClickListener, DropTarget  {
	
	private DragController dragger_controller;
	private NotificationImageSpinnerAdapter notification_adapter;
	
	public TargetDragDropGallery(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public TargetDragDropGallery(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public TargetDragDropGallery(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	//drop interfaces
	@Override
	public void onDrop(DragSource source, int x, int y, int xOffset,
			int yOffset, Object dragInfo) {
		// TODO Auto-generated method stub
		View v = (View)dragInfo;
		notification_adapter.addItem(v);
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
	
	public void setAdapter(NotificationImageSpinnerAdapter notification_adapter) {
		// TODO Auto-generated method stub
		this.notification_adapter = notification_adapter;
		super.setAdapter(notification_adapter);
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
		return true;
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}


}

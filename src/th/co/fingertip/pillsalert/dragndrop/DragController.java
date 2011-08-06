package th.co.fingertip.pillsalert.dragndrop;

import android.view.View;

/**
 * @author f
 * Interface for initiating a drag within a view.
 *
 */
public interface DragController {
	/**
	 * 
	 * @author f
	 * Interface to receive notifications when a drag starts or stops
	 *
	 */
	public static int DRAG_ACTION_MOVE = 0;
	interface DragListener {
		//drag starts
		void onDragStart(View v, DragSource source, Object info, int drag_action);
		//drag ends
		void onDragEnd();
		
	}
	
	void startDrag(View v, DragSource source, Object info, int drag_action);
	
	void setDragListener(DragListener l);
	
	void removeDragListener(DragListener l);
}

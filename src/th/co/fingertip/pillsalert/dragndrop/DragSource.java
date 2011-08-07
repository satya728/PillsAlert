package th.co.fingertip.pillsalert.dragndrop;

import android.view.View;

public interface DragSource {
	
	void setDragger(DragController controller);
	void onDropCompleted(View target, boolean success);
}

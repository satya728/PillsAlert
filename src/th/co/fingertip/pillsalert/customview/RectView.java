package th.co.fingertip.pillsalert.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class RectView extends View {
	Paint paint;
	
	public static class RectParams {
		
		public static final float[] POINTS = {
			100.0f, 100.0f, 100.0f, 300.0f, //line1
			100.0f, 100.0f, 300.0f, 100.0f, //line2
			300.0f, 300.0f, 100.0f, 300.0f, //line3
			300.0f, 300.0f, 300.0f, 100.0f  //line4
		};
		public static final int LEFT = 100;
		public static final int TOP = 100;
		public static final int RIGHT = 450;
		public static final int BOTTOM = 450;
		
	}
	public RectView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initialize();
	}

	public RectView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initialize();
	}

	public RectView(Context context) {
		super(context);
		initialize();
	}
	
	private void initialize() {
		paint = new Paint();
		paint.setColor(Color.RED);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		
		canvas.drawLines(RectParams.POINTS, paint);
		
		super.onDraw(canvas);
	}
	
}

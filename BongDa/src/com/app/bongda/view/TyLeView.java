package com.app.bongda.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class TyLeView extends View {
	private float per = 0.5f;

	public void setPer(float per) {
		this.per = per;
	}

	public TyLeView(Context context) {
		super(context);
		init();
	}

	private void init() {
		setWillNotDraw(false);
		paint.setColor(Color.RED);
	}

	public TyLeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public TyLeView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private Paint paint = new Paint();

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int width = getWidth();
		int height = getHeight();
		canvas.drawRect((int)(width*0.25),(int) (height *(1.0- per)), (int)(width*0.75),height, paint);
		invalidate();

	}
}

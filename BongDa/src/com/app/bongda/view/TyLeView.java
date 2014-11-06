package com.app.bongda.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

public class TyLeView extends TextView {
	private float per = 0.0f;
	private int mCurrentTop = -1;

	public void setPer(float per) {
		this.per = per;
		mCurrentTop = -1;
	}

	public TyLeView(Context context) {
		super(context);
		init();
	}

	private void init() {
		setWillNotDraw(false);
		paint.setColor(Color.RED);
		setGravity(Gravity.BOTTOM);
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
		int bottom = getHeight();

		int top = (int) (bottom * (1.0 - per));
		int left = (int) (width * 0.25);
		int right = (int) (width * 0.75);

		if (mCurrentTop == -1) {
			mCurrentTop = bottom;
		}

		if (mCurrentTop > top) {
			mCurrentTop = mCurrentTop - 10;
		}

		if (mCurrentTop <= top)
			mCurrentTop = top;
		canvas.drawRect(left, mCurrentTop, right, bottom, paint);

		invalidate();

	}
}

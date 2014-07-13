package com.kchodorow.gamegame.drawing;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class DrawView extends View implements OnTouchListener {

	ArrayList<Point> points = new ArrayList<Point>();
	Paint paint = new Paint();
	
	public DrawView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		this.setOnTouchListener(this);
		paint.setColor(Color.BLACK);
	}
	
	@Override
	public void onDraw(Canvas canvas) {
		for (Point point : points) {
			canvas.drawCircle(point.x, point.y, 5, paint);
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		Point point = new Point();
		point.x = event.getX();
		point.y = event.getY();
		points.add(point);
		invalidate();
		return true;
	}

}

class Point {
	float x;
	float y;
}

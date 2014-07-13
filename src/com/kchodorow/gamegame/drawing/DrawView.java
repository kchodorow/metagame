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

	private final String TAG = "DrawView";
	
	private final int STROKE_WIDTH = 5;
	
	ArrayList<Stroke> strokes = new ArrayList<Stroke>();
	Paint currentPaint;
	
	public DrawView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		this.setOnTouchListener(this);
		setColor(Color.BLACK);
	}
	
	public void setColor(int color) {
		currentPaint = new Paint();
		currentPaint.setColor(color);
		currentPaint.setStrokeWidth(STROKE_WIDTH);
	}
	
	@Override
	public void onDraw(Canvas canvas) {
		for (Stroke stroke : strokes) {
			stroke.draw(canvas);
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			brushDown(event);
			break;
		case MotionEvent.ACTION_MOVE:
			brushMove(event);
			break;
		}
		return true;
	}
	
	private void brushMove(MotionEvent event) {
		Stroke currentStroke = strokes.get(strokes.size() - 1);
		currentStroke.add(event.getX(), event.getY());
		invalidate();
	}
	
	private void brushDown(MotionEvent event) {
		strokes.add(new Stroke(currentPaint));
		brushMove(event);
	}
}

class Stroke {
	private ArrayList<Point> points = new ArrayList<Point>();
	private Paint paint;
	
	Stroke(Paint paint) {
		this.paint = paint;
	}
	
	void add(float x, float y) {
		Point point = new Point();
		point.x = x;
		point.y = y;
		points.add(point);
	}
	
	void draw(Canvas canvas) {
		Point prevPoint = null;
		for (Point point : points) {
			if (prevPoint != null) {
				canvas.drawLine(prevPoint.x, prevPoint.y, point.x, point.y, paint);
			}
			prevPoint = point;
		}
	}
}

class Point {
	float x;
	float y;
}

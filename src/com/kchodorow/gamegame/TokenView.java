package com.kchodorow.gamegame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

// Draggable and resizable image view.
public class TokenView extends ImageView {

	private Bitmap bitmap;
	
	public TokenView(Context context, Bitmap bitmap) {
		super(context);
		
		this.bitmap = bitmap;
		this.setImageBitmap(bitmap);
		
		this.setOnTouchListener(new ImageTouchListener());
	}
	
	public RelativeLayout.LayoutParams getParams() {
		return new RelativeLayout.LayoutParams(bitmap.getWidth(), bitmap.getHeight());
	}
	
	@SuppressLint("NewApi")
	class ImageTouchListener implements OnTouchListener {
		@Override
		public boolean onTouch(View view, MotionEvent motion) {
			DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
			view.startDrag(null, shadowBuilder, view, 0);
			return true;
		}
	}
}

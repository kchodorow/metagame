package com.kchodorow.gamegame.drawing;

import com.kchodorow.gamegame.R;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;

public class EditorActivity extends Activity implements OnTouchListener {
	
	private static final String TAG = "EditorActivity";
	
	private DrawView drawView;
	private ImageButton black;
	private ImageButton white;
	private ImageButton red;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);
        
        drawView = (DrawView)findViewById(R.id.draw_view);
        
		black = (ImageButton)findViewById(R.id.palette_black);
		black.setOnTouchListener(this);
		white = (ImageButton)findViewById(R.id.palette_white);
		white.setOnTouchListener(this);
		red = (ImageButton)findViewById(R.id.palette_red);
		red.setOnTouchListener(this);
	}
	
	public void saveHandler(View target) {
    }
	
	@Override
	public boolean onTouch(View view, MotionEvent event) {
		if (view == black) {
			drawView.setColor(Color.BLACK);		
		} else if (view == white) {
			drawView.setColor(Color.WHITE);
		} else if (view == red) {
			drawView.setColor(Color.RED);
		}
		return true;
	}
}

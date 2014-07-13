package com.kchodorow.gamegame.drawing;

import com.kchodorow.gamegame.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class EditorActivity extends Activity {
	DrawView drawView;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);
        
        drawView = (DrawView)findViewById(R.id.draw_view);
	}
	
	public void saveHandler(View target) {
    }
}

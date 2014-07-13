package com.kchodorow.gamegame;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.kchodorow.gamegame.game.GameView;
import com.kchodorow.gamegame.game.RandomGenerator;

@SuppressLint("NewApi")
public class EditGameActivity extends Activity {
	
	private final String LOG = "EditGameActivity";
	private GameView game;
	private RelativeLayout workArea;
	
	private enum ListItem {
		BOARD("Add board"),
		TOKEN("Add token"),
		DECK("Add deck"),
		RANDOM("Add random");
		
		private final String description;
		
		ListItem(String description) {
			this.description = description;
		}
		
		public String description() {
			return this.description;
		}
	}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_game);
        
        game = new GameView(this);
        
        final ArrayList<String> edit_list = new ArrayList<String>();
        for (ListItem item : ListItem.values()) {
          edit_list.add(item.description());
        }
        
        workArea = (RelativeLayout)findViewById(R.id.work_area);
        workArea.setOnDragListener(new OnDragListener() {
			@Override
			public boolean onDrag(View view, DragEvent event) {
				switch (event.getAction()) {
				case DragEvent.ACTION_DROP:
					View droppable = (View) event.getLocalState();
					RelativeLayout.LayoutParams params = (LayoutParams)droppable.getLayoutParams();
					params.leftMargin = (int)event.getX();
		            params.topMargin = (int)event.getY();
		            droppable.setLayoutParams(params);
					Log.e(LOG, "dropping: "+event);
		            break;
				}
				return true;
			}
        	
        });
        
        ListView listView = (ListView)findViewById(R.id.add_item_view);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
        		this, android.R.layout.simple_list_item_1, edit_list);
        listView.setAdapter(adapter);
        
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Log.e(LOG, "Pos: "+position);
				switch (position) {
				case 0:
					break;
				case 1:
					break;
				case 2:
					break;
				case 3:
					Intent makeRandom = new Intent(EditGameActivity.this, AddRandomActivity.class);
					startActivityForResult(makeRandom, position);
					break;
				default:
					throw new IllegalArgumentException("Got unexpected position: " + position);
				}
			}
		 
        });
    }
    
    @SuppressLint("NewApi")
	@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
    	if (resultCode == RESULT_OK) {
    		Bundle bundle = data.getExtras();
    		int min = bundle.getInt("min");
    		int max = bundle.getInt("max");
    		game.addRandomGenerator(new RandomGenerator(min, max));
    		
            final Button button = new Button(this);
            button.setText("x");
            button.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View view, MotionEvent motion) {
					DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
			    	view.startDrag(null, shadowBuilder, view, 0);
			    	return true;
				}
            	
            });
            
            
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100, 100);
            params.leftMargin = 75;
            params.topMargin = 500;
    		workArea.addView(button, params);
    	}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_edit_game, menu);
        return true;
    }
    
}

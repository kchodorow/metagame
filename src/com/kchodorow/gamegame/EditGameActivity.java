package com.kchodorow.gamegame;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.ImageView;
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
	
	private final int BOARD = 0;
	private final int TOKEN = 1;
	private final int DECK = 2;
	private final int RANDOM = 3;
	
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
        
        final ArrayList<String> editList = new ArrayList<String>();
        for (ListItem item : ListItem.values()) {
          editList.add(item.description());
        }
        
        ListView listView = (ListView)findViewById(R.id.add_item_view);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
        		this, android.R.layout.simple_list_item_1, editList);
        listView.setAdapter(adapter);
        
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				switch (position) {
				case BOARD:
					Intent makeBoard = new Intent(EditGameActivity.this, AddBoardActivity.class);
					startActivityForResult(makeBoard, position);
					break;
				case TOKEN:
					break;
				case DECK:
					break;
				case RANDOM:
					Intent makeRandom = new Intent(EditGameActivity.this, AddRandomActivity.class);
					startActivityForResult(makeRandom, RANDOM);
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
    	if (resultCode != RESULT_OK) {
    		return;
    	}
    	switch (requestCode) {
    	case BOARD:
    		addBoard(data);
    		break;
    	case RANDOM:
    		addRandom(data);
    		break;
    	default:
    		throw new IllegalArgumentException("Invalid request: " + requestCode);
    	}
    }

    private void addBoard(Intent data) {
    	ImageView imageView = new ImageView(this);
    	Bitmap bitmap = null;
    	if (data.hasExtra("filename")) {
    		String bitmapFile = data.getExtras().get("filename").toString();
    		bitmap = BitmapFactory.decodeFile(bitmapFile);
    		
    	} else if (data.hasExtra("data")) {
    		bitmap = (Bitmap)data.getExtras().get("data");
    	} else {
    		throw new IllegalArgumentException("No bitmap found: " + data);
    	}
    	imageView.setImageBitmap(bitmap);
    	RelativeLayout.LayoutParams params = 
    			new RelativeLayout.LayoutParams(bitmap.getWidth(), bitmap.getHeight());
        workArea.addView(imageView, params);
    }
    
    private void addRandom(Intent data) {
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

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_edit_game, menu);
        return true;
    }
    
    
}

package com.kchodorow.gamegame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddRandomActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_random);        
    }

    public void cancelHandler(View target) {
    	setResult(RESULT_CANCELED);
    	finish();
    }
    
    public void saveHandler(View target) {
    	EditText editText = (EditText)findViewById(R.id.random_gen_min);
    	String min = editText.getText().toString();
    	editText = (EditText)findViewById(R.id.random_gen_max);
    	String max = editText.getText().toString();
    	Intent retval = new Intent();
    	Bundle extras = new Bundle();
    	extras.putInt("min", Integer.parseInt(min));
    	extras.putInt("max", Integer.parseInt(max));
    	retval.putExtras(extras);
    	setResult(RESULT_OK, retval);
    	finish();
    }
}

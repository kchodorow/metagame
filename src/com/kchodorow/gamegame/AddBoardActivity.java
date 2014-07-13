package com.kchodorow.gamegame;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class AddBoardActivity extends Activity {
	
	private String LOG = "AddBoardActivity";
	
	private final int CAMERA = 0;
	private final int GALLERY = 1;
	private final int DRAW = 2;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_board);
        
        final ArrayList<String> optionsList = new ArrayList<String>();
        optionsList.add("Camera");
        optionsList.add("Gallery");
        optionsList.add("Draw");
        
        ListView listView = (ListView)findViewById(R.id.add_board_view);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
        		this, android.R.layout.simple_list_item_1, optionsList);
        listView.setAdapter(adapter);
        
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
				switch (position) {
				case CAMERA:
					getImageFromCamera();
					break;
				case GALLERY:
					getImageFromGallery();
					break;
				case DRAW:
					break;
				default:
					throw new IllegalArgumentException("Nothing to click as pos " + position);
				}
			}
        });
	}

    public void getImageFromCamera() {
    	Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
    	startActivityForResult(intent, CAMERA);
    }

    public void getImageFromGallery() {
		Intent intent = new Intent(
				Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY);
    }
    
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	Log.e(LOG, "Got: "+data.getData());
        if (resultCode != RESULT_OK) {
        	return;
        }
        ImageView imageView = new ImageView(this);
        switch (requestCode) {
        case CAMERA:
        	imageView.setImageBitmap((Bitmap)data.getExtras().get("data"));
        	break;
        case GALLERY:
        	Uri selectedImage = data.getData();
        	Log.e(LOG, "selected image: "+selectedImage);
        	String filePathColumn[] = {MediaStore.Images.Media.DATA};
        	Cursor cursor = getContentResolver()
        			.query(selectedImage, filePathColumn, null, null, null);
        	cursor.moveToFirst();
        	int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        	String filePath = cursor.getString(columnIndex);
        	cursor.close();
        	
        	imageView.setImageBitmap(BitmapFactory.decodeFile(filePath));
        	break;
        case DRAW:
        	break;
        default:
        	throw new IllegalArgumentException("Got request code " + requestCode);
        }
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100, 100);
        
        ((RelativeLayout)findViewById(R.id.add_board_layout)).addView(imageView, params);

//        Intent retval = new Intent();
//        setResult(RESULT_OK, retval);
//        finish();
    }
}

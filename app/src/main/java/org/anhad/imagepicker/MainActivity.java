package org.anhad.imagepicker;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import org.anhad.imagepicker.Util.Util;

import java.io.File;


public class MainActivity extends Activity implements View.OnClickListener {

    private String APP_NAME = "TEST_APP_UTIL";
//    private Button cameraButton,galleryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getFragmentManager().beginTransaction().replace(R.id.rootLayout,new HelloFragment()).commit();
/*
        cameraButton = (Button) findViewById(R.id.CameraButton);
        galleryButton = (Button) findViewById(R.id.GalleryButton);

        cameraButton.setOnClickListener(this);
        galleryButton.setOnClickListener(this);*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
     /*    if(view.getId() == R.id.CameraButton)
            getImageFromCamera();
        if(view.getId() ==  R.id.GalleryButton)
            getImageFromGallery();*/
    }

    private void getImageFromCamera() {
        Util.openCamera(this,null);
    }

    private void getImageFromGallery() {
        Util.openGallery(this,null);
    }

   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent receivedIntent) {
        super.onActivityResult(requestCode, resultCode, receivedIntent);
        if (requestCode == Util.OPEN_GALLERY_REQUEST && resultCode == RESULT_OK){
            File file = Util.getGalleryImage(this,receivedIntent, APP_NAME);
            if (file != null)Log.d("GALLERY",file.toString());
        }
        else if(requestCode == Util.OPEN_CAMERA_REQUEST && resultCode == RESULT_OK){
            File file = Util.getCameraImage(this,receivedIntent, APP_NAME);
            if (file != null)Log.d("CAMERA",file.toString());
        }
    }*/
}

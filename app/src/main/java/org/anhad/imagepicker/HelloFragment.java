package org.anhad.imagepicker;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.anhad.imagepicker.Util.Util;

import java.io.File;
import java.util.zip.Inflater;

/**
 * Created by Anhad on 4/8/15.
 */
public class HelloFragment extends Fragment implements View.OnClickListener {
    private String APP_NAME = "TEST_APP_UTIL";

    private Button cameraButton,galleryButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_hello,null);

        cameraButton = (Button) view.findViewById(R.id.CameraButton);
        galleryButton = (Button) view.findViewById(R.id.GalleryButton);

        cameraButton.setOnClickListener(this);
        galleryButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.CameraButton)
            getImageFromCamera();
        if(view.getId() ==  R.id.GalleryButton)
            getImageFromGallery();
    }

    private void getImageFromCamera() {
        Util.openCamera(getActivity(),this);
    }

    private void getImageFromGallery() {
        Util.openGallery(getActivity(),this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent receivedIntent) {
        super.onActivityResult(requestCode, resultCode, receivedIntent);

        if (requestCode == Util.OPEN_GALLERY_REQUEST && resultCode == Activity.RESULT_OK){
            File file = Util.getGalleryImage(getActivity(),receivedIntent, APP_NAME);
            if (file != null) Log.d("GALLERY", file.toString());
        }
        else if(requestCode == Util.OPEN_CAMERA_REQUEST && resultCode == Activity.RESULT_OK){
            File file = Util.getCameraImage(getActivity(),receivedIntent, APP_NAME);
            if (file != null)Log.d("CAMERA",file.toString());
        }
    }
}

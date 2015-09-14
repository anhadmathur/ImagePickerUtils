package org.anhad.imagepicker.Util;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Created by Anhad on 4/8/15.
 */
public class Util {
    public static final int OPEN_CAMERA_REQUEST = 8;
    public static final int OPEN_GALLERY_REQUEST = 7;

    public static String getUniqueFileNameFromGallery(String appName, File fileName)
    {
        String fileNameArray[] = fileName.toString().split("\\.");
        String extension = fileNameArray[fileNameArray.length-1];

        String uniqueFileName = appName+"_"+String.valueOf(System.currentTimeMillis())+"."+extension;

        return uniqueFileName;
    }

    public static String getUniqueFileNameFromCamera(String appName,String extension)
    {
        String uniqueFileName = appName+"_"+String.valueOf(System.currentTimeMillis())+"."+extension;

        return uniqueFileName;
    }

    public static void openCamera(Context context,Fragment fragment)
    {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
            if (fragment == null) {
                ((Activity) context).startActivityForResult(takePictureIntent, OPEN_CAMERA_REQUEST);
            }
            else if (fragment != null){
                fragment.startActivityForResult(takePictureIntent, OPEN_CAMERA_REQUEST);
            }
        }
    }

    public static void openGallery(Context context, Fragment fragment)
    {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (fragment == null) {
            ((Activity) context).startActivityForResult(galleryIntent, OPEN_GALLERY_REQUEST);
        }
        else if (fragment != null) {
            fragment.startActivityForResult(galleryIntent, OPEN_GALLERY_REQUEST);
        }
    }

    private static File saveImageToExternalStorage(Context context, Bitmap image, String appName) {

        String APP_PATH_SD_CARD = "/"+appName+"/";
        String APP_THUMBNAIL_PATH_SD_CARD;
        if (null == image)
            return null;

        APP_THUMBNAIL_PATH_SD_CARD = String.valueOf(System.currentTimeMillis());
        String fullPath = Environment.getExternalStorageDirectory()
                .getAbsolutePath()
                + APP_PATH_SD_CARD
                + APP_THUMBNAIL_PATH_SD_CARD;

        try {
            File dir = new File(fullPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            OutputStream fOut;

            File file = new File(fullPath, getUniqueFileNameFromCamera(appName,"jpg"));
            file.createNewFile();
            fOut = new FileOutputStream(file);

            // 100 means no compression, the lower you go, the stronger the
            // compression
            //image.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            image.compress(Bitmap.CompressFormat.JPEG,100,fOut);
            fOut.flush();
            fOut.close();

            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), file.getName(), file.getName());

            return file;

        } catch (Exception e) {
            Log.e("saveToExternalStorage()", e.getMessage());
            return null;
        }
    }

    public static File getGalleryImage(Context context, Intent intent, String appName){
        try {
            if (intent != null) {

                Uri selectedImage = intent.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                // Get the cursor
                Cursor cursor = context.getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String fileName = cursor.getString(columnIndex);

                File oldFile = new File(fileName);

                File newFile = new File(oldFile.getParent()+"/"+getUniqueFileNameFromGallery(appName, oldFile));
                oldFile.renameTo(newFile);

                cursor.close();

                return newFile;

            } else {
                Toast.makeText(context, "You haven't picked Image !",
                        Toast.LENGTH_LONG).show();
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static File getCameraImage(Context context, Intent intent, String appName){
        Bundle extras = intent.getExtras();
        File file = saveImageToExternalStorage(context, (Bitmap) extras.get("data"), appName);

        return file;
    }
}

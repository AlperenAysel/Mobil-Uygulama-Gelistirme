package com.example.mug_proje;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class PhotoActivity extends AppCompatActivity {


    Button gal;
    ImageView image_view;
    Button cam;
    Uri image_uri;

    private static final int IMAGE_PICK_CODE =1000;
    private static final int PERMISSION_CODE =1001;
    private static final int IMAGE_CAPTURE_CODE =1002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        image_view = findViewById(R.id.image_view);
        gal = findViewById(R.id.gallery);
        cam = findViewById(R.id.cam);

     gal.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

             if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                 if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                     String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                     requestPermissions(permissions,PERMISSION_CODE );

                 } else {
                     pickFromGallery();

                 }
             } else {
                 pickFromGallery();

             }

         }
     });

     cam.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                 if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED ||
                         checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
                     String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
                     requestPermissions(permissions,PERMISSION_CODE );

                 } else {
                     pickFromCam();

                 }
             } else {
                 pickFromCam();

             }

         }
     });


    }
    public  void  pickFromCam() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"New Pic");
        values.put(MediaStore.Images.Media.DESCRIPTION,"From The Camera");

        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(cameraIntent,IMAGE_CAPTURE_CODE);

    }

    public void pickFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == IMAGE_PICK_CODE) {
            switch (requestCode){
                case PERMISSION_CODE: {
                    if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        pickFromGallery();
                    } else {
                        Toast.makeText(this,"permission denied",Toast.LENGTH_SHORT).show();
                    }
                }

            }
        }
        if(requestCode == IMAGE_CAPTURE_CODE) {
            switch (requestCode){
                case PERMISSION_CODE: {
                    if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        pickFromCam();
                    } else {
                        Toast.makeText(this,"permission denied",Toast.LENGTH_SHORT).show();
                    }
                }

            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
             image_view.setImageURI(data.getData());
        }
        if(resultCode == RESULT_OK && requestCode == IMAGE_CAPTURE_CODE) {
            image_view.setImageURI(image_uri);
        }
    }
}

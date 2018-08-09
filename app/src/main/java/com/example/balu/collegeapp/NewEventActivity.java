package com.example.balu.collegeapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import id.zelory.compressor.Compressor;

public class NewEventActivity extends AppCompatActivity {

    private static final String TAG = "NewEventActivity";
    private EditText contact, details, location, title, website;
    private TextView img;
    private Button newEventBtn;
    private FirebaseFirestore mStore;
    private Uri mainImageURI = null;
    private StorageReference storageReference;
    private Bitmap compressedImageFile;
    private Uri download_uri=null ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);
        contact = (EditText) findViewById(R.id.contact);
        details = (EditText) findViewById(R.id.details);
        img = (TextView) findViewById(R.id.image);
        location = (EditText) findViewById(R.id.location);
        title = (EditText) findViewById(R.id.title);
        website = (EditText) findViewById(R.id.website);
        newEventBtn = (Button) findViewById(R.id.newEventBtn);
        mStore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        newEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            /****Img Compression*****/
                File newImageFile = new File(mainImageURI.getPath());
                try {

                    compressedImageFile = new Compressor(NewEventActivity.this)
                            .setMaxHeight(125)
                            .setMaxWidth(125)
                            .setQuality(50)
                            .compressToBitmap(newImageFile);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                compressedImageFile.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] thumbData = baos.toByteArray();
                UploadTask image_path = storageReference.child("event_posters").child(title.getText().toString() + ".jpg").putBytes(thumbData);
                image_path.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful()) {
                            storeFirestore(task);
                        } else {
                            String error = task.getException().getMessage();
                            Toast.makeText(NewEventActivity.this, "(IMAGE Error) : " + error, Toast.LENGTH_LONG).show();

                        }
                    }

                    private void storeFirestore(Task<UploadTask.TaskSnapshot> task) {


                        if(task != null) {

                            download_uri = task.getResult().getDownloadUrl();
                            Log.d(TAG, "storeFirestore: " + download_uri.toString());


                        } else {

                            download_uri = mainImageURI;

                        }
                        // Create a new user with a first and last name
                        Map<String, Object> user = new HashMap<>();
                        user.put("event_contact", contact.getText().toString());
                        user.put("event_details", details.getText().toString());
                        user.put("event_img",  download_uri.toString());
                        user.put("event_location", location.getText().toString());
                        user.put("event_title", title.getText().toString());
                        user.put("event_website", website.getText().toString());
                        // Add a new document with a generated ID
                        mStore.collection("events")
                                .add(user)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w(TAG, "Error adding document", e);
                                    }
                                });
                    }
                });



               /* // Create a new user with a first and last name
                Map<String, Object> user = new HashMap<>();
                user.put("event_contact", contact.getText().toString());
                user.put("event_details", details.getText().toString());
                user.put("event_img",  download_uri.toString());
                user.put("event_location", location.getText().toString());
                user.put("event_title", title.getText().toString());
                user.put("event_website", website.getText().toString());
                // Add a new document with a generated ID
                mStore.collection("events")
                        .add(user)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error adding document", e);
                            }
                        });*/



            }
        });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (ContextCompat.checkSelfPermission(NewEventActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                        Toast.makeText(NewEventActivity.this, "Permission Denied", Toast.LENGTH_LONG).show();
                        ActivityCompat.requestPermissions(NewEventActivity.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

                    } else {

                        BringImagePicker();

                    }

                } else {

                    BringImagePicker();

                }

            }

        });

    }

    private void BringImagePicker() {


        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1, 1)
                .start(NewEventActivity.this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                mainImageURI = result.getUri();
                Log.d(TAG, "onActivityResult: "+ mainImageURI);


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                Exception error = result.getError();

            }
        }

    }
}

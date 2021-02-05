package com.example.text_recognizer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.Nullable;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    Button btnTake,btnPr,btnProcessed;
    Bitmap imBM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        btnTake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCallingOrSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, 101);
                }

                takpicture();

            }
        });



       btnPr.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               //1. create a FirebaseVisionImage object from a Bitmap object
               FirebaseVisionImage firebaseVisionImage = FirebaseVisionImage.fromBitmap(imBM);
               //2. Get an instance of FirebaseVision
               FirebaseVision firebaseVision = FirebaseVision.getInstance();
               //3. Create an instance of FirebaseVisionTextRecognizer
               FirebaseVisionTextRecognizer firebaseVisionTextRecognizer = firebaseVision.getOnDeviceTextRecognizer();
               //4. Create a task to process the image
               Task<FirebaseVisionText> task = firebaseVisionTextRecognizer.processImage(firebaseVisionImage);
               task.addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                   @Override
                   public void onSuccess(FirebaseVisionText firebaseVisionText) {
                       String text = firebaseVisionText.getText();
                       btnProcessed.setVisibility(View.VISIBLE);

                       btnProcessed.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               Intent intent =new Intent(MainActivity.this,ProcessedTextActivity.class);
                           }
                       });
                   }
               });
               //6. if task is failure
               task.addOnFailureListener(new OnFailureListener() {
                   @Override
                   public void onFailure(@NonNull Exception e) {
                       Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                   }
               });

           }
       });
    }

    private void takpicture() {

        Intent intent =new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,101);

        protected void onActivityResult(int requestCode,int resultCode,@Nullable Intent data){
            super.onActivityResult(requestCode, resultCode, data);
            Bundle bundle = data.getExtras();
            imBM=(Bitmap) bundle.get("data");
            imageView.setImageBitmap(imBM);


        }

    }


    private void initViews() {

        imageView=findViewById(R.id.imageView);
        btnTake=findViewById(R.id.btnTake);
        btnPr=findViewById(R.id.btnPr);
        btnProcessed=findViewById(R.id.btnProcessed);

    }
}
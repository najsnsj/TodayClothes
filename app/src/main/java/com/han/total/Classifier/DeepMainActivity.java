package com.han.total.Classifier;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.widget.Button;

import com.han.total.R;

public class DeepMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deepmain);

        Button galleryBtn = findViewById(R.id.deepgalleryBtn);
        galleryBtn.setOnClickListener(view -> {
            Intent i = new Intent(DeepMainActivity.this, DeepGalleryActivity.class);
            startActivity(i);
        });

        Button cameraBtn = findViewById(R.id.deepcameraBtn);
        cameraBtn.setOnClickListener(view -> {
            Intent i = new Intent(DeepMainActivity.this, DeepCameraActivity.class);
            startActivity(i);
        });
    }
}
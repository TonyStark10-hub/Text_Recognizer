package com.example.text_recognizer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ProcessedTextActivity extends AppCompatActivity {
    private TextView txtAns;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_processed_text);

        txtAns=findViewById(R.id.txtAns);
        Intent intent =getIntent();
        String rec=intent.getStringExtra("ProcessedText");

        txtAns.setText(rec.toString());


    }
}
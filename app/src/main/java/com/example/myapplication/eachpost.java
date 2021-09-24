package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class eachpost extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eachpost);
        Intent intent=getIntent();
        String POST=intent.getStringExtra("POST");
        TextView textView=(TextView)findViewById(R.id.P1);
        textView.setText(POST);
    }
}
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
        String IMG_URL=intent.getStringExtra("IMG_URL");
        String LIKE_C=intent.getStringExtra("LIKE_C");
        String TEXT=intent.getStringExtra("TEXT");
        TextView textView_img=(TextView)findViewById(R.id.img_url);
        TextView textView_like=(TextView)findViewById(R.id.good_count);
        TextView textView_text=(TextView)findViewById(R.id.posttext);
        textView_img.setText(IMG_URL);
        textView_like.setText(LIKE_C);
        textView_text.setText(TEXT);
    }
}
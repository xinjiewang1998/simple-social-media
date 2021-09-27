package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class chatBox extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_box);
        Intent intent  = getIntent();
        String name = intent.getStringExtra("user");
//        Bitmap pic = intent.getParcelableExtra("pic");
        TextView t = (TextView) findViewById(R.id.friend_name);
//        int pic = Integer.parseInt(intent.getStringExtra("pic"));
//        Bitmap pic = intent.getParcelableExtra("pic");
        ImageView temp = (ImageView)findViewById(R.id.pic);
//        temp.setImageBitmap(pic);
        temp.setImageResource(R.drawable.ic_launcher_background);
        t.setText(name);
    }
    public void toProfile(View v){
//        Intent intent = new Intent(getApplicationContext(),profile.class)
    }
}
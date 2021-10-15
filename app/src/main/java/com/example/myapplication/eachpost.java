package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class eachpost extends AppCompatActivity {
    private Integer Position;
    private String LIKE_C;
    TextView textView_like;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eachpost);
        Intent intent=getIntent();
        LIKE_C=intent.getStringExtra("LIKE_C");
        String TEXT=intent.getStringExtra("TEXT");
        Position=intent.getIntExtra("POSITION",0);
        textView_like=(TextView)findViewById(R.id.LikeCount);
        TextView textView_text=(TextView)findViewById(R.id.posttext);
        textView_like.setText(LIKE_C);
        textView_text.setText(TEXT);
        Button button=(Button) findViewById(R.id.Like);
        button.setOnClickListener(LikeListener);
    }
    private View.OnClickListener LikeListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
            DatabaseReference myRef=firebaseDatabase.getReference("allpost");
            Integer NewLikeCount=(Integer.parseInt(LIKE_C))+1;
            myRef.child(Position.toString()).child("like_count").setValue(NewLikeCount);
            textView_like.setText(NewLikeCount.toString());
        }
    };
}
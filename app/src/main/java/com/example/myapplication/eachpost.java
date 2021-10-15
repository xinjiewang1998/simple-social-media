package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class eachpost extends AppCompatActivity {
    private Integer Position;
    private String LIKE_C;
    TextView textView_like;
    private int ClickCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eachpost);
        ClickCount=1;
        Intent intent=getIntent();
        LIKE_C=intent.getStringExtra("LIKE_C");
        String TEXT=intent.getStringExtra("TEXT");
        Position=intent.getIntExtra("POSITION",0);
        textView_like=(TextView)findViewById(R.id.LikeCount);
        TextView textView_text=(TextView)findViewById(R.id.posttext);
        textView_like.setText(LIKE_C);
        textView_text.setText(TEXT);
        Button LikeButton=(Button) findViewById(R.id.Like);
        LikeButton.setOnClickListener(LikeListener);
        Button FavoriteButton=(Button) findViewById(R.id.Favorite);
        FavoriteButton.setOnClickListener(FavoriteListener);
    }
    private View.OnClickListener FavoriteListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
            DatabaseReference myRef=firebaseDatabase.getReference("FavoritePost");
            myRef.setValue(1);
        }
    };
    private View.OnClickListener LikeListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
            DatabaseReference myRef=firebaseDatabase.getReference("allpost");
            Integer NewLikeCount=(Integer.parseInt(LIKE_C));
            if(ClickCount%2!=0){
                NewLikeCount++;
            }
            ClickCount++;
            myRef.child(Position.toString()).child("like_count").setValue(NewLikeCount);
            textView_like.setText(NewLikeCount.toString());
        }
    };
}
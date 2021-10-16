package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class eachpost extends AppCompatActivity {
    private Integer Position;
    private String LIKE_C;
    private TextView textView_like;
    private int ClickCount;
    private Boolean flag=false;
    private FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eachpost);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
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
            FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
            DatabaseReference myRef=firebaseDatabase.getReference("FavoritePost");
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot dss: snapshot.getChildren()){
                        if(Position.equals(dss.child("Position").getValue(Integer.class)) && firebaseUser.getEmail().equals(dss.child("User").getValue(String.class))){
                            flag=true;     // not to add duplicate post for the same user.
                            break;
                        }
                    }
                    if(!flag){
                        HashMap<String,Object> UserPost=new HashMap<>();
                        UserPost.put("Position",Position);
                        UserPost.put("User",firebaseUser.getEmail());
                        myRef.push().setValue(UserPost);
                        Toast.makeText(getApplicationContext(),"Collect into Favorite Post Successfully",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Have already collected this post",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
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
                Button button=findViewById(R.id.Like);
                button.setText("Unlike");
            }
            else{
                Button button=findViewById(R.id.Like);
                button.setText("like");
            }
            ClickCount++;
            myRef.child(Position.toString()).child("like_count").setValue(NewLikeCount);
            textView_like.setText(NewLikeCount.toString());
        }
    };
}
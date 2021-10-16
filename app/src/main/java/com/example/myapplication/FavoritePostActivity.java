package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.HashMap;


public class FavoritePostActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private RecyclerView recyclerView;
    private ArrayList<HashMap<String,Object>> FavoritePostList;
    private ArrayList<HashMap<String,Object>> AllPostList;
    private ArrayList<Integer> PositionList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_post);
        mAuth=FirebaseAuth.getInstance();
        recyclerView= (RecyclerView)findViewById(R.id.FavoritePostList);
        AllPostList=new ArrayList<>();
        FavoritePostList=new ArrayList<>();
        PositionList=new ArrayList<>();
        readFPost();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Button button=(Button)findViewById(R.id.AllPost);
        button.setOnClickListener(FavoritePostListener);
    }
    private void readFPost(){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference myRef=firebaseDatabase.getReference("FavoritePost");
        myRef.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot dss: snapshot.getChildren()){
                        if(firebaseUser.getEmail().equals(dss.child("User").getValue(String.class))){
                            Integer p=dss.child("Position").getValue(Integer.class);
                            PositionList.add(p);
                        }
                    }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.err.println("Failed to retrieve data, error: "+error.toException());
            }
        });



        DatabaseReference Ref=firebaseDatabase.getReference("allpost");
        Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                AllPostList.clear();
                for(DataSnapshot dss: snapshot.getChildren()){
                    HashMap<String,Object> Plist=new HashMap<>();
                    Integer LikeCount=dss.child("like_count").getValue(Integer.class);
                    String Text=dss.child("text").getValue(String.class);
                    Integer CommentCount=dss.child("comment_count").getValue(Integer.class);
                    String ImgUrl=dss.child("img_url").getValue(String.class);
                    Plist.put("text",Text);
                    Plist.put("like_count",LikeCount);
                    Plist.put("img_url",ImgUrl);
                    Plist.put("comment_count",CommentCount);
                    AllPostList.add(Plist);
                }
                for(int i=0;i<PositionList.size();i++){
                    FavoritePostList.add(AllPostList.get(PositionList.get(i)));
                }
                PostAdapter postAdapter=new PostAdapter(getApplicationContext(),FavoritePostList);
                recyclerView.setAdapter(postAdapter);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

            }



    private View.OnClickListener FavoritePostListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(getApplicationContext(),AllPostPage.class);
            startActivity(intent);
        }
    };
}
package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.myapplication.R;
import java.util.ArrayList;
import java.util.HashMap;

public class AllPostPage extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private ArrayList<HashMap<String,Object>>AllPostItems;
    private ArrayList<HashMap<String,String>>AllPostText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_post_page);
        mAuth=FirebaseAuth.getInstance();

        recyclerView=(RecyclerView) findViewById(R.id.AllPostView);
        AllPostItems=new ArrayList<>();
        readJson();
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void readJson(){
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference myRef=firebaseDatabase.getReference("allpost");
        /*
        myRef = FirebaseDatabase.getInstance().getReference("User").child("u77777331");
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("email", "zlc");
        hashMap.put("id", "u77777331");
        myRef.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(AllPostPage.this, "Register succeed", Toast.LENGTH_SHORT).show();
                }
            }
        });

         */
        //HashMap<String,Object> Plist=new HashMap<>();
        //Integer CommentCount=5;
        //String ImgUrl="https";
        //Integer LikeCount=4;
        //String Text="@uidij";
        //Plist.put("img_url",ImgUrl);
        //Plist.put("text",Text);
        //Plist.put("comment_count",CommentCount);
        //Plist.put("like_count",LikeCount);
        //AllPostItems.add(Plist);
        System.out.println("++++++++++++++++++++++++");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                System.out.println("++++++++++++++++++++++++");
                AllPostItems.clear();
               for(DataSnapshot dss: snapshot.getChildren()){
                   HashMap<String,Object> Plist=new HashMap<>();

                   Integer CommentCount=dss.child("comment_count").getValue(Integer.class);
                   String ImgUrl=dss.child("img_url").getValue(String.class);
                   Integer LikeCount=dss.child("like_count").getValue(Integer.class);
                   String Text=dss.child("text").getValue(String.class);
                   Plist.put("img_url",ImgUrl);
                   Plist.put("text",Text);
                   Plist.put("comment_count",CommentCount);
                   Plist.put("like_count",LikeCount);
                   AllPostItems.add(Plist);

               }
                postAdapter=new PostAdapter(getApplicationContext(),AllPostItems);
                recyclerView.setAdapter(postAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.err.println("Failed to retrieve data, error: "+error.toException());
            }
        });

    }

}
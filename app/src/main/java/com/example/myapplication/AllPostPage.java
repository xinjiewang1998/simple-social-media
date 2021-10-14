package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.appsearch.AppSearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;


import com.example.myapplication.search.QueryEngine;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.myapplication.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;


public class AllPostPage extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private RecyclerView recyclerView;
    private ArrayList<HashMap<String,Object>>AllPostItems;
    private ArrayList<HashMap<String,Object>> BufferPostList=new ArrayList<>();
    private PostAdapter postAdapter;
    private PostsConcreteCollection postsConcreteCollection;
    private Iterator PostIterator;
    private SearchView PostsearchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_post_page);
        mAuth=FirebaseAuth.getInstance();

        recyclerView=(RecyclerView) findViewById(R.id.AllPostView);
        AllPostItems=new ArrayList<>();
        readJson();
        PostsearchView=(SearchView)findViewById(R.id.SearchPost);
        PostsearchView.setIconifiedByDefault(true);
        PostsearchView.setSubmitButtonEnabled(true);
        PostsearchView.onActionViewExpanded();
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        PostsearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(!TextUtils.isEmpty(newText)){

                }
                return false;
            }
        });
    }

    public class PostsConcreteCollection implements IterableCollection{
        private ArrayList<HashMap<String,Object>> AllPostCollection=AllPostItems;
        @Override
        public Iterator createIterator() {
            return new PostsConcreteIterator();
        }
        private class PostsConcreteIterator implements Iterator{
            int index=0;
            @Override
            public boolean hasNext() {
                if(AllPostCollection!=null && index<AllPostCollection.size()){
                    return true;
                }
                return false;
            }

            @Override
            public Object next() {
                if(this.hasNext()){
                    return AllPostCollection.get(index++);
                }
                return null;
            }
        }
    }

    private class MyTask extends TimerTask
    {
        private Activity context;
        MyTask(Activity context)
        {
            this.context = context;
        }

        @Override
        public void run()
        {
            context.runOnUiThread(updateThread);
            System.gc();
        }
    }
    Runnable updateThread = new Runnable()
    {

        @Override
        public void run()
        {
            if(PostIterator.hasNext()){
                HashMap<String,Object> SinglePost=(HashMap<String,Object>)PostIterator.next();
                BufferPostList.add(SinglePost);
            }
            postAdapter=new PostAdapter(getApplicationContext(),BufferPostList);
            recyclerView.setAdapter(postAdapter);
        }

    };
    private void readJson() {
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference myRef=firebaseDatabase.getReference("allpost");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
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
                postsConcreteCollection=new PostsConcreteCollection();
                PostIterator=postsConcreteCollection.createIterator();
                HashMap<String,Object> SinglePost=(HashMap<String,Object>)PostIterator.next();
                BufferPostList.add(SinglePost);
                postAdapter=new PostAdapter(getApplicationContext(),BufferPostList);
                recyclerView.setAdapter(postAdapter);
                Timer timer = new Timer();
                timer.schedule(new MyTask(AllPostPage.this),0,2000);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.err.println("Failed to retrieve data, error: "+error.toException());
            }
        });

    }


}
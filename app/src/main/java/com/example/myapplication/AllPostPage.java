package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;


import com.example.myapplication.search.QueryEngine;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private ArrayList<HashMap<String,Object>> SearchResultList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_post_page);
        mAuth=FirebaseAuth.getInstance();

        recyclerView=(RecyclerView) findViewById(R.id.AllPostView);
        AllPostItems=new ArrayList<>();
        SearchResultList=new ArrayList<>();
        readJson();
        PostsearchView=(SearchView)findViewById(R.id.SearchPost);
        PostsearchView.setIconifiedByDefault(true);
        PostsearchView.setSubmitButtonEnabled(true);
        PostsearchView.onActionViewExpanded();
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Button button=(Button)findViewById(R.id.FavoritePost);
        button.setOnClickListener(AllPostListener);
        PostsearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(!TextUtils.isEmpty(query)){
                    for(int i=0;i<AllPostItems.size();i++){
                        QueryEngine queryEngine = new QueryEngine(AllPostItems.get(i).toString());
                        List<String> outputs=queryEngine.queryText(query);
                        if(outputs!=null && outputs.size()!=0){
                            HashMap<String,Object> SingleResult=new HashMap<>();
                            SingleResult.put("like_count",AllPostItems.get(i).get("like_count"));
                            SingleResult.put("text",outputs.get(0));
                            SearchResultList.add(SingleResult);
                        }
                    }
                    Intent intent=new Intent(getApplicationContext(),SearchResultActivity.class);
                    ArrayList<String> TextList=new ArrayList<>();
                    ArrayList<Integer> LikeCountList=new ArrayList<>();
                    for(int j=0;j<SearchResultList.size();j++){
                        String text=SearchResultList.get(j).get("text").toString();
                        Integer LikeCount=(Integer) SearchResultList.get(j).get("like_count");
                        TextList.add(text);
                        LikeCountList.add(LikeCount);
                    }
                    intent.putStringArrayListExtra("TEXTLIST",TextList);
                    intent.putIntegerArrayListExtra("LIKECOUNTLIST",LikeCountList);
                    startActivity(intent);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
    private View.OnClickListener AllPostListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(getApplicationContext(),FavoritePostActivity.class);
            startActivity(intent);
        }
    };
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
                   Integer LikeCount=dss.child("like_count").getValue(Integer.class);
                   String Text=dss.child("text").getValue(String.class);
                   Integer CommentCount=dss.child("comment_count").getValue(Integer.class);
                   String ImgUrl=dss.child("img_url").getValue(String.class);
                   Plist.put("text",Text);
                   Plist.put("like_count",LikeCount);
                   Plist.put("img_url",ImgUrl);
                   Plist.put("comment_count",CommentCount);
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
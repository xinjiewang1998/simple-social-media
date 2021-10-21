package com.example.myapplication.post;

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


import com.example.myapplication.R;
import com.example.myapplication.search.PostObj;
import com.example.myapplication.search.QueryEngine;
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
    private ArrayList<HashMap<String, Object>> allPostItems;
    private ArrayList<HashMap<String, Object>> BufferPostList;
    private PostAdapter postAdapter;
    private PostsConcreteCollection postsConcreteCollection;
    private Iterator PostIterator;
    private ArrayList<HashMap<String, Object>> SearchResultList;
    private List<PostObj> postObjList;
    private ArrayList<Integer> postPosition;
    private List<String> outputs;
    private ArrayList<String>  imgUrlList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_post_page);
        mAuth = FirebaseAuth.getInstance();

        recyclerView = (RecyclerView) findViewById(R.id.AllPostView);
        allPostItems = new ArrayList<>();
        BufferPostList = new ArrayList<>();
        SearchResultList = new ArrayList<>();
        postObjList=new ArrayList<>();
        postPosition=new ArrayList<>();
        outputs=new ArrayList<>();
        imgUrlList=new ArrayList<>();
        readJson();
        SearchView PostSearchView = (SearchView) findViewById(R.id.SearchPost);
        PostSearchView.setIconifiedByDefault(true);
        PostSearchView.setSubmitButtonEnabled(true);
        //PostSearchView.onActionViewExpanded();
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Button button = (Button) findViewById(R.id.FavoritePost);
        button.setOnClickListener(AllPostListener);
        PostSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!TextUtils.isEmpty(query)) {
                        QueryEngine queryEngine = new QueryEngine(postObjList);
                        outputs = queryEngine.queryText(query);
                        if (outputs != null && outputs.size() != 0) {
                          findPostPosition();
                          for(int i=0;i<outputs.size();i++){
                              HashMap<String, Object> SingleResult = new HashMap<>();
                              SingleResult.put("like_count",postPosition.get(i));
                              SingleResult.put("text", outputs.get(i));
                              SingleResult.put("img_url",imgUrlList.get(i));
                              SearchResultList.add(SingleResult);
                          }
                        }
                    Intent intent = new Intent(getApplicationContext(), SearchResultActivity.class);
                    ArrayList<String> TextList = new ArrayList<>();
                    ArrayList<Integer> LikeCountList = new ArrayList<>();
                    ArrayList<String> imgList=new ArrayList<>();
                    for (int j = 0; j < SearchResultList.size(); j++) {
                        String text = SearchResultList.get(j).get("text").toString();
                        Integer LikeCount = (Integer) SearchResultList.get(j).get("like_count");
                        String imgUrl=SearchResultList.get(j).get("img_url").toString();
                        TextList.add(text);
                        LikeCountList.add(LikeCount);
                        imgList.add(imgUrl);
                    }
                    intent.putStringArrayListExtra("TEXTLIST", TextList);
                    intent.putIntegerArrayListExtra("LIKECOUNTLIST", LikeCountList);
                    intent.putStringArrayListExtra("IMGLIST",imgList);
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
    private void findPostPosition(){

        for(int i=0;i<outputs.size();i++){
            for(int j=0;j<postObjList.size();j++){
                if(outputs.get(i).equals(allPostItems.get(j).get("text").toString())){
                    Integer position=(Integer) allPostItems.get(j).get("like_count");
                    String  imgUrl=allPostItems.get(j).get("img_url").toString();
                    postPosition.add(position);
                    imgUrlList.add(imgUrl);
                }
            }
        }

    }
    private View.OnClickListener AllPostListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), FavoritePostActivity.class);
            startActivity(intent);
        }
    };

    public class PostsConcreteCollection implements IterableCollection {
        private ArrayList<HashMap<String, Object>> AllPostCollection = allPostItems;

        @Override
        public Iterator createIterator() {
            return new PostsConcreteIterator();
        }

        private class PostsConcreteIterator implements Iterator {
            int index = 0;

            @Override
            public boolean hasNext() {
                if (AllPostCollection != null && index < AllPostCollection.size()) {
                    return true;
                }
                return false;
            }

            @Override
            public Object next() {
                if (this.hasNext()) {
                    return AllPostCollection.get(index++);
                }
                return null;
            }
        }
    }

    private class MyTask extends TimerTask {
        private Activity context;

        MyTask(Activity context) {
            this.context = context;
        }

        @Override
        public void run() {
            context.runOnUiThread(updateThread);
        }
    }

    Runnable updateThread = new Runnable() {

        @Override
        public void run() {
            if (PostIterator.hasNext()) {
                HashMap<String, Object> SinglePost = (HashMap<String, Object>) PostIterator.next();
                BufferPostList.add(SinglePost);
            }
            postAdapter = new PostAdapter(getApplicationContext(), BufferPostList);
            recyclerView.setAdapter(postAdapter);

        }

    };

    private void readJson() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference("allpost");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                allPostItems.clear();
                for (DataSnapshot dss : snapshot.getChildren()) {
                    HashMap<String, Object> pList = new HashMap<>();
                    Integer likeCount = dss.child("like_count").getValue(Integer.class);
                    String text = dss.child("text").getValue(String.class);
                    Integer commentCount = dss.child("comment_count").getValue(Integer.class);
                    String imgUrl = dss.child("img_url").getValue(String.class);
                    pList.put("text", text);
                    pList.put("like_count", likeCount);
                    pList.put("img_url", imgUrl);
                    pList.put("comment_count", commentCount);
                    int commentC=commentCount;
                    int likeC=likeCount;
                    PostObj postObj = new PostObj(imgUrl,commentC, likeC, text);
                    allPostItems.add(pList);
                    postObjList.add(postObj);

                }
                postsConcreteCollection = new PostsConcreteCollection();
                PostIterator = postsConcreteCollection.createIterator();
                HashMap<String, Object> SinglePost = (HashMap<String, Object>) PostIterator.next();
                BufferPostList.add(SinglePost);
                postAdapter = new PostAdapter(getApplicationContext(), BufferPostList);
                recyclerView.setAdapter(postAdapter);
                Timer timer = new Timer();
                timer.schedule(new MyTask(AllPostPage.this), 0, 2000);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.err.println("Failed to retrieve data, error: " + error.toException());
            }
        });

    }


}
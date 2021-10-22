package com.example.myapplication.post;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.post.PostAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchResultActivity extends AppCompatActivity {
    private ArrayList<HashMap<String, Object>> SearchResultList;
    private RecyclerView recyclerView;
    private PostAdapter postAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        recyclerView = (RecyclerView) findViewById(R.id.SearchResultList);
        SearchResultList = new ArrayList<>();
        viewSearchResult();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void viewSearchResult() {
        Intent intent = getIntent();
        ArrayList<String> TextList = intent.getStringArrayListExtra("TEXTLIST");
        ArrayList<Integer> LikeCountList = intent.getIntegerArrayListExtra("LIKECOUNTLIST");
        ArrayList<String> imgList = intent.getStringArrayListExtra("IMGLIST");
        for (int i = 0; i < TextList.size(); i++) {
            HashMap<String, Object> SingleResult = new HashMap<>();
            SingleResult.put("text", TextList.get(i));
            SingleResult.put("like_count", LikeCountList.get(i));
            SingleResult.put("img_url", imgList.get(i));
            SearchResultList.add(SingleResult);
        }
        if (SearchResultList.size() == 0) {
            Toast.makeText(getApplicationContext(), "Can not find posts with search tag!", Toast.LENGTH_SHORT).show();
        } else {
            postAdapter = new PostAdapter(getApplicationContext(), SearchResultList);
            recyclerView.setAdapter(postAdapter);
        }
    }
}
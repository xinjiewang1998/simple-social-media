package com.example.myapplication.post;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchResultActivity extends AppCompatActivity {
    private ArrayList<HashMap<String, Object>> searchResultList;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        recyclerView = findViewById(R.id.SearchResultList);
        searchResultList = new ArrayList<>();
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
            searchResultList.add(SingleResult);
        }

        if (searchResultList.size() == 0) {
            Toast.makeText(getApplicationContext(), "Can not find posts with search tag!", Toast.LENGTH_SHORT).show();
        } else {
            PostAdapter postAdapter = new PostAdapter(getApplicationContext(), searchResultList);
            recyclerView.setAdapter(postAdapter);
        }
    }
}
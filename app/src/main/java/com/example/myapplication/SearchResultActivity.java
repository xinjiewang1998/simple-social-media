package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchResultActivity extends AppCompatActivity {
    private ArrayList<HashMap<String,Object>> SearchResultList;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        recyclerView=(RecyclerView) findViewById(R.id.SearchResultList);
        Intent intent=getIntent();
        ArrayList<String> TextList=intent.getStringArrayListExtra("TEXTLIST");
        ArrayList<Integer> LikeCountList=intent.getIntegerArrayListExtra("LIKECOUNTLIST");
        for(int i=0;i<TextList.size();i++){
            HashMap<String,Object> SingleResult=new HashMap<>();
            SingleResult.put("text",TextList.get(i));
            SingleResult.put("like_count",LikeCountList.get(i));
            SearchResultList.add(SingleResult);
        }
        PostAdapter postAdapter=new PostAdapter(getApplicationContext(),SearchResultList);
        recyclerView.setAdapter(postAdapter);
    }
}
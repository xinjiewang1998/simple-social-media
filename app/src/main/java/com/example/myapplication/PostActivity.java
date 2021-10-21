package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.myapplication.post.AllPostPage;
import com.example.myapplication.post.allpost;
import com.example.myapplication.post.eachpost;

import java.util.ArrayList;

public class PostActivity extends AppCompatActivity {

    ArrayList<String> postItems = new ArrayList<>();
    ArrayList<String> hottestItem = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post);
        Intent intent = new Intent(getApplicationContext(), AllPostPage.class);
        startActivity(intent);
        hottestItem.add("hottestPost");
        postItems.add("Post1");
        postItems.add("Post2");
        postItems.add("Post3");
        postItems.add("Post4");
        postItems.add("Post5");

        ListView hottestItem =  findViewById(R.id.hottest);
        ListView postItemsList = findViewById(R.id.post_list);
        ArrayAdapter post_items_adapter = new ArrayAdapter(PostActivity.this, android.R.layout.simple_list_item_1, postItems);
        ArrayAdapter hottest_item_adapter = new ArrayAdapter(PostActivity.this, android.R.layout.simple_list_item_1, this.hottestItem);
        hottestItem.setAdapter(hottest_item_adapter);
        postItemsList.setAdapter(post_items_adapter);

        // Capture our button from layout
        Button button = findViewById(R.id.all);

        // Register the onClick listener with the implementation above
        button.setOnClickListener(myListener);
        postItemsList.setOnItemClickListener(myItemListener);
    }


    private final View.OnClickListener myListener = new View.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), allpost.class);
            startActivity(intent);
        }
    };


    private final ListView.OnItemClickListener myItemListener = new ListView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            Intent intent = new Intent(getApplicationContext(), eachpost.class);
            intent.putExtra("POST", postItems.get(i));
            startActivity(intent);
        }
    };
}
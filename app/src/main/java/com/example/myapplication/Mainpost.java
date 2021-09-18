package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class Mainpost extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post);
        ArrayList<String> postitems=new ArrayList<>();
        ArrayList<String> hottestitem=new ArrayList<>();
        hottestitem.add("hosttestPost");
        postitems.add("Post1");
        postitems.add("Post2");
        postitems.add("Post3");
        postitems.add("Post4");
        postitems.add("Post5");
        ListView hottest_item= (ListView) findViewById(R.id.hottest);
        ListView post_items_list= (ListView) findViewById(R.id.post_list);

        ArrayAdapter post_items_adapter=new ArrayAdapter(this, android.R.layout.simple_list_item_1,postitems);
        ArrayAdapter hottest_item_adapter=new ArrayAdapter(this, android.R.layout.simple_list_item_1,hottestitem);
        hottest_item.setAdapter(hottest_item_adapter);
        post_items_list.setAdapter(post_items_adapter);
// Capture our button from layout
        Button button = (Button)findViewById(R.id.all);
// Register the onClick listener with the implementation above
        button.setOnClickListener(myListener);
    }
    private View.OnClickListener myListener = new View.OnClickListener() {
        public void onClick(View v) {
            Intent intent=new Intent(getApplicationContext(),allpost.class);
            startActivity(intent);                  // do something when the button is clicked
        }
    };
}
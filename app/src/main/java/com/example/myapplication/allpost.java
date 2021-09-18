package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class allpost extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allpost);
        ArrayList<String> postitems=new ArrayList<>();
        postitems.add("Posta");
        postitems.add("Postb");
        postitems.add("Postc");
        postitems.add("Postd");
        postitems.add("Poste");
        postitems.add("Post66");
        postitems.add("Post77");
        ListView post_items_list= (ListView) findViewById(R.id.all_post_list);

        ArrayAdapter post_items_adapter=new ArrayAdapter(this, android.R.layout.simple_list_item_1,postitems);

        post_items_list.setAdapter(post_items_adapter);
    }
}
package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class allpost extends AppCompatActivity {
    ArrayList<String> allpostitems=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allpost);
        allpostitems.add("Posta");
        allpostitems.add("Postb");
        allpostitems.add("Postc");
        allpostitems.add("Postd");
        allpostitems.add("Poste");
        allpostitems.add("Post66");
        allpostitems.add("Post77");
        ListView post_items_list= (ListView) findViewById(R.id.all_post_list);

        ArrayAdapter post_items_adapter=new ArrayAdapter(allpost.this, android.R.layout.simple_list_item_1,allpostitems);

        post_items_list.setAdapter(post_items_adapter);
        ListView listview=(ListView) findViewById(R.id.all_post_list);
        listview.setOnItemClickListener(myitemlistener);
    }

    private ListView.OnItemClickListener myitemlistener=new ListView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            Intent intent=new Intent(getApplicationContext(), eachpost.class);
            intent.putExtra("POST",allpostitems.get(i));
            startActivity(intent);
        }
    };


}
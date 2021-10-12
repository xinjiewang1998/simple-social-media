package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.myapplication.search.QueryEngine;
import com.example.myapplication.search.StoreEngine;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class PostActivity extends AppCompatActivity {

    ArrayList<String> postitems=new ArrayList<>();
    ArrayList<String> hottestitem=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post);
        Intent intent = new Intent(getApplicationContext(), AllPostPage.class);
        startActivity(intent);
        hottestitem.add("hosttestPost");
        postitems.add("Post1");
        postitems.add("Post2");
        postitems.add("Post3");
        postitems.add("Post4");
        postitems.add("Post5");

        ListView hottest_item= (ListView) findViewById(R.id.hottest);
        ListView post_items_list= (ListView) findViewById(R.id.post_list);
        ArrayAdapter post_items_adapter=new ArrayAdapter(PostActivity.this, android.R.layout.simple_list_item_1,postitems);
        ArrayAdapter hottest_item_adapter=new ArrayAdapter(PostActivity.this, android.R.layout.simple_list_item_1,hottestitem);
        hottest_item.setAdapter(hottest_item_adapter);
        post_items_list.setAdapter(post_items_adapter);

        // Capture our button from layout
        Button button = (Button)findViewById(R.id.all);

        // Register the onClick listener with the implementation above
        button.setOnClickListener(myListener);
        ListView listview=(ListView) findViewById(R.id.post_list);
        listview.setOnItemClickListener(myitemlistener);

        searchExample();
    }


    private View.OnClickListener myListener = new View.OnClickListener() {
        public void onClick(View v) {
            Intent intent=new Intent(getApplicationContext(),allpost.class);
            startActivity(intent);                  // do something when the button is clicked
        }
    };


    private ListView.OnItemClickListener myitemlistener = new ListView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            Intent intent=new Intent(getApplicationContext(), eachpost.class);
            intent.putExtra("POST",postitems.get(i));
            startActivity(intent);
        }
    };

    private void searchExample() {
        try {
            InputStream is = getAssets().open("allpostdata.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");

            QueryEngine queryEngine = new QueryEngine(json);

            System.out.println("==============================================================");
            List<String> outputs = queryEngine.queryText("#APeoplesJourney");

//            outputs = queryEngine.queryText("#NoTag");
//            outputs = queryEngine.queryText("#NoTag & @acommonname");
//            outputs = queryEngine.queryText("#NoTag | @acommonname");
//            outputs = queryEngine.queryText("!#NoTag");
            outputs = queryEngine.queryText("#APeoplesJourney | (#NoTag & !@acommonname)");

//            outputs = queryEngine.queryText("(#NoTag & !@acommonname) | #APeoplesJourney");
            System.out.println(outputs);
            System.out.println(outputs.size());
            System.out.println("==============================================================");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
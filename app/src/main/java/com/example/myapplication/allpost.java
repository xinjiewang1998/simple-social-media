package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class allpost extends AppCompatActivity {
    ListView post_items_list;
    ArrayList<HashMap<String,Object>>allpostitems=new ArrayList<>();
    ArrayList<HashMap<String,String>>allposttext=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allpost);
        /*
        allpostitems.add("Posta");
        allpostitems.add("Postb");
        allpostitems.add("Postc");
        allpostitems.add("Postd");
        allpostitems.add("Poste");
        allpostitems.add("Post66");
        allpostitems.add("Post77");

         */
        post_items_list= (ListView) findViewById(R.id.all_post_list);
        try{
            JSONObject obj=new JSONObject(LoadJsonfile());
            JSONArray  post_array=obj.getJSONArray("allpost");
            HashMap<String,Object> p_list;  //String:String info
            HashMap<String,String> pt_list;
            for(int i=0;i<post_array.length();i++){
               JSONObject o= post_array.getJSONObject(i);
               String img_url=o.getString("img_url");
               Integer comment_c=o.getInt("comment_count");
               Integer like_c=o.getInt("like_count");
               String text=o.getString("text");
               p_list=new HashMap<>();
               //pt_list=new HashMap<>();
               p_list.put("img_url",img_url);
               p_list.put("comment_count",comment_c);
               p_list.put("like_count",like_c);
               p_list.put("text",text);
               //String postput=text.substring(0,21);

               //pt_list.put("text",postput);
               allpostitems.add(p_list);
               //allposttext.add(pt_list);
            }
        }catch(JSONException e){
            e.getStackTrace();
        }
        ArrayAdapter post_items_adapter=new ArrayAdapter(allpost.this,android.R.layout.simple_list_item_1,allposttext);

        post_items_list.setAdapter(post_items_adapter);
        ListView listview=(ListView) findViewById(R.id.all_post_list);
        listview.setOnItemClickListener(myitemlistener);
    }

    private ListView.OnItemClickListener myitemlistener=new ListView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            Intent intent=new Intent(getApplicationContext(), eachpost.class);
            intent.putExtra("IMG_URL",allpostitems.get(i).get("img_url").toString());
            intent.putExtra("COMMENT_C",allpostitems.get(i).get("comment_count").toString());
            intent.putExtra("LIKE_C",allpostitems.get(i).get("like_count").toString());
            intent.putExtra("TEXT",allpostitems.get(i).get("text").toString());
            startActivity(intent);
        }
    };

    public String LoadJsonfile(){
        String json=null;
        try{
            InputStream is=this.getAssets().open("allpostdata.json");
            int size=is.available();
            byte []buffer=new byte[size];
            is.read(buffer);
            is.close();
            json=new String(buffer,"UTF-8");

        }catch(IOException e){
            e.getStackTrace();
        }
        return json;
    }

}
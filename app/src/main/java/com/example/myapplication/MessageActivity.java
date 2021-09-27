package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list_friends);
        List<User> listFriends = new ArrayList<>();
        User user1 = new User("Wiley");
        User user2 = new User("Alan");
        listFriends.add(user1);
        listFriends.add(user2);

        friendAdapter a = new friendAdapter(getApplicationContext(),listFriends);
        recyclerView.setAdapter(a);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
    public void toReply(View v){
//        Intent intent  = new Intent(getApplicationContext(),reply.class);
//        startActivity(intent);
    }
    public void toLike(View v){
//        Intent intent  = new Intent(getApplicationContext(),like.class);
//        startActivity(intent);
    }
    public void chatWith(View v){
        Intent intent  = new Intent(getApplicationContext(),chatBox.class);
        startActivity(intent);
    }
    }

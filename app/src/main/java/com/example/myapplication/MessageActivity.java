package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private friendAdapter friendAdapter;
    private RecyclerView recyclerView;
    private List<User> listFriends;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        TextView account = (TextView) findViewById(R.id.account_name);
        setContentView(R.layout.activity_message);
        recyclerView = (RecyclerView) findViewById(R.id.list_friends);
        mAuth = FirebaseAuth.getInstance();
//        String name = mAuth.getCurrentUser().getEmail();

//        account.setText(name);

        listFriends = new ArrayList<>();
        readUser();
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void readUser(){
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listFriends.clear();
                for(DataSnapshot snap: snapshot.getChildren()){
                    User user = snap.getValue(User.class);
                    assert user!=null;
                    if(!user.getId().equals(firebaseUser.getUid())){
                        listFriends.add(user);
                    }
                    friendAdapter = new friendAdapter(getApplicationContext(),listFriends);
                    recyclerView.setAdapter(friendAdapter);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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

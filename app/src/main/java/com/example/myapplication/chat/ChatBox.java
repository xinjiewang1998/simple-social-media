package com.example.myapplication.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChatBox extends AppCompatActivity {
    //Field
    TextView friend_name;
    FirebaseUser firebaseUser;
    DatabaseReference reference;
    Intent intent;
    List<Chat> listChat;
    RecyclerView recyclerView;
    EditText editText;
    Button send;
    ImageView image;
    MessageAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_box);
        //RecyclerView
        recyclerView = findViewById(R.id.chat_box);
        friend_name = (TextView) findViewById(R.id.friend_name);
        image = (ImageView) findViewById(R.id.pic);
        recyclerView = findViewById(R.id.chat_box);
        editText = findViewById(R.id.input_text);
        send = findViewById(R.id.send);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("user").child(firebaseUser.getUid());
        intent = getIntent();
        String nameId = intent.getStringExtra("userId");
        String name = intent.getStringExtra("user");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Read message from firebase when a new message send to it.
                readMessage(firebaseUser.getUid(), nameId);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // send button to send message to firebase
                String msg = editText.getText().toString();
                if (!msg.equals("")) {
                    sendMessage(firebaseUser.getUid(), nameId, msg);
                }
                editText.setText("");
            }
        });
        friend_name = (TextView) findViewById(R.id.friend_name);
        image.setImageResource(R.drawable.test);
        friend_name.setText(name);
    }

    /**
     * Click function of send button, send message in the box to another user.
     *
     * @param sender   the current user
     * @param receiver the user the current user want to chat with
     * @param msg      message need to be send
     */
    private void sendMessage(String sender, String receiver, String msg) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("msg", msg);
        reference.child("Chat").push().setValue(hashMap);
    }

    /**
     * receive message from firebase
     *
     * @param myID   current user's ID
     * @param userID the id of another user.
     */
    private void readMessage(String myID, String userID) {
        listChat = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Chat");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listChat.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Chat chat = dataSnapshot.getValue(Chat.class);
                    if (chat.getReceiver().equals(myID) && chat.getSender().equals(userID) ||
                            chat.getReceiver().equals(userID) && chat.getSender().equals(myID)) {
                        listChat.add(chat);
                    }
                    messageAdapter = new MessageAdapter(ChatBox.this, listChat);
                    recyclerView.setAdapter(messageAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
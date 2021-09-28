package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class chatBox extends AppCompatActivity {
    TextView friend_name;
    FirebaseUser firebaseUser;
    DatabaseReference reference;
    Intent intent;

    RecyclerView recyclerView;
    EditText editText;
    Button send;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        friend_name = (TextView) findViewById(R.id.friend_name);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("user").child(firebaseUser.getUid());
        image = (ImageView)findViewById(R.id.pic);
        recyclerView = findViewById(R.id.chat_box);
        editText = findViewById(R.id.input_text);
        send = findViewById(R.id.send);
        intent  = getIntent();
        String name = intent.getStringExtra("user");
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = editText.getText().toString();
                if(!msg.equals("")){
                    sendMessage(firebaseUser.getUid(),name,msg)
                }
                editText.setText("");
            }
        });
        setContentView(R.layout.activity_chat_box);

        friend_name = (TextView) findViewById(R.id.friend_name);
        image.setImageResource(R.drawable.test);
        friend_name.setText(name);
    }
    public void toProfile(View v){
//        Intent intent = new Intent(getApplicationContext(),profile.class)
    }
    private void sendMessage(String sender,String receiver,String msg){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("sender",sender);
        hashMap.put("receiver",receiver);
        hashMap.put("msg",msg);
        reference.child("Chat").push().setValue(hashMap);

    }
}
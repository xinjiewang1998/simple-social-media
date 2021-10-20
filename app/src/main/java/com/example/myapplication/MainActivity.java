package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private TextView logoutButton;
    private TextView usernameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logoutButton = findViewById(R.id.logout_button);
        usernameTextView = findViewById(R.id.username);



    }


    @Override
    protected void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() == null) {
            startActivity(new Intent(MainActivity.this, StartActivity.class));
        } else {
            if(mAuth.getCurrentUser()!= null) {
                usernameTextView.setText(mAuth.getCurrentUser().getEmail());
            }
        }
    }





    public void onClick(View view) {
        if (view.getId() == R.id.logout_button) {
            mAuth = FirebaseAuth.getInstance();
            mAuth.signOut();
            startActivity(new Intent(MainActivity.this, StartActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
            Toast.makeText(MainActivity.this, "Logout succeed", Toast.LENGTH_SHORT).show();
        }
        if(view.getId() == R.id.message){
            Intent intent = new Intent(getApplicationContext(),MessageActivity.class);
            startActivity(intent);
        }
        if(view.getId() == R.id.my_post) {
            Intent intent = new Intent(getApplicationContext(),PostActivity.class);
            startActivity(intent);
        }
    }
}




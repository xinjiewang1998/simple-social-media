package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class StartActivity extends AppCompatActivity {

    private EditText usernameTextField;
    private EditText passwordTextField;

    private String username, password;

    FirebaseAuth mAuth;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        mAuth = FirebaseAuth.getInstance();
        Button loginButton = findViewById(R.id.login_button);
        Button registerButton = findViewById(R.id.register_button);
        usernameTextField = findViewById(R.id.user_name);
        passwordTextField = findViewById(R.id.password);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username = usernameTextField.getText().toString();
                password = passwordTextField.getText().toString();

                if (username.isEmpty()) {
                    usernameTextField.setError("Username is required");
                } else if (password.isEmpty()) {
                    passwordTextField.setError("Password is required");
                } else {
                    loginUser(username, password);
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = usernameTextField.getText().toString();
                password = passwordTextField.getText().toString();

                if (username.isEmpty()) {
                    usernameTextField.setError("Username is required");
                } else if (password.isEmpty()) {
                    passwordTextField.setError("Password is required");
                } else {
                    registerUser(username, password);
                }

            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(StartActivity.this, MainActivity.class));
        }
    }


    /**
     * register user
     * @param username the email
     * @param password the password
     */
    private void registerUser(String username, String password) {
        mAuth.createUserWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    // save to user db
                    reference = FirebaseDatabase.getInstance().getReference("User").child(user.getUid());
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("email", username);
                    hashMap.put("id", user.getUid());
                    // on complete make a toast
                    reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(StartActivity.this, "Register succeed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    /**
     * register user
     * @param username the email
     * @param password the password
     */
    private void loginUser(String username, String password) {
        mAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(StartActivity.this, MainActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                    Toast.makeText(StartActivity.this, "Login succeed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}



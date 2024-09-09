package com.example.hotel2;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText fullnameInput, emailInput, passwordInput;
    private Button registerButton, roomButton;
    private TextView registerText;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // Initialize Firebase Database
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Initialize Views
        fullnameInput = findViewById(R.id.fullname_input);
        emailInput = findViewById(R.id.email_input);
        passwordInput = findViewById(R.id.password_input);
        registerButton = findViewById(R.id.register_button);
        roomButton = findViewById(R.id.room_button);  // Add this line

        // Handle Register Button Click
        registerButton.setOnClickListener(v -> registerUser());

        // Handle Room Button Click
        roomButton.setOnClickListener(v -> {
            // Navigate to RoomActivity
            Intent intent = new Intent(RegisterActivity.this, RoomActivity.class);
            startActivity(intent);
        });
    }

    private void registerUser() {
        String fullname = fullnameInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        if (TextUtils.isEmpty(fullname) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Register user with Firebase Authentication
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Log and Toast on successful registration
                        Log.d("RegisterActivity", "User registered successfully.");
                        Toast.makeText(RegisterActivity.this, "Registration Successful!", Toast.LENGTH_SHORT).show();

                        // Store additional user details in Firebase Database
                        String userId = mAuth.getCurrentUser().getUid();
                        Map<String, Object> userProfile = new HashMap<>();
                        userProfile.put("fullname", fullname);
                        userProfile.put("email", email);

                        mDatabase.child("users").child(userId).setValue(userProfile)
                                .addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        Log.d("RegisterActivity", "User data stored successfully.");
                                        Toast.makeText(RegisterActivity.this, "Successfully Registered!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Log.d("RegisterActivity", "Failed to store user data.");
                                        Toast.makeText(RegisterActivity.this, "Failed to store user data. Please try again.", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        // Log and Toast on registration failure
                        Log.d("RegisterActivity", "Registration failed: " + task.getException().getMessage());
                        Toast.makeText(RegisterActivity.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}

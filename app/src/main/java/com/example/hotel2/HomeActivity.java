package com.example.hotel2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;


public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button btnLogin = findViewById(R.id.btn_login);
        Button btnRegister = findViewById(R.id.btn_register);
        Button btnRoom = findViewById(R.id.btn_room);

        btnLogin.setOnClickListener(v -> {
            // Navigate to LoginActivity
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
        });

        btnRegister.setOnClickListener(v -> {
            // Navigate to RegisterActivity
            startActivity(new Intent(HomeActivity.this, RegisterActivity.class));
        });

        btnRoom.setOnClickListener(v -> {
            // Navigate to RoomActivity
            startActivity(new Intent(HomeActivity.this, RoomActivity.class));
        });
    }
}

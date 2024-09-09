package com.example.hotel2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class RoomActivity extends AppCompatActivity {

    private Button bookNowButtonSingle;
    private Button bookNowButtonDouble;
    private Button bookNowButtonTriple;
    private Button bookNowButtonQuad;
    private ImageView menuButton;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        // Initialize the "Book Now" buttons for each room
        bookNowButtonSingle = findViewById(R.id.singleroom);
        bookNowButtonDouble = findViewById(R.id.doubleroom);
        bookNowButtonTriple = findViewById(R.id.tripleroom);
        bookNowButtonQuad = findViewById(R.id.quadroom);

        // Initialize the menu button
        menuButton = findViewById(R.id.menu_button);

        // Initialize the DrawerLayout
        drawerLayout = findViewById(R.id.drawer_layout);

        // Set up the button click listeners for each room
        bookNowButtonSingle.setOnClickListener(v -> {
            // Handle Single Room booking process
            Intent intent = new Intent(RoomActivity.this, SingleRoomActivity.class);
            startActivity(intent);
        });

        bookNowButtonDouble.setOnClickListener(v -> {
            // Handle Double Room booking process
            Intent intent = new Intent(RoomActivity.this, DoubleRoomActivity.class);
            startActivity(intent);
        });

        bookNowButtonTriple.setOnClickListener(v -> {
            // Handle Triple Room booking process
            Intent intent = new Intent(RoomActivity.this, TripleRoomActivity.class);
            startActivity(intent);
            // Add further logic for booking a Triple Room
        });

        bookNowButtonQuad.setOnClickListener(v -> {
            // Handle Quad Room booking process
            Intent intent = new Intent(RoomActivity.this, QuadRoomActivity.class);
            startActivity(intent);
            // Add further logic for booking a Quad Room
        });

        // Set up click listener for the menu button
        menuButton.setOnClickListener(v -> {
            // Handle menu button click to open the drawer
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }
}

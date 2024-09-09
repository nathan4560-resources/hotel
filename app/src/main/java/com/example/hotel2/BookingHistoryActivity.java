package com.example.hotel2;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hotel2.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BookingHistoryActivity extends AppCompatActivity {

    private ListView bookingListView;
    private DatabaseReference databaseReference;
    private List<String> bookingList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_history);

        // Initialize ListView and List
        bookingListView = findViewById(R.id.booking_list_view);
        bookingList = new ArrayList<>();

        // Initialize Firebase Auth and Database
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String userEmail = currentUser != null ? currentUser.getEmail().replace(".", "_") : "";

        databaseReference = FirebaseDatabase.getInstance().getReference("Bookings").child(userEmail);

        // Initialize Adapter
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, bookingList);
        bookingListView.setAdapter(adapter);

        // Fetch booking history
        fetchBookingHistory();
    }

    private void fetchBookingHistory() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                bookingList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    BookingDetails bookingDetails = snapshot.getValue(BookingDetails.class);
                    if (bookingDetails != null) {
                        String bookingInfo = "Room ID: " + bookingDetails.roomId + "\n" +
                                "Check-in: " + bookingDetails.checkInDate + "\n" +
                                "Check-out: " + bookingDetails.checkOutDate + "\n" +
                                "Guests: " + bookingDetails.guestCount;
                        bookingList.add(bookingInfo);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(BookingHistoryActivity.this, "Failed to load booking history", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Define a model class for booking details (if not already defined elsewhere)
    public static class BookingDetails {
        public String roomId;
        public String checkInDate;
        public String checkOutDate;
        public String guestCount;

        public BookingDetails() {
            // Default constructor required for calls to DataSnapshot.getValue(BookingDetails.class)
        }

        public BookingDetails(String roomId, String checkInDate, String checkOutDate, String guestCount) {
            this.roomId = roomId;
            this.checkInDate = checkInDate;
            this.checkOutDate = checkOutDate;
            this.guestCount = guestCount;
        }
    }
}

package com.example.hotel2;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class TripleRoomActivity extends AppCompatActivity {

    private EditText checkInDate, checkOutDate, guestCountInput, emailInput, passwordInput;
    private Button proceedToLoginButton;
    private DatabaseReference databaseReference;

    private static final String ROOM_ID = "103";  // Setting the room ID to 103 for Triple Room

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_triple_room);

        // Initialize Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference("Bookings");

        // Initialize views
        checkInDate = findViewById(R.id.check_in_date);
        checkOutDate = findViewById(R.id.check_out_date);
        guestCountInput = findViewById(R.id.guest_count_input);
        emailInput = findViewById(R.id.email_input);
        passwordInput = findViewById(R.id.password_input);
        proceedToLoginButton = findViewById(R.id.book_now_button);

        // Set up the date pickers
        checkInDate.setOnClickListener(v -> showDatePickerDialog(checkInDate));
        checkOutDate.setOnClickListener(v -> showDatePickerDialog(checkOutDate));

        // Set up the button click listener
        proceedToLoginButton.setOnClickListener(v -> saveBookingToFirebase());
    }

    private void showDatePickerDialog(final EditText dateInput) {
        // Get current date
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create DatePickerDialog and show it
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                TripleRoomActivity.this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Display the selected date in the EditText
                    String date = (selectedMonth + 1) + "/" + selectedDay + "/" + selectedYear;
                    dateInput.setText(date);
                }, year, month, day);
        datePickerDialog.show();
    }

    private void saveBookingToFirebase() {
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();  // You might not save the password for security reasons, just for login purposes.
        String checkIn = checkInDate.getText().toString();
        String checkOut = checkOutDate.getText().toString();
        String guestCount = guestCountInput.getText().toString();

        if (email.isEmpty() || password.isEmpty() || checkIn.isEmpty() || checkOut.isEmpty() || guestCount.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a BookingDetails object with the room ID set to 103
        BookingDetails bookingDetails = new BookingDetails(ROOM_ID, checkIn, checkOut, guestCount);

        // Save booking details under the provided email in Firebase
        databaseReference.child(email.replace(".", "_")).child(ROOM_ID).setValue(bookingDetails)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Booking saved successfully
                        Toast.makeText(TripleRoomActivity.this, "Booking saved successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        // Handle failure
                        Toast.makeText(TripleRoomActivity.this, "Failed to save booking", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Define a model class for booking details
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

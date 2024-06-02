package com.example.purpleshirt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class AttendActivity extends AppCompatActivity {

    private EditText editTextCode;
    private Button buttonSubmit;
    private ImageButton ib;

    private DatabaseReference codeRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attend);

        String name = getIntent().getStringExtra("leadster");

        editTextCode = findViewById(R.id.editTextCode);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        ib= findViewById(R.id.back);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        codeRef = database.getReference("generated_code");

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitCode();
            }
        });

        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                home();
            }
        });
    }



    private void submitCode() {
        final String enteredCode = editTextCode.getText().toString().trim();

        codeRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String generatedCode = dataSnapshot.getValue(String.class);

                // Compare generated code with the code that the user entered, if it's a mach the app will mark attendence
                if (generatedCode != null && generatedCode.equals(enteredCode)) {
                    markAttendance();
                } else {
                    showToast("Invalid code. Please try again.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                showToast("Failed to retrieve code from Firebase.");
            }
        });
    }

        private void markAttendance() {
            final String userNickname = getIntent().getStringExtra("leadster");

            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(userNickname);

            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    leadster user = dataSnapshot.getValue(leadster.class);

                    // Increment the count and add true to the attendance list
                    if (user != null) {
                        int currentCount = user.getCount();
                        currentCount++;
                        user.setCount(currentCount);
                        user.getAttendance().add(true);

                        // Update the user's data in the database
                        dataSnapshot.getRef().setValue(user);

                        showToast("Attendance marked successfully!");
                        home();
                    } else {
                        showToast("Failed to mark attendance. User data not found.");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    showToast("Failed to mark attendance. Database error.");
                }
            });
        }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    public void home(){
        finish();
    }

}

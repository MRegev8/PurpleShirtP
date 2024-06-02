package com.example.purpleshirt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class SetCodeActivity extends AppCompatActivity {

    private TextView GeneratedCode;
    private Button buttonGenerateCode;
    private ImageButton ib;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_code);

        GeneratedCode = findViewById(R.id.GeneratedCode);
        buttonGenerateCode = findViewById(R.id.buttonGenerateCode);
        ib = findViewById(R.id.back);

        // Check if code already exists in Firebase
        DatabaseReference codeRef = FirebaseDatabase.getInstance().getReference("generated_code");
        codeRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String existingCode = dataSnapshot.getValue(String.class);
                if (existingCode != null) {
                    GeneratedCode.setText(existingCode);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(SetCodeActivity.this, "Failed to retrieve existing code from Firebase.", Toast.LENGTH_SHORT).show();
            }
        });

        // generating new code
        buttonGenerateCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateAndStoreCode();
            }
        });

        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                home();
            }
        });
    }


    private void generateAndStoreCode() {
        // Generate a 6 digits random code
        String code = generateCode();

        GeneratedCode.setText(code);

        DatabaseReference codeRef = FirebaseDatabase.getInstance().getReference("generated_code");
        codeRef.setValue(code);

        Toast.makeText(this, "Code generated and stored successfully!", Toast.LENGTH_SHORT).show();
    }

    private String generateCode() {
        Random random = new Random();
        int code = random.nextInt(900000) + 100000; // Generates a random number between 100000 and 999999
        return String.valueOf(code);
    }
    public void home(){
        finish();
    }
}

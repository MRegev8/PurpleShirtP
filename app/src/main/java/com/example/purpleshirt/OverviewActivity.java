package com.example.purpleshirt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OverviewActivity extends AppCompatActivity {

    private ImageButton ib;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        String name = getIntent().getStringExtra("leadster");
        // Initialize ListView
        ListView listViewUsers = findViewById(R.id.listViewUsers);

        // Retrieve users from Firebase and display in ListView
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("Users");
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> userList = new ArrayList<>();
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String fullName = userSnapshot.child("fullName").getValue(String.class);
                    userList.add(fullName);
                }

                // Display user list in ListView
                ArrayAdapter<String> adapter = new ArrayAdapter<>(OverviewActivity.this, android.R.layout.simple_list_item_1, userList);
                listViewUsers.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(OverviewActivity.this, "Failed to retrieve user list.", Toast.LENGTH_SHORT).show();
            }
        });

        // Handle item click on ListView
        listViewUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedUser = (String) parent.getItemAtPosition(position);
                // Open DetailsActivity and pass selected user's details
                openDetailsActivity(selectedUser);
            }
        });

        ib= findViewById(R.id.back);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                home();
            }
        });

    }

    private void openDetailsActivity(String fullName) {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("Users");
        usersRef.orderByChild("fullName").equalTo(fullName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        String nickname = userSnapshot.child("nickName").getValue(String.class);
                        // Open UserActivity and pass selected user's nickname
                        Intent intent = new Intent(OverviewActivity.this, UserActivity.class);
                        intent.putExtra("leadster", nickname);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(OverviewActivity.this, "User not found.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(OverviewActivity.this, "Failed to retrieve user details.", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void home(){
        finish();
    }
}

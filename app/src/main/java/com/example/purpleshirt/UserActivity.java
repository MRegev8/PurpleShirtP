package com.example.purpleshirt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import javax.annotation.Nonnull;

public class UserActivity extends AppCompatActivity {
    private TextView text;

    private ImageButton ib;
    private static final String TAG = "UserActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        String name = getIntent().getStringExtra("leadster");

        text = findViewById(R.id.deatales);
        ib= findViewById(R.id.back);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users/" + name);

        /*myRef.addValueEventListener(new ValueEventListener()); {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    String uname = childSnapshot.child("leadster").child("nickName").getValue(String.class);
                    if (uname == name) {
                        eshk = childSnapshot.child("leadster").child("eshkol").getValue(String.class);
                        city = childSnapshot.child("leadster").child("city").getValue(String.class);
                        //grade =childSnapshot.child("leadster").child("grade").getValue(int.class);


                        text.setText("בית ספר:" + eshk + " עיר:" + city);

                    }
                }
                //leadster Object value = dataSnapshot.getValue(leadster.class);

                /*String value = dataSnapshot.getValue (String.class);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            };
        }*/
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@Nonnull DataSnapshot dataSnapshot) {
                leadster value = dataSnapshot.getValue(leadster.class);
                if (value != null) {
                    text.setText(value.toString());
                } else {
                    Toast.makeText(UserActivity.this, "User not found.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@Nonnull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                home();
            }
        });
    }
    public void home(){
        finish();
    }
}
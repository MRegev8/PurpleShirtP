package com.example.purpleshirt;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";
    private Button attendens, forum, deats;
    private ImageButton back;
    private LinearLayout messagesLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        String name = getIntent().getStringExtra("leadster");

        attendens = findViewById(R.id.attend);
        forum = findViewById(R.id.forum);
        deats = findViewById(R.id.user);
        back = findViewById(R.id.back);
        messagesLayout = findViewById(R.id.messagesLayout);

        deats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deats(name);
            }
        });
        attendens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attend(name);
            }
        });

        forum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openForum(name);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        loadMessages();
    }

    private void loadMessages() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference projectsRef = database.getReference("projects");

        projectsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                messagesLayout.removeAllViews();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Project project = snapshot.getValue(Project.class);
                    if (project != null) {
                        addMessageToLayout(project);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    private void addMessageToLayout(Project project) {
        TextView textView = new TextView(this);
        textView.setText(project.getUsername() + ": " + project.getMessage());
        textView.setTextSize(16);
        textView.setPadding(8, 8, 8, 8);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUserActivity(project.getUsername());
            }
        });
        messagesLayout.addView(textView);
    }

    public void deats(String name){
        Intent intent = new Intent(this, UserActivity.class);
        intent.putExtra("leadster", name);
        startActivity(intent);
    }

    public void attend(String name){
        Intent intent = new Intent(this, AttendActivity.class);
        intent.putExtra("leadster", name);
        startActivity(intent);
    }

    public void openForum(String name){
        Intent intent = new Intent(this, ForumActivity.class);
        intent.putExtra("leadster", name);
        startActivity(intent);
    }

    public void openUserActivity(String username) {
        Intent intent = new Intent(this, UserActivity.class);
        intent.putExtra("leadster", username);
        startActivity(intent);
    }

    public void login() {
        finish();
    }
}

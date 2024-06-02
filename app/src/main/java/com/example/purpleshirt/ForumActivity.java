package com.example.purpleshirt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ForumActivity extends AppCompatActivity {

    private EditText messageEditText;
    private Button publishButton;
    private String username;
    private ImageButton ib;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);

        username = getIntent().getStringExtra("leadster");

        messageEditText = findViewById(R.id.messageEditText);
        publishButton = findViewById(R.id.publishButton);
        ib = findViewById(R.id.back);


        publishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publishMessage();
            }
        });
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                home();
            }
        });
    }

    private void publishMessage() {
        String message = messageEditText.getText().toString().trim();
        if (message.isEmpty()) {
            Toast.makeText(this, "Message cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference projectsRef = database.getReference("projects");

        String projectId = projectsRef.push().getKey();
        Project project = new Project(username, message);
        projectsRef.child(projectId).setValue(project);

        Toast.makeText(this, "Message published", Toast.LENGTH_SHORT).show();
        messageEditText.setText("");
    }
    public void home(){
        finish();
    }
}

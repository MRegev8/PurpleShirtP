package com.example.purpleshirt;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.purpleshirt.AdminHomeActivity;
import com.example.purpleshirt.HomeActivity;
import com.example.purpleshirt.SignUpActivity;
import com.example.purpleshirt.leadster;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import javax.annotation.Nonnull;

public class LoginActivity extends AppCompatActivity {

    private EditText Uname, Password;
    private Button button, btnSignUp;
    private CheckBox checkBoxRememberMe;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //SharedPreferences
        sharedPreferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);

        // Initialize views and perform further setup
        initViews();

        boolean rememberMe = sharedPreferences.getBoolean("rememberMe", false);
        if (rememberMe) {
            // If "remember me" is checked, automatically log in
            String savedUsername = sharedPreferences.getString("username", "");
            String savedPassword = sharedPreferences.getString("password", "");
            if (!savedUsername.isEmpty() && !savedPassword.isEmpty()) {
                login(savedUsername, savedPassword);
            }
        }
    }

    private void login(String username, String password) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("rememberMe", checkBoxRememberMe.isChecked());
        editor.putString("username", username);
        editor.putString("password", password);
        editor.apply();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users/" + username);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@Nonnull DataSnapshot dataSnapshot) {
                leadster value = dataSnapshot.getValue(leadster.class);
                if (value != null) {
                    if ("מוביל".equals(value.getRole())) {
                        homeA(value.getNickName());
                    } else {
                        home(value.getNickName());
                    }
                }
            }

            @Override
            public void onCancelled(@Nonnull DatabaseError error) {
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
    }

    public void initViews() {
        Uname = findViewById(R.id.editTextUsername);
        Password = findViewById(R.id.editTextPassword);
        btnSignUp = findViewById(R.id.reg_sign_up);
        button = findViewById(R.id.reg_sign_in);
        checkBoxRememberMe = findViewById(R.id.checkBox);


        boolean rememberMe = sharedPreferences.getBoolean("rememberMe", false);
        checkBoxRememberMe.setChecked(rememberMe);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = Uname.getText().toString();
                String password = Password.getText().toString();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please fill all the fields.", Toast.LENGTH_SHORT).show();
                    return;
                }

                login(username, password);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }

    public void signUp() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    public void home(String nn) {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("leadster", nn);
        startActivity(intent);
    }

    public void homeA(String nn) {
        Intent intent = new Intent(this, AdminHomeActivity.class);
        intent.putExtra("leadster", nn);
        startActivity(intent);
    }
}

package com.example.purpleshirt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    private EditText Uname,NiName, Password,school,city,phone,day,month,year;
    private Button button,backBtn;
    private RadioGroup radioGroup;
    int g;
    boolean grade;


    /*private FirebaseDatabase db= FirebaseDatabase.getInstance();
    private DatabaseReference root=db.getReference().child("Users");*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        button= findViewById(R.id.reg_sign_up);
        backBtn=findViewById(R.id.retorn);
        Uname= findViewById(R.id.Username2);
        NiName= findViewById(R.id.nickname);
        school=findViewById(R.id.school);
        city=findViewById(R.id.city);
        phone=findViewById(R.id.phone);
        Password=findViewById(R.id.Password2);
        day=findViewById(R.id.day);
        month=findViewById(R.id.month);
        year=findViewById(R.id.year);
        radioGroup = findViewById(R.id.radioGroup);
        grade=false;

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                grade = true;
                if (checkedId == R.id.radioButton0) {
                    g = 0;
                } else if (checkedId == R.id.radioButton1) {
                    g = 1;
                } else if (checkedId == R.id.radioButton2) {
                    g = 2;
                }
            }
        });



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name= Uname.getText().toString();
                String niname= NiName.getText().toString();
                String Pass= Password.getText().toString();
                String scho= school.getText().toString();
                String cit= city.getText().toString();
                String phon= phone.getText().toString();
                String d= day.getText().toString();
                String m= month.getText().toString();
                String y= year.getText().toString();



                if(name.length()>0&&Pass.length()>0&&scho.length()>0&&cit.length()>0&&phon.length()>0&&grade==true&&d.length()>0&&m.length()>0&&y.length()>0){
                    if(niname.equals(""))niname=name;

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("Users/"+niname);

                    Leador admin;
                    leadster lead;

                    if ("27269".equals(Pass)) {     // Creating an admin user (Leador)
                        if (niname.length() > 0) {
                            admin = new Leador(name, niname, Pass, scho, cit, phon, g, d, m, y);
                        } else {
                            admin = new Leador(name, Pass, scho, cit, phon, g, d, m, y);
                        }
                        myRef.setValue(admin);
                        homeA(admin.getNickName());
                    } else {       // Creating a regular user (leadster)
                        if (niname.length() > 0) {
                            lead = new leadster(name, niname, Pass, scho, cit, phon, g, d, m, y);
                        } else {
                            lead = new leadster(name, Pass, scho, cit, phon, g, d, m, y);
                        }
                        myRef.setValue(lead);
                        home(lead.getNickName());
                    }


                }
                else Toast.makeText(SignUpActivity.this, "Please fill all the fields.", Toast.LENGTH_SHORT).show();
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIp();
            }
        });
    }
    public void signIp(){
        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);
    }
    public void home(String nn){
        Intent intent=new Intent(this,HomeActivity.class);
        intent.putExtra("leadster", nn);
        startActivity(intent);


    }
        public void homeA(String nn){
            Intent intent=new Intent(this,AdminHomeActivity.class);
            intent.putExtra("leadster", nn);
            startActivity(intent);
    }
}
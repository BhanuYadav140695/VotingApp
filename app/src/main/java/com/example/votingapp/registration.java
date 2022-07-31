package com.example.votingapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class registration extends AppCompatActivity {
    EditText name;
    EditText emailid;
    EditText phonenumber ;
    EditText passcode ;

    Button register;
    FirebaseAuth mAuth;
    FirebaseFirestore FFstore;
    DatabaseReference databaseReference;
    String UserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mAuth = FirebaseAuth.getInstance();
        FFstore = FirebaseFirestore.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();


        name = findViewById(R.id.Name);
        emailid = findViewById(R.id.emailid);
        phonenumber = findViewById(R.id.PhoneNumber);
        passcode = findViewById(R.id.password);
//        login = findViewById(R.id.Login);
        register = findViewById(R.id.Register2);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterNewuser();
            }
        });


    }

    private void RegisterNewuser() {

        String UserEmail = emailid.getText().toString().trim();
        String Password = passcode.getText().toString().trim();
        String FullName = name.getText().toString().trim();
        String phone = phonenumber.getText().toString().trim();
        String VotingStats = "";

        mAuth.createUserWithEmailAndPassword(UserEmail ,Password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                UserID = mAuth.getCurrentUser().getUid();
                databaseReference.child("Users").child(UserID).child("VotingStatus").setValue("No");
                startActivity(new Intent(registration.this , LoginActivity.class));
                Log.d("TAG","User Registration Successfull ");
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG","User Registration Failed");
            }
        });



    }
}
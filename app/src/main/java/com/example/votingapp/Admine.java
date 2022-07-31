package com.example.votingapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Admine extends AppCompatActivity {


    DatabaseReference databaseReference;
    TextView Vot1,Vot2,Vot3,Vot4;
    FirebaseAuth mAuth;
    int Counter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admine);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Voting");

        Vot1 = findViewById(R.id.CandidateVots);
        Vot2 = findViewById(R.id.Candidate1Vots);
        Vot3 = findViewById(R.id.Candidate2Vots);
        Vot4 = findViewById(R.id.Candidate3Vots);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String CanVot1 = snapshot.child("Candidate1").getValue().toString();
                String CanVot2 = snapshot.child("Candidate2").getValue().toString();
                String CanVot3 = snapshot.child("Candidate3").getValue().toString();
                String CanVot4 = snapshot.child("Candidate4").getValue().toString();

                Vot1.setText(CanVot1);
                Vot2.setText(CanVot2);
                Vot3.setText(CanVot3);
                Vot4.setText(CanVot4);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    public void onBackPressed() {
        Counter++;
        //super.onBackPressed();
        if(Counter == 2){
            startActivity(new Intent(Admine.this , LoginActivity.class));
            mAuth.signOut();
            finish();

        }

    }
}
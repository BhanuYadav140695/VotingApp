package com.example.votingapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    int radioId = 0;
    int Count = 0;
    String UserId;
    RadioGroup radioGroup;
    RadioButton radioButton;
    Button VotButton;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    DatabaseReference databaseReference , databaseReference1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference1 = FirebaseDatabase.getInstance().getReference("Users");


        radioGroup = findViewById(R.id.radioGroup);
        VotButton = findViewById(R.id.vote);

        radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);

        UserId = mAuth.getCurrentUser().getUid();




        VotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Log.d("TAG", "Voting Done For " + UserId);

                UpDateData();

                databaseReference1.child(UserId).child("VotingStatus").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        databaseReference1.child(UserId).child("VotingStatus").setValue("Yes").addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d("TAG","Value Is Change");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });

                        Log.d("TAG",""+snapshot.getValue());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Your Vot is Done");
                builder.setTitle("Voting is Completed ");
                builder.setCancelable(false);
                builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mAuth.signOut();
                        startActivity(new Intent(MainActivity.this , LoginActivity.class));
                    }
                });

                builder.create();
                builder.show();


            }
        }

        );


    }

    private void UpDateData() {

        databaseReference.child("Voting").child(radioButton.getText().toString())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Log.d("TAG" , ""+snapshot.getKey());
                        Log.d("TAG" , ""+snapshot.getValue());

                        int Value = Integer.parseInt(snapshot.getValue().toString())+1;

                        databaseReference.child("Voting").child(radioButton.getText().toString())
                                .setValue(Value).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Log.d("TAG" , "Update Successful");
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d("TAG" , "Something Wents Wrong");
                                    }
                                });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }

    public void CheckRadioButton(View view) {
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
    }



}
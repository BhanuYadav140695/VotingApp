package com.example.votingapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    TextView signup;
    TextView login;
    EditText emailId;
    EditText password;
    Button loginbutton;
    TextView newuser;
    String UserId;
    boolean checkfieldisfull = false;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        signup = findViewById(R.id.Register1);
        login = findViewById(R.id.Login);
        emailId = findViewById(R.id.User_Name);
        password = findViewById(R.id.pascode);
        loginbutton = findViewById(R.id.Login);
        newuser = findViewById(R.id.Register1);


        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, registration.class);
                startActivity(intent);

            }
        });

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignIn();
            }
        });


        newuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, registration.class);
                startActivity(intent);
            }
        });


    }

    private void SignIn() {


        String EmailId = emailId.getText().toString().trim();
        String Password = password.getText().toString().trim();

        mAuth.signInWithEmailAndPassword(EmailId, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    UserId = mAuth.getCurrentUser().getUid();


                    switch(UserId) {

                        case "TzYAgWS3uychNwNw4a9RK7otq9n2":
                            startActivity(new Intent(LoginActivity.this , Admine.class));
                            break;

                        default:
                            databaseReference.child("Users").child(UserId).child("VotingStatus").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                    if (snapshot.getValue().equals("No")) {
                                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    } else {

                                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                        builder.setMessage("Your Vot is Done");
                                        builder.setTitle("Voting is Completed ");
                                        builder.setCancelable(false);
                                        builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                mAuth.signOut();
                                            }
                                        });

                                        builder.create();
                                        builder.show();

                                    }

                                    Log.d("TAG", "" + snapshot.getValue());
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            break;


                    }


                    Log.d("TAG", "Login Successefull");
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setMessage("You Enter Wrong Password and Email Id");
                    builder.setTitle("Incorect Credentials");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }

                    });

                    builder.create();
                    builder.show();
                }
            }
        });


    }

    public boolean isFieldEmpty() {

        if (emailId.toString().length() == 0) {
            emailId.setError("This Field is should not be Empty");
            return false;
        }

        if (password.toString().length() == 0) {
            password.setError("This Field Should not be Empty");
            return false;
        } else if (password.toString().length() < 8) {
            password.setError("Password must be minimum 8 characters");
            return false;
        }

        return true;
    }


}
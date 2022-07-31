package com.example.votingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.WindowManager;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splac_screen);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

            getSupportActionBar().hide();
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

            Thread thread =  new Thread(){
                public  void run(){
                    try {

                        sleep(4000);

                    }catch (Exception e){
                        e.getStackTrace();
                    }finally{
                        Intent intent = new Intent(SplashScreen.this,LoginActivity.class);
                        startActivity(intent);
                    }
                }
            };thread.start();
    }
}
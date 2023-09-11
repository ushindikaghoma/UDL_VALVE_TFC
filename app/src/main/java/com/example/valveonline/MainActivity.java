package com.example.valveonline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.valveonline.Home.GetStatedActivity;
import com.example.valveonline.Home.HomeActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.getSupportActionBar().hide();

       Thread myThead = new Thread()
       {
           @Override
           public void run() {
               try {
                   sleep(5000);
                   startActivity(new Intent(getApplicationContext(), GetStatedActivity.class));
                   finish();
               } catch (InterruptedException e) {
                   throw new RuntimeException(e);
               }
               super.run();
           }
       };
       myThead.start();
    }
}
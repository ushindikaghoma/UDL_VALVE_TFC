package com.example.valveonline.Horaire;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.valveonline.R;

public class JourSemaine extends AppCompatActivity {

    CardView cardViewLundi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jour_semaine);

        this.getSupportActionBar().setTitle("Jour de la semaine");

        cardViewLundi = findViewById(R.id.cardview_lundi);
        cardViewLundi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ListeCoursDuJour.class)
                        .putExtra("jour","Lundi"));
            }
        });


    }
}
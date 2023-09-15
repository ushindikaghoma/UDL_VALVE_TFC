package com.example.valveonline.Horaire;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.valveonline.R;

public class JourSemaine extends AppCompatActivity {

    CardView cardViewLundi;
    CardView cardViewMardi;
    CardView cardViewMercredi;
    CardView cardViewJeudi;
    CardView cardViewVendredi;
    CardView cardViewSamedi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jour_semaine);

        this.getSupportActionBar().setTitle("Jour de la semaine");

        cardViewLundi = findViewById(R.id.cardview_lundi);
        cardViewMardi = findViewById(R.id.cardview_mardi);
        cardViewMercredi = findViewById(R.id.cardview_mercredi);
        cardViewJeudi = findViewById(R.id.cardview_jeudi);
        cardViewVendredi = findViewById(R.id.cardview_vendredi);
        cardViewSamedi = findViewById(R.id.cardview_samedi);
        cardViewLundi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ListeCoursDuJour.class)
                        .putExtra("jour","LUNDI"));
            }
        });
        cardViewMardi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ListeCoursDuJour.class)
                        .putExtra("jour","MARDI"));
            }
        });
        cardViewMercredi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ListeCoursDuJour.class)
                        .putExtra("jour","MERCREDI"));
            }
        });
        cardViewJeudi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ListeCoursDuJour.class)
                        .putExtra("jour","JEUDI"));
            }
        });
        cardViewVendredi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ListeCoursDuJour.class)
                        .putExtra("jour","VENDREDI"));
            }
        });
        cardViewSamedi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ListeCoursDuJour.class)
                        .putExtra("jour","SAMEDI"));
            }
        });


    }
}
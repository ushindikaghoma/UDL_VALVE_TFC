package com.example.valveonline.Horaire;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.valveonline.R;

public class ListeCoursDuJour extends AppCompatActivity {

    String nom_jour;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_cours_du_jour);

        nom_jour = getIntent().getStringExtra("jour");

        this.getSupportActionBar().setTitle(nom_jour);
    }
}
package com.example.valveonline.Etudiant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.valveonline.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ListeEtudiant extends AppCompatActivity {

    FloatingActionButton ajouter_nouveau_etudiant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_etudiant);

        this.getSupportActionBar().setTitle("Liste des étudiants");

        ajouter_nouveau_etudiant = findViewById(R.id.ajouter_etudiant_btn);

        ajouter_nouveau_etudiant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), NouveauEtudiantActivity.class));
            }
        });

    }
}
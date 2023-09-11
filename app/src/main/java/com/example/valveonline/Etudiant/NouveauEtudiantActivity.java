package com.example.valveonline.Etudiant;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.valveonline.R;

public class NouveauEtudiantActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nouveau_etudiant);

        this.getSupportActionBar().setTitle("Nouvel étudiant");
    }
}
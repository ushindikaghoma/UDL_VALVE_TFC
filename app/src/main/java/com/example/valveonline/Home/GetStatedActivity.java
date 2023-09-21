package com.example.valveonline.Home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.valveonline.Infos.ListeInfosActivity;
import com.example.valveonline.Login.LoginActivity;
import com.example.valveonline.R;

public class GetStatedActivity extends AppCompatActivity {

    CardView cardViewEtudiant, cardViewVisitweur;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_stated);

        this.getSupportActionBar().setTitle("Mode d'accès");

        cardViewEtudiant = findViewById(R.id.card_etudiant_staff);
        cardViewVisitweur = findViewById(R.id.card_visiteur);

        cardViewEtudiant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });

        cardViewVisitweur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ListeInfosActivity.class)
                        .putExtra("visitor_mode",true));
            }
        });
    }
}
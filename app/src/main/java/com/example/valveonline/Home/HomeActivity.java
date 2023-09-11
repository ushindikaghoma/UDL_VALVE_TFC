package com.example.valveonline.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.valveonline.Etudiant.ListeEtudiant;
import com.example.valveonline.Horaire.JourSemaine;
import com.example.valveonline.R;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity {

    CardView cardViewHoraire;
    private NavigationView navigationView;
    private ActionBarDrawerToggle mToogle;
    private DrawerLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        this.getSupportActionBar().setTitle("Menu principal");


        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        //View hView = navigationView.inflateHeaderView(R.layout.header_drawer);


        View hView = navigationView.inflateHeaderView(R.layout.header_drawer);
        TextView txt_nom_ustisiateur = hView.findViewById(R.id.txt_nom_ustisiateur);

        txt_nom_ustisiateur.setText("JEANSSY");

        TextView txt_mis_ajour = navigationView.findViewById(R.id.txt_date_mis_ajour);
        txt_mis_ajour.setText("DERNIER MIS A JOUR : 2023-09-11");
//
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        layout = (DrawerLayout) findViewById(R.id.main_layout);
        mToogle = new ActionBarDrawerToggle(this, layout, R.string.open, R.string.close);
        layout.addDrawerListener(mToogle);
        mToogle.syncState();


        cardViewHoraire = findViewById(R.id.card_horaire);

        cardViewHoraire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), JourSemaine.class));
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();

                if (id == R.id.action_etudiant)
                {
                    startActivity(new Intent(getApplicationContext(), ListeEtudiant.class));
                }

                return false;
            }
        });
    }

    @SuppressLint("WrongConstant")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (mToogle.onOptionsItemSelected(item)) {
            return true;
        }

        layout.closeDrawer(Gravity.START, true);
        return super.onOptionsItemSelected(item);
    }
}
package com.example.valveonline.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.valveonline.Etudiant.ListeEtudiant;
import com.example.valveonline.Faculte.NouvelleFaculteActivity;
import com.example.valveonline.Horaire.JourSemaine;
import com.example.valveonline.Horaire.PublierHoraireActivity;
import com.example.valveonline.Infos.ListeInfosActivity;
import com.example.valveonline.Infos.PublierInfosActivity;
import com.example.valveonline.Promotion.NouvellePromotionActivity;
import com.example.valveonline.R;
import com.example.valveonline.Utilisateur.NouvelUtilisateurActivity;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity {

    CardView cardViewHoraire, cardViewInfos;
    private NavigationView navigationView;
    private ActionBarDrawerToggle mToogle;
    private DrawerLayout layout;
    String pref_nom_utilisateur, pref_niveau_utilisateur,
            pref_fonction_utilisateur, pref_designation_utilisateur,
            pref_code_faculte, pref_code_promotion;
    SharedPreferences preferences;
    public static SharedPreferences.Editor editor;

    MenuItem item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        this.getSupportActionBar().setTitle("Menu principal");

        preferences = getSharedPreferences("maPreference", MODE_PRIVATE);
        editor = preferences.edit();

        pref_nom_utilisateur = preferences.getString("pref_nom_user","");
        pref_niveau_utilisateur = preferences.getString("pref_niveau_user","");
        pref_code_faculte = preferences.getString("pref_code_faculte","");
        pref_code_promotion = preferences.getString("pref_code_promotion","");




        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        //View hView = navigationView.inflateHeaderView(R.layout.header_drawer);


        View hView = navigationView.inflateHeaderView(R.layout.header_drawer);
        TextView txt_nom_ustisiateur = hView.findViewById(R.id.txt_nom_ustisiateur);


        txt_nom_ustisiateur.setText(pref_nom_utilisateur);

        TextView txt_mis_ajour = navigationView.findViewById(R.id.txt_date_mis_ajour);
        txt_mis_ajour.setText("DERNIER MIS A JOUR : 2023-09-11");
//
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        layout = (DrawerLayout) findViewById(R.id.main_layout);
        mToogle = new ActionBarDrawerToggle(this, layout, R.string.open, R.string.close);
        layout.addDrawerListener(mToogle);
        mToogle.syncState();

        item = findViewById(R.id.action_publier_horaire);


        cardViewHoraire = findViewById(R.id.card_horaire);
        cardViewInfos = findViewById(R.id.card_infos);

        if (pref_niveau_utilisateur.equals("Etudiant"))
        {
            hideItem(navigationView);
        }


        cardViewHoraire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), JourSemaine.class));
            }
        });

        cardViewInfos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), ListeInfosActivity.class)
                        .putExtra("visitor_mode",false));
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();

                if (id == R.id.action_etudiant)
                {
                    startActivity(new Intent(getApplicationContext(), ListeEtudiant.class));
                }else if (id == R.id.action_liste_faculte)
                {
                   startActivity(new Intent(getApplicationContext(), NouvelleFaculteActivity.class));
                }else if (id == R.id.action_liste_promotion)
                {
                    startActivity(new Intent(getApplicationContext(), NouvellePromotionActivity.class));
                }else if (id == R.id.action_publier_horaire)
                {
                    startActivity(new Intent(getApplicationContext(), PublierHoraireActivity.class)
                            .putExtra("type",""));
                }else if (id == R.id.action_liste_utilisateur)
                {
                    startActivity(new Intent(getApplicationContext(), NouvelUtilisateurActivity.class));
                }else if (id == R.id.action_publier_infos)
                {
                    startActivity(new Intent(getApplicationContext(), PublierInfosActivity.class)
                            .putExtra("type",""));
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

    private void hideItem(NavigationView _navigationView)
    {
        Menu nav_Menu = navigationView.getMenu();

        nav_Menu.findItem(R.id.action_etudiant).setVisible(false);
        nav_Menu.findItem(R.id.action_publier_infos).setVisible(false);
        nav_Menu.findItem(R.id.action_publier_horaire).setVisible(false);
        nav_Menu.findItem(R.id.action_liste_utilisateur).setVisible(false);
        nav_Menu.findItem(R.id.action_liste_cours).setVisible(false);
        nav_Menu.findItem(R.id.action_liste_faculte).setVisible(false);
        nav_Menu.findItem(R.id.action_liste_promotion).setVisible(false);
    }
}
package com.example.valveonline.Horaire;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.valveonline.Faculte.data.FaculteReponse;
import com.example.valveonline.Horaire.data.HoraireAdapter;
import com.example.valveonline.Horaire.data.HoraireRepository;
import com.example.valveonline.Horaire.data.HoraireResponse;
import com.example.valveonline.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListeCoursDuJour extends AppCompatActivity {

    String nom_jour;
    HoraireRepository horaireRepository;
    HoraireAdapter horaireAdapter;
    RecyclerView recyclerViewHoraire;
    ProgressBar progressBar;

    String pref_code_faculte, pref_code_promotion, jour;
    SharedPreferences preferences;
    public static SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_cours_du_jour);

        nom_jour = getIntent().getStringExtra("jour");

        recyclerViewHoraire = findViewById(R.id.horaire_liste);
        progressBar = findViewById(R.id.progress_horaire_dujour);

        horaireRepository = HoraireRepository.getInstance();
        horaireAdapter = new HoraireAdapter(this);

        preferences = getSharedPreferences("maPreference", MODE_PRIVATE);
        editor = preferences.edit();

        recyclerViewHoraire.setHasFixedSize(true);
        recyclerViewHoraire.setLayoutManager(new LinearLayoutManager(this));


        pref_code_faculte = preferences.getString("pref_code_faculte","");
        pref_code_promotion = preferences.getString("pref_code_promotion","");

        Toast.makeText(ListeCoursDuJour.this, ""+nom_jour, Toast.LENGTH_SHORT).show();

        this.getSupportActionBar().setTitle(nom_jour);

        LoadListeHoraire(progressBar, pref_code_faculte, pref_code_promotion,nom_jour);


    }

    public void LoadListeHoraire(ProgressBar progressBarLoadArticle, String codeFaculte, String codePromotion, String jour)
    {
        Call<List<HoraireResponse>> call_liste_article = horaireRepository.horaireConnexion().
                getHoraireParPromotion(codeFaculte, codePromotion,jour);
        progressBarLoadArticle.setVisibility(View.VISIBLE);
        call_liste_article.enqueue(new Callback<List<HoraireResponse>>() {
            @Override
            public void onResponse(Call<List<HoraireResponse>> call, Response<List<HoraireResponse>> response) {
                if (response.isSuccessful())
                {
                    progressBarLoadArticle.setVisibility(View.GONE);

                    List<HoraireResponse> list_local = new ArrayList<>();
                    for (int a = 0; a < response.body().size(); a++)
                    {
                        HoraireResponse liste_article =
                                new HoraireResponse (
                                        response.body().get(a).getCodeFaculte(),
                                        response.body().get(a).getCodePromotion(),
                                        response.body().get(a).getDesignationCours(),
                                        response.body().get(a).getCodeCours()
                                );


                        list_local.add(liste_article);
                    }
                    horaireAdapter.setList(list_local);
                    recyclerViewHoraire.setAdapter(horaireAdapter);

                }
            }

            @Override
            public void onFailure(Call<List<HoraireResponse>> call, Throwable t) {
                progressBarLoadArticle.setVisibility(View.GONE);
            }
        });
    }

}
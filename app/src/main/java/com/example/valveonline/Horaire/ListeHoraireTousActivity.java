package com.example.valveonline.Horaire;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.valveonline.Faculte.data.FaculteReponse;
import com.example.valveonline.Faculte.data.FaculteRepository;
import com.example.valveonline.Horaire.data.HoraireAdapter;
import com.example.valveonline.Horaire.data.HoraireRepository;
import com.example.valveonline.Horaire.data.HoraireResponse;
import com.example.valveonline.Promotion.data.PromotionRepository;
import com.example.valveonline.Promotion.data.PromotionResponse;
import com.example.valveonline.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListeHoraireTousActivity extends AppCompatActivity {

    ProgressBar progressBar, progressBarSpinner;
    FaculteRepository faculteRepository;
    ArrayAdapter arrayAdapterFaculte;
    ArrayAdapter arrayAdapterPromotion;
    PromotionRepository promotionRepository;
    Spinner code_faculte, code_promotion;
    HoraireRepository horaireRepository;
    HoraireAdapter horaireAdapter;
    RecyclerView recyclerViewHoraire;
    SearchView rechercheHoraireSearchView;
    public static List<HoraireResponse> list_local;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_horaire_tous);

        this.getSupportActionBar().setTitle("Horaire de toutes les facultés");

//        code_faculte = findViewById(R.id.horaire_spinner_faculte);
//        code_promotion = findViewById(R.id.horaire_spinner_promotion);
//        progressBar = findViewById(R.id.progress_horaire_dujour_);
        progressBarSpinner = findViewById(R.id.progress_horaire_spinner);
        recyclerViewHoraire = findViewById(R.id.horaire_liste);

        faculteRepository = FaculteRepository.getInstance();
        promotionRepository = PromotionRepository.getInstance();
        horaireRepository = HoraireRepository.getInstance();
        //horaireAdapter = new HoraireAdapter(this);

        recyclerViewHoraire.setHasFixedSize(true);
        recyclerViewHoraire.setLayoutManager(new LinearLayoutManager(this));

//        new AsyncLoadSpinner(progressBarSpinner, code_faculte, code_promotion).execute();
//
//
//        code_faculte.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                LoadListeHoraire(progressBar, code_faculte.getSelectedItem().toString(),
//                        code_promotion.getSelectedItem().toString());
//            }
//        });
//
//        code_promotion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                LoadListeHoraire(progressBar, code_faculte.getSelectedItem().toString(),
//                        code_promotion.getSelectedItem().toString());
//            }
//        });


        LoadListeHoraire(progressBarSpinner, this);

    }

    public void LoadListeHoraire(ProgressBar progressBarLoadArticle, Context context)
    {
        Call<List<HoraireResponse>> call_liste_article = horaireRepository.horaireConnexion().
                getHoraireTous();
        progressBarLoadArticle.setVisibility(View.VISIBLE);
        call_liste_article.enqueue(new Callback<List<HoraireResponse>>() {
            @Override
            public void onResponse(Call<List<HoraireResponse>> call, Response<List<HoraireResponse>> response) {
                if (response.isSuccessful())
                {
                    progressBarLoadArticle.setVisibility(View.GONE);

                     list_local = new ArrayList<>();
                    for (int a = 0; a < response.body().size(); a++)
                    {
                        HoraireResponse liste_article =
                                new HoraireResponse (
                                        response.body().get(a).getCodeFaculte(),
                                        response.body().get(a).getCodePromotion(),
                                        response.body().get(a).getDesignationCours(),
                                        response.body().get(a).getCodeCours(),
                                        response.body().get(a).getHeureDebut(),
                                        response.body().get(a).getHeureFin(),
                                        response.body().get(a).getDateHoraire(),
                                        response.body().get(a).getJourHoraire(),
                                        response.body().get(a).getIdHoraire()
                                );


                        list_local.add(liste_article);
                    }
                    horaireAdapter = new HoraireAdapter(ListeHoraireTousActivity.this, list_local);
                    recyclerViewHoraire.setAdapter(horaireAdapter);

                }
            }

            @Override
            public void onFailure(Call<List<HoraireResponse>> call, Throwable t) {
                progressBarLoadArticle.setVisibility(View.GONE);
            }
        });
    }

    public class AsyncLoadSpinner extends AsyncTask<Void, Void, Void>
    {
        ProgressBar load;
        Spinner faculte, promotion;

        public AsyncLoadSpinner(ProgressBar load, Spinner faculte, Spinner promotion) {
            this.load = load;
            this.faculte = faculte;
            this.promotion = promotion;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            load.setVisibility(View.VISIBLE);


        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            load.setVisibility(View.GONE);
        }

        @Override
        protected Void doInBackground(Void... voids) {

            LoadListeFaculte(faculte);
            LoadListePromotion(promotion);

            return null;
        }
    }

    public void LoadListeFaculte(Spinner spinner_liste_faculte)
    {
        Call<List<FaculteReponse>> call_liste_depot = faculteRepository.faculteConnexion().getListeFaculte();
        //loadDepot.setVisibility(View.VISIBLE);
        call_liste_depot.enqueue(new Callback<List<FaculteReponse>>() {
            @Override
            public void onResponse(Call<List<FaculteReponse>> call, Response<List<FaculteReponse>> response) {
                if (response.isSuccessful())
                {
                    //loadDepot.setVisibility(View.GONE);

                    List<String>list_local_depot = new ArrayList<>();
                    for (int a = 0; a < response.body().size(); a++)
                    {
                        FaculteReponse liste_faculte=
                                new FaculteReponse (
                                        response.body().get(a).getCodeFaculte(),
                                        response.body().get(a).getDesignationFaculte()
                                );

                        list_local_depot.add(response.body().get(a).getCodeFaculte());
                    }
                    arrayAdapterFaculte = new ArrayAdapter<>(ListeHoraireTousActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, list_local_depot);

                    spinner_liste_faculte.setAdapter(arrayAdapterFaculte);

                }
            }

            @Override
            public void onFailure(Call<List<FaculteReponse>> call, Throwable t) {
                //loadDepot.setVisibility(View.GONE);
            }
        });
    }

    public void LoadListePromotion(Spinner spinner_liste_promotione)
    {
        Call<List<PromotionResponse>> call_liste_depot = promotionRepository.promotionConnexion().getListePromotion();
        //loadDepot.setVisibility(View.VISIBLE);
        call_liste_depot.enqueue(new Callback<List<PromotionResponse>>() {
            @Override
            public void onResponse(Call<List<PromotionResponse>> call, Response<List<PromotionResponse>> response) {
                if (response.isSuccessful())
                {
                    //loadDepot.setVisibility(View.GONE);

                    List<String>list_local_depot = new ArrayList<>();
                    for (int a = 0; a < response.body().size(); a++)
                    {
                        PromotionResponse liste_faculte=
                                new PromotionResponse (
                                        response.body().get(a).getCodePromotion(),
                                        response.body().get(a).getCodeFaculte(),
                                        response.body().get(a).getDesignationPromotion()
                                );

                        list_local_depot.add(response.body().get(a).getCodePromotion());
                    }
                    arrayAdapterPromotion = new ArrayAdapter<>(ListeHoraireTousActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, list_local_depot);

                    spinner_liste_promotione.setAdapter(arrayAdapterPromotion);

                }
            }

            @Override
            public void onFailure(Call<List<PromotionResponse>> call, Throwable t) {
                //loadDepot.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recherche_horaire, menu);
        MenuItem searchMenuItem = menu.findItem(R.id.rechercherHoraire);
        rechercheHoraireSearchView = (SearchView) searchMenuItem.getActionView();

        rechercheHoraireSearchView.setQueryHint("Rechercher...");

        rechercheHoraireSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                String search = s;
                horaireAdapter.filter(search);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                String search = s;
                horaireAdapter.filter(search);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}
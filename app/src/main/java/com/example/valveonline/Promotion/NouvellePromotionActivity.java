package com.example.valveonline.Promotion;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.valveonline.DataBaseConnector.DonneesFromMySQL;
import com.example.valveonline.DataBaseConnector.Reponse;
import com.example.valveonline.DataBaseConnector.me_URL;
import com.example.valveonline.Faculte.NouvelleFaculteActivity;
import com.example.valveonline.Faculte.data.FaculteAdapter;
import com.example.valveonline.Faculte.data.FaculteReponse;
import com.example.valveonline.Faculte.data.FaculteRepository;
import com.example.valveonline.Promotion.data.PromotionAdapter;
import com.example.valveonline.Promotion.data.PromotionRepository;
import com.example.valveonline.Promotion.data.PromotionResponse;
import com.example.valveonline.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NouvellePromotionActivity extends AppCompatActivity {

    ProgressBar progressBarLoadPromotion;
    PromotionRepository promotionRepository;
    FaculteRepository faculteRepository;
    RecyclerView recyclerViewListePromotion;
    PromotionAdapter promotionAdapter;
    ArrayAdapter<String> arrayAdapterFaculte;
    Button nouvellePromotionBtn;
    String codeFaculte;
    List<String> list_local_depot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nouvelle_promotion);

        this.getSupportActionBar().setTitle("Nouvelle promotion");

        progressBarLoadPromotion = findViewById(R.id.progress_load_promotion);
        recyclerViewListePromotion = findViewById(R.id.promotion_recycle_promotion);
        nouvellePromotionBtn = findViewById(R.id.promotion_add_btn);

        //progressBarLoadFaculte.setVisibility(View.INVISIBLE);

        promotionRepository = PromotionRepository.getInstance();
        faculteRepository = FaculteRepository.getInstance();
        promotionAdapter = new PromotionAdapter(NouvellePromotionActivity.this);

        recyclerViewListePromotion.setHasFixedSize(true);
        recyclerViewListePromotion.setLayoutManager(new LinearLayoutManager(this));

        LoadListePromotion(progressBarLoadPromotion);

        nouvellePromotionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(NouvellePromotionActivity.this);
                View myView = getLayoutInflater().inflate(R.layout.dialog_nouvelle_promotion,null);
                builder.setView(myView);

                EditText designation = myView.findViewById(R.id.dialog_promotion_designation);
                Spinner spinner_faculte = myView.findViewById(R.id.promotion_spinner_faculter);
                Button save_promotion = myView.findViewById(R.id.dialog_promotion_save);
                ProgressBar load = myView.findViewById(R.id.dialog_promotion_saveprogress);

                LoadListeFaculte(load, spinner_faculte);



                AlertDialog dialog = builder.create();
                dialog.show();

                save_promotion.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        new AsyncSavePromotion(designation, load, dialog,
                                spinner_faculte.getSelectedItem().toString()).execute();



                    }
                });

            }
        });
    }

    public void LoadListeFaculte(ProgressBar loadDepot, Spinner spinner_liste_faculte)
    {
        Call<List<FaculteReponse>> call_liste_depot = faculteRepository.faculteConnexion().getListeFaculte();
        loadDepot.setVisibility(View.VISIBLE);
        call_liste_depot.enqueue(new Callback<List<FaculteReponse>>() {
            @Override
            public void onResponse(Call<List<FaculteReponse>> call, Response<List<FaculteReponse>> response) {
                if (response.isSuccessful())
                {
                    loadDepot.setVisibility(View.GONE);

                     list_local_depot = new ArrayList<>();
                    for (int a = 0; a < response.body().size(); a++)
                    {
                        FaculteReponse liste_faculte=
                                new FaculteReponse (
                                        response.body().get(a).getCodeFaculte(),
                                        response.body().get(a).getDesignationFaculte()
                                );

                        list_local_depot.add(response.body().get(a).getCodeFaculte());
                    }
                    arrayAdapterFaculte = new ArrayAdapter<>(NouvellePromotionActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, list_local_depot);

                    spinner_liste_faculte.setAdapter(arrayAdapterFaculte);

                }
            }

            @Override
            public void onFailure(Call<List<FaculteReponse>> call, Throwable t) {
                loadDepot.setVisibility(View.GONE);
            }
        });
    }


    public void LoadListePromotion(ProgressBar progressBarLoadArticle)
    {
        Call<List<PromotionResponse>> call_liste_article = promotionRepository.promotionConnexion().getListePromotion();
        progressBarLoadArticle.setVisibility(View.VISIBLE);
        call_liste_article.enqueue(new Callback<List<PromotionResponse>>() {
            @Override
            public void onResponse(Call<List<PromotionResponse>> call, Response<List<PromotionResponse>> response) {
                if (response.isSuccessful())
                {
                    progressBarLoadArticle.setVisibility(View.GONE);

                    List<PromotionResponse> list_local = new ArrayList<>();
                    for (int a = 0; a < response.body().size(); a++)
                    {
                        PromotionResponse liste_article =
                                new PromotionResponse (
                                        response.body().get(a).getCodePromotion(),
                                        response.body().get(a).getCodeFaculte(),
                                        response.body().get(a).getDesignationPromotion()
                                );


                        list_local.add(liste_article);
                    }
                    promotionAdapter.setList(list_local);
                    recyclerViewListePromotion.setAdapter(promotionAdapter);

                }
            }

            @Override
            public void onFailure(Call<List<PromotionResponse>> call, Throwable t) {
                progressBarLoadArticle.setVisibility(View.GONE);
            }
        });
    }

    public class AsyncSavePromotion extends AsyncTask<Void, Void, Void>
    {
        EditText editTextDesignation;
        ProgressBar saveProgress;
        AlertDialog dialog;
        String codeFaculte;

        public AsyncSavePromotion(EditText editTextDesignation,
                                ProgressBar saveProgress,
                                AlertDialog dialog,
                                String codeFaculte) {
            this.editTextDesignation = editTextDesignation;
            this.saveProgress = saveProgress;
            this.dialog = dialog;
            this.codeFaculte = codeFaculte;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            saveProgress.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);

            dialog.dismiss();


        }

        @Override
        protected Void doInBackground(Void... voids) {


            SavePromotion(editTextDesignation, codeFaculte);

            return null;
        }
    }

//    public String GetCodeFaculte(EditText editTextDesignation, ProgressBar load,
//                               AlertDialog dialog, String designationFaculte)
    public String GetCodeFaculte()
    {

        Call<String> call_liste_article = faculteRepository.faculteConnexion().getCodeFaculte("DROIT");
        //progressBarLoadArticle.setVisibility(View.VISIBLE);
        call_liste_article.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful())
                {
                    //new AsyncSavePromotion(editTextDesignation,load,dialog,response.body().toString()).execute();
                    //return response.body().toString();
                    codeFaculte = response.body().toString();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                //progressBarLoadArticle.setVisibility(View.GONE);
            }
        });

        return codeFaculte;
    }

    public void SavePromotion(EditText editTextDesignation, String codeFaculte)
    {
        PromotionRepository promotionRepository = PromotionRepository.getInstance();
        PromotionResponse promotionResponse = new PromotionResponse();

        promotionResponse.setCodeFaculte(codeFaculte);
        promotionResponse.setDesignationPromotion(editTextDesignation.getText().toString());

        promotionRepository.promotionConnexion().SavePromotion(promotionResponse).enqueue(new Callback<Reponse>()
        {
            @Override
            public void onResponse(Call<Reponse> call, Response<Reponse> response) {
                if (response.isSuccessful())
                {
                    Log.e("Faculte",""+response);


                    Reponse saveee = response.body();

                    boolean success = saveee.isSuccess();
                    String message = saveee.getMessage();

                    Log.e("OPERATION",response.body().toString());
                    if(success){
                        Toast.makeText(NouvellePromotionActivity.this, ""+message, Toast.LENGTH_SHORT).show();
                        LoadListePromotion(progressBarLoadPromotion);
                        Log.e("OPERATION",response.body().toString());
                    }else{
                        Toast.makeText(NouvellePromotionActivity.this, "Echec"+message, Toast.LENGTH_SHORT).show();
                        Log.e("Faculte",""+response);
                    }

                }
                else
                {
                    switch (response.code())
                    {
                        case 404:
                            Toast.makeText(NouvellePromotionActivity.this, "Serveur introuvable", Toast.LENGTH_LONG).show();
                            Log.e("Facule",""+response);
                            break;
                        case 500:
                            Toast.makeText(NouvellePromotionActivity.this, "Serveur en pane",Toast.LENGTH_LONG).show();
                            Log.e("Faculte",""+response);
                            break;
                        default:
                            Toast.makeText(NouvellePromotionActivity.this, "Erreur inconnu", Toast.LENGTH_LONG).show();
                            Log.e("Faculte",""+response);
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<Reponse> call, Throwable t) {
                Toast.makeText(NouvellePromotionActivity.this, "Probleme de connection", Toast.LENGTH_LONG).show();
            }
        });
    }
}
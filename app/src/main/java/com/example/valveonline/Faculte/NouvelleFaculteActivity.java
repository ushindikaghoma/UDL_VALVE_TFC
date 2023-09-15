package com.example.valveonline.Faculte;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.valveonline.DataBaseConnector.Reponse;
import com.example.valveonline.Faculte.data.FaculteAdapter;
import com.example.valveonline.Faculte.data.FaculteReponse;
import com.example.valveonline.Faculte.data.FaculteRepository;
import com.example.valveonline.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NouvelleFaculteActivity extends AppCompatActivity {

    ProgressBar progressBarLoadFaculte;
    FaculteRepository faculteRepository;
    RecyclerView recyclerViewListeFaculte;
    FaculteAdapter faculteAdapter;
    Button nouvelleFaculteBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nouvelle_faculte);

        this.getSupportActionBar().setTitle("Nouvelle faculté");

        progressBarLoadFaculte = findViewById(R.id.progress_load_faculte);
        recyclerViewListeFaculte = findViewById(R.id.recycle_faculte);
        nouvelleFaculteBtn = findViewById(R.id.nouvelle_faculte_btn);

        //progressBarLoadFaculte.setVisibility(View.INVISIBLE);

        faculteRepository = FaculteRepository.getInstance();
        faculteAdapter = new FaculteAdapter(NouvelleFaculteActivity.this);

        recyclerViewListeFaculte.setHasFixedSize(true);
        recyclerViewListeFaculte.setLayoutManager(new LinearLayoutManager(this));

        LoadListeFaculte(progressBarLoadFaculte);

        nouvelleFaculteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(NouvelleFaculteActivity.this);
                View myView = getLayoutInflater().inflate(R.layout.dialog_nouvelle_faculte, null);
                builder.setView(myView);

                EditText designation_faculte = myView.findViewById(R.id.dialog_faculte_designation);
                EditText code_faculte = myView.findViewById(R.id.dialog_faculte_code);
                Button save_faculte = myView.findViewById(R.id.dialog_faculte_save);
                ProgressBar progressSave = myView.findViewById(R.id.dialog_faculte_saveprogress);

                progressSave.setVisibility(View.INVISIBLE);

                final AlertDialog dialog = builder.create();
                dialog.show();

                save_faculte.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                      new  AsyncSaveFaculte(designation_faculte,code_faculte,progressSave, dialog).execute();
                    }
                });


            }
        });
    }


    public void LoadListeFaculte(ProgressBar progressBarLoadArticle)
    {
        Call<List<FaculteReponse>> call_liste_article = faculteRepository.faculteConnexion().getListeFaculte();
        progressBarLoadArticle.setVisibility(View.VISIBLE);
        call_liste_article.enqueue(new Callback<List<FaculteReponse>>() {
            @Override
            public void onResponse(Call<List<FaculteReponse>> call, Response<List<FaculteReponse>> response) {
                if (response.isSuccessful())
                {
                    progressBarLoadArticle.setVisibility(View.GONE);

                    List<FaculteReponse> list_local = new ArrayList<>();
                    for (int a = 0; a < response.body().size(); a++)
                    {
                        FaculteReponse liste_article =
                                new FaculteReponse (
                                        response.body().get(a).getCodeFaculte(),
                                        response.body().get(a).getDesignationFaculte()
                                );


                        list_local.add(liste_article);
                    }
                    faculteAdapter.setList(list_local);
                    recyclerViewListeFaculte.setAdapter(faculteAdapter);

                }
            }

            @Override
            public void onFailure(Call<List<FaculteReponse>> call, Throwable t) {
                progressBarLoadArticle.setVisibility(View.GONE);
            }
        });
    }

    public class AsyncSaveFaculte extends AsyncTask<Void, Void, Void>
    {
        EditText editTextDesignation, editTextCode;
        ProgressBar saveProgress;
        AlertDialog dialog;

        public AsyncSaveFaculte(EditText editTextDesignation, EditText editTextCode,
                                ProgressBar saveProgress, AlertDialog dialog) {
            this.editTextDesignation = editTextDesignation;
            this.editTextCode = editTextCode;
            this.saveProgress = saveProgress;
            this.dialog = dialog;

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

            FaculteRepository faculteRepository = FaculteRepository.getInstance();
            FaculteReponse faculteReponse = new FaculteReponse();

            faculteReponse.setCodeFaculte(editTextCode.getText().toString());
            faculteReponse.setDesignationFaculte(editTextDesignation.getText().toString());
            faculteRepository.faculteConnexion().SaveFaculte(faculteReponse).enqueue(new Callback<Reponse>()
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
                            Toast.makeText(NouvelleFaculteActivity.this, ""+message, Toast.LENGTH_SHORT).show();
                            LoadListeFaculte(progressBarLoadFaculte);
                        }else{
                            Toast.makeText(NouvelleFaculteActivity.this, "Echec"+message, Toast.LENGTH_SHORT).show();
                        }

                    }
                    else
                    {
                        switch (response.code())
                        {
                            case 404:
                                Toast.makeText(NouvelleFaculteActivity.this, "Serveur introuvable", Toast.LENGTH_LONG).show();
                                Log.e("Facule",""+response);
                                break;
                            case 500:
                                Toast.makeText(NouvelleFaculteActivity.this, "Serveur en pane",Toast.LENGTH_LONG).show();
                                Log.e("Faculte",""+response);
                                break;
                            default:
                                Toast.makeText(NouvelleFaculteActivity.this, "Erreur inconnu", Toast.LENGTH_LONG).show();
                                Log.e("Faculte",""+response);
                                break;
                        }
                    }
                }

                @Override
                public void onFailure(Call<Reponse> call, Throwable t) {
                    Toast.makeText(NouvelleFaculteActivity.this, "Probleme de connection", Toast.LENGTH_LONG).show();
                }
            });

            return null;
        }
    }
}
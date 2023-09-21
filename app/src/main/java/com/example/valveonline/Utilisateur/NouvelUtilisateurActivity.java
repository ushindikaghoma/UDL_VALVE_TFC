package com.example.valveonline.Utilisateur;

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

import com.example.valveonline.DataBaseConnector.Reponse;
import com.example.valveonline.Faculte.data.FaculteReponse;
import com.example.valveonline.Faculte.data.FaculteRepository;
import com.example.valveonline.Horaire.PublierHoraireActivity;
import com.example.valveonline.Horaire.data.HoraireRepository;
import com.example.valveonline.Horaire.data.HoraireResponse;
import com.example.valveonline.Promotion.NouvellePromotionActivity;
import com.example.valveonline.Promotion.data.PromotionRepository;
import com.example.valveonline.Promotion.data.PromotionResponse;
import com.example.valveonline.R;
import com.example.valveonline.Utilisateur.data.UtilisateurAdapter;
import com.example.valveonline.Utilisateur.data.UtilisateurRepository;
import com.example.valveonline.Utilisateur.data.UtilisateurResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NouvelUtilisateurActivity extends AppCompatActivity {

    ProgressBar progressBar;
    FaculteRepository faculteRepository;
    ArrayAdapter arrayAdapterFaculte;
    ArrayAdapter arrayAdapterPromotion;
    PromotionRepository promotionRepository;
    UtilisateurRepository utilisateurRepository;
    UtilisateurAdapter utilisateurAdapter;
    RecyclerView recyclerViewListeUtilisateur;
    Button nouvel_utilisateur_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nouvel_utilisateur);

        this.getSupportActionBar().setTitle("Utilisateurs");

        faculteRepository = FaculteRepository.getInstance();
        promotionRepository = PromotionRepository.getInstance();
        utilisateurRepository = UtilisateurRepository.getInstance();
        utilisateurAdapter = new UtilisateurAdapter(this);

        recyclerViewListeUtilisateur = findViewById(R.id.recycle_utilisateur);
        nouvel_utilisateur_btn = findViewById(R.id.utilisateur_add_utilisateur);
        progressBar = findViewById(R.id.progress_load_utilisateur);

        recyclerViewListeUtilisateur.setHasFixedSize(true);
        recyclerViewListeUtilisateur.setLayoutManager(new LinearLayoutManager(this));

        LoadListeUtilisateur(progressBar);

        nouvel_utilisateur_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(NouvelUtilisateurActivity.this);
                View myView = getLayoutInflater().inflate(R.layout.dialog_nouvel_utilisateur, null);
                builder.setView(myView);

                EditText nom_user = myView.findViewById(R.id.dialog_utilisateur_designation);
                EditText password_user = myView.findViewById(R.id.dialog_utilisateur_password);
                Spinner niveau_user = myView.findViewById(R.id.utilisateur_spinner_niveau);
                Spinner faculte = myView.findViewById(R.id.utilisateur_spinner_faculte);
                Spinner promotion = myView.findViewById(R.id.utilisateur_spinner_promotion);
                Button save_user = myView.findViewById(R.id.dialog_utilisateur_save);
                ProgressBar progress = myView.findViewById(R.id.dialog_utilisateur_saveprogress);

                new AsyncLoadSpinner(progress, faculte, promotion, niveau_user).execute();


                AlertDialog dialog = builder.create();
                dialog.show();

                save_user.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        new AsyncSaveUtilisateur(faculte.getSelectedItem().toString(),
                                promotion.getSelectedItem().toString(),
                                niveau_user.getSelectedItem().toString(),
                                nom_user, password_user,progress, dialog).execute();
                    }
                });
            }
        });


    }

    public void LoadListeNiveau(Spinner spinner_liste_niveau)
    {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Admin");
        arrayList.add("Etudiant");
        arrayList.add("Appariteur");
        arrayList.add("Visiteur");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, arrayList);
        spinner_liste_niveau.setAdapter(arrayAdapter);
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
                    arrayAdapterFaculte = new ArrayAdapter<>(NouvelUtilisateurActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, list_local_depot);

                    spinner_liste_faculte.setAdapter(arrayAdapterFaculte);

                }
            }

            @Override
            public void onFailure(Call<List<FaculteReponse>> call, Throwable t) {
                //loadDepot.setVisibility(View.GONE);
            }
        });
    }

    public void LoadListeUtilisateur(ProgressBar progressBarLoadArticle)
    {
        Call<List<UtilisateurResponse>> call_liste_article = utilisateurRepository.utilisateurConnexion().getListeUtilisateur();
        progressBarLoadArticle.setVisibility(View.VISIBLE);
        call_liste_article.enqueue(new Callback<List<UtilisateurResponse>>() {
            @Override
            public void onResponse(Call<List<UtilisateurResponse>> call, Response<List<UtilisateurResponse>> response) {
                if (response.isSuccessful())
                {
                    progressBarLoadArticle.setVisibility(View.GONE);

                    List<UtilisateurResponse> list_local = new ArrayList<>();
                    for (int a = 0; a < response.body().size(); a++)
                    {
                        UtilisateurResponse liste_article =
                                new UtilisateurResponse (
                                        response.body().get(a).getNomUtilisateur(),
                                        response.body().get(a).getNiveauUtilisateur(),
                                        response.body().get(a).getCodeFaculte(),
                                        response.body().get(a).getCodePromotion(),
                                        response.body().get(a).getIdUtilisateur(),
                                        response.body().get(a).getMotPasseUtilisateur()

                                );


                        list_local.add(liste_article);
                    }
                    utilisateurAdapter.setList(list_local);
                    recyclerViewListeUtilisateur.setAdapter(utilisateurAdapter);

                }
            }

            @Override
            public void onFailure(Call<List<UtilisateurResponse>> call, Throwable t) {
                progressBarLoadArticle.setVisibility(View.GONE);
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
                    arrayAdapterPromotion = new ArrayAdapter<>(NouvelUtilisateurActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, list_local_depot);

                    spinner_liste_promotione.setAdapter(arrayAdapterPromotion);

                }
            }

            @Override
            public void onFailure(Call<List<PromotionResponse>> call, Throwable t) {
                //loadDepot.setVisibility(View.GONE);
            }
        });
    }

    public class AsyncLoadSpinner extends AsyncTask<Void, Void, Void>
    {
        ProgressBar load;
        Spinner faculte, promotion, niveau;

        public AsyncLoadSpinner(ProgressBar load, Spinner faculte,
                                Spinner promotion, Spinner niveau) {
            this.load = load;
            this.faculte = faculte;
            this.promotion = promotion;
            this.niveau = niveau;
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
            LoadListeNiveau(niveau);

            return null;
        }
    }

    public class AsyncSaveUtilisateur extends AsyncTask<Void, Void, Void>
    {
        String codeFaculte, codePromotion, niveau;
        EditText editTextNom, editTextPassword;
        ProgressBar saveProgress;
        AlertDialog dialog;

        public AsyncSaveUtilisateur(String codeFaculte, String codePromotion,
                                    String niveau,
                                    EditText editTextNom,
                                    EditText editTextPassword,
                                    ProgressBar saveProgress,
                                    AlertDialog dialog) {
            this.codeFaculte = codeFaculte;
            this.codePromotion = codePromotion;
            this.niveau = niveau;
            this.editTextNom = editTextNom;
            this.editTextPassword = editTextPassword;
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

            saveProgress.setVisibility(View.GONE);
            editTextNom.setText("");
            editTextPassword.setText("");

            dialog.dismiss();


        }

        @Override
        protected Void doInBackground(Void... voids) {

            UtilisateurRepository utilisateurRepository = UtilisateurRepository.getInstance();
            UtilisateurResponse utilisateurResponse = new UtilisateurResponse();

            utilisateurResponse.setCodeFaculte(codeFaculte);
            utilisateurResponse.setCodePromotion(codePromotion);
            utilisateurResponse.setNomUtilisateur(editTextNom.getText().toString());
            utilisateurResponse.setMotPasseUtilisateur(editTextPassword.getText().toString());
            utilisateurResponse.setNiveauUtilisateur(niveau);
            utilisateurResponse.setDesignationUtilisateur(editTextNom.getText().toString());
            utilisateurResponse.setFonctionUtilisateur(niveau);
            utilisateurRepository.utilisateurConnexion().SaveUtilisateur(utilisateurResponse).enqueue(new Callback<Reponse>()
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
                            Toast.makeText(NouvelUtilisateurActivity.this, ""+message, Toast.LENGTH_SHORT).show();
                            LoadListeUtilisateur(progressBar);

                        }else{
                            Toast.makeText(NouvelUtilisateurActivity.this, "Echec"+message, Toast.LENGTH_SHORT).show();
                        }

                    }
                    else
                    {
                        switch (response.code())
                        {
                            case 404:
                                Toast.makeText(NouvelUtilisateurActivity.this, "Serveur introuvable", Toast.LENGTH_LONG).show();
                                Log.e("Facule",""+response);
                                break;
                            case 500:
                                Toast.makeText(NouvelUtilisateurActivity.this, "Serveur en pane",Toast.LENGTH_LONG).show();
                                Log.e("Faculte",""+response);
                                break;
                            default:
                                Toast.makeText(NouvelUtilisateurActivity.this, "Erreur inconnu", Toast.LENGTH_LONG).show();
                                Log.e("Faculte",""+response);
                                break;
                        }
                    }
                }

                @Override
                public void onFailure(Call<Reponse> call, Throwable t) {
                    Toast.makeText(NouvelUtilisateurActivity.this, "Probleme de connection", Toast.LENGTH_LONG).show();
                }
            });

            return null;
        }
    }
}
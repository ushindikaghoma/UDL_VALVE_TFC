package com.example.valveonline.Horaire;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.valveonline.DataBaseConnector.Reponse;
import com.example.valveonline.Faculte.NouvelleFaculteActivity;
import com.example.valveonline.Faculte.data.FaculteReponse;
import com.example.valveonline.Faculte.data.FaculteRepository;
import com.example.valveonline.Horaire.data.HoraireRepository;
import com.example.valveonline.Horaire.data.HoraireResponse;
import com.example.valveonline.Promotion.NouvellePromotionActivity;
import com.example.valveonline.Promotion.data.PromotionRepository;
import com.example.valveonline.Promotion.data.PromotionResponse;
import com.example.valveonline.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PublierHoraireActivity extends AppCompatActivity {

    Spinner code_faculte, code_promotion;
    EditText date_horaire, jour_horaire,
             nom_cours, heure_debut, heure_fin;
    Button save_horaire_btn;
    ProgressBar progressBar;
    FaculteRepository faculteRepository;
    ArrayAdapter arrayAdapterFaculte;
    ArrayAdapter arrayAdapterPromotion;
    PromotionRepository promotionRepository;

    Calendar calendar;
    String date_debut, date_fin, todayDate;
    String type;
    HoraireResponse myObject;
    Button view_liste_horaire;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publier_horaire);

        this.getSupportActionBar().setTitle("Publier horaire");

        code_faculte = findViewById(R.id.horaire_spinner_faculte);
        code_promotion = findViewById(R.id.horaire_spinner_promotion);
        date_horaire = findViewById(R.id.horaire_date_horaire);
        jour_horaire = findViewById(R.id.horaire_jour_horaire);
        nom_cours = findViewById(R.id.horaire_nom_cours);
        heure_debut = findViewById(R.id.horaire_heure_debut);
        heure_fin = findViewById(R.id.horaire_heure_fin);
        save_horaire_btn = findViewById(R.id.horaire_confirmer_btn);
        view_liste_horaire = findViewById(R.id.horaire_liste_horaire_btn);
        progressBar = findViewById(R.id.horaire_progress);

        faculteRepository = FaculteRepository.getInstance();
        promotionRepository = PromotionRepository.getInstance();

        type = getIntent().getStringExtra("type");
        myObject = (HoraireResponse) getIntent().getSerializableExtra("myObject");

        calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        todayDate = format.format(calendar.getTime());

        Toast.makeText(PublierHoraireActivity.this, ""+type, Toast.LENGTH_SHORT).show();

        if (type != "")
        {
            if (type.equals("modifier"))
            {
                date_horaire.setText(myObject.getDateHoraire());
                new AsyncLoadSpinner(progressBar, code_faculte, code_promotion).execute();
                nom_cours.setText(myObject.getCodeCours());
                jour_horaire.setText(myObject.getJourHoraire());
                heure_debut.setText(myObject.getHeureDebut());
                heure_fin.setText(myObject.getHeureFin());

            }else
            {
                date_horaire.setText(todayDate);

                new AsyncLoadSpinner(progressBar, code_faculte, code_promotion).execute();
            }
        }else {
            date_horaire.setText(todayDate);

            new AsyncLoadSpinner(progressBar, code_faculte, code_promotion).execute();
        }

        view_liste_horaire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ListeHoraireTousActivity.class));
            }
        });


        date_horaire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar c = Calendar.getInstance();

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(

                        PublierHoraireActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
//                                if(day>9 && month>9)date_debut.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                if(dayOfMonth>9 && month>9)date_horaire.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                                if(dayOfMonth>9 && !(month>9))date_horaire.setText(year + "-0" + (monthOfYear + 1) + "-" + dayOfMonth);
                                if(!(dayOfMonth>9) && month>9)date_horaire.setText(year + "-" + (monthOfYear + 1) + "-0" + dayOfMonth);
                                if(!(dayOfMonth>9) && !(month>9))date_horaire.setText(year + "-0" + (monthOfYear + 1) + "-0" + dayOfMonth);
                                //date_debut.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        },
                        year, month, day);
                datePickerDialog.show();
            }
        });

        save_horaire_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (type.equals("modifier"))
                {
                    new AsyncUpdateHoraire(code_faculte.getSelectedItem().toString(),
                            code_promotion.getSelectedItem().toString(),
                            date_horaire, jour_horaire, nom_cours, heure_debut,
                            heure_fin,progressBar).execute();
                }else
                {
                    new AsyncSaveHoraire(code_faculte.getSelectedItem().toString(),
                            code_promotion.getSelectedItem().toString(),
                            date_horaire, jour_horaire, nom_cours, heure_debut,
                            heure_fin,progressBar).execute();
                }
            }
        });


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
                    arrayAdapterFaculte = new ArrayAdapter<>(PublierHoraireActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, list_local_depot);

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
                    arrayAdapterPromotion = new ArrayAdapter<>(PublierHoraireActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, list_local_depot);

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

    public class AsyncSaveHoraire extends AsyncTask<Void, Void, Void>
    {
        String codeFaculte, codePromotion;
        EditText editTextDateHoraire, editTextJourHoraire,
                editTextNomCours, editTextHeureDebut, editTextHeureFin;
        ProgressBar saveProgress;
        AlertDialog dialog;

        public AsyncSaveHoraire(String codeFaculte, String codePromotion,
                                EditText editTextDateHoraire,
                                EditText editTextJourHoraire,
                                EditText editTextNomCours,
                                EditText editTextHeureDebut,
                                EditText editTextHeureFin,
                                ProgressBar saveProgress
                                ) {
            this.codeFaculte = codeFaculte;
            this.codePromotion = codePromotion;
            this.editTextDateHoraire = editTextDateHoraire;
            this.editTextJourHoraire = editTextJourHoraire;
            this.editTextNomCours = editTextNomCours;
            this.editTextHeureDebut = editTextHeureDebut;
            this.editTextHeureFin = editTextHeureFin;
            this.saveProgress = saveProgress;
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
                    editTextJourHoraire.setText("");
                    editTextNomCours.setText("");
                    heure_debut.setText("");


        }

        @Override
        protected Void doInBackground(Void... voids) {

            HoraireRepository horaireRepository = HoraireRepository.getInstance();
            HoraireResponse horaireResponse = new HoraireResponse();

            horaireResponse.setCodeFaculte(codeFaculte);
            horaireResponse.setCodePromotion(codePromotion);
            horaireResponse.setDateHoraire(editTextDateHoraire.getText().toString());
            horaireResponse.setJourHoraire(editTextJourHoraire.getText().toString().toUpperCase());
            horaireResponse.setCodeCours(editTextNomCours.getText().toString());
            horaireResponse.setHeureDebut(editTextHeureDebut.getText().toString());
            horaireResponse.setHeureFin(editTextHeureFin.getText().toString());
            horaireResponse.setEtatHoraire(0);
            horaireRepository.horaireConnexion().SaveHoraire(horaireResponse).enqueue(new Callback<Reponse>()
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
                            Toast.makeText(PublierHoraireActivity.this, ""+message, Toast.LENGTH_SHORT).show();

                        }else{
                            Toast.makeText(PublierHoraireActivity.this, "Echec"+message, Toast.LENGTH_SHORT).show();
                        }

                    }
                    else
                    {
                        switch (response.code())
                        {
                            case 404:
                                Toast.makeText(PublierHoraireActivity.this, "Serveur introuvable", Toast.LENGTH_LONG).show();
                                Log.e("Facule",""+response);
                                break;
                            case 500:
                                Toast.makeText(PublierHoraireActivity.this, "Serveur en pane",Toast.LENGTH_LONG).show();
                                Log.e("Faculte",""+response);
                                break;
                            default:
                                Toast.makeText(PublierHoraireActivity.this, "Erreur inconnu", Toast.LENGTH_LONG).show();
                                Log.e("Faculte",""+response);
                                break;
                        }
                    }
                }

                @Override
                public void onFailure(Call<Reponse> call, Throwable t) {
                    Toast.makeText(PublierHoraireActivity.this, "Probleme de connection", Toast.LENGTH_LONG).show();
                }
            });

            return null;
        }
    }

    public class AsyncUpdateHoraire extends AsyncTask<Void, Void, Void>
    {
        String codeFaculte, codePromotion;
        EditText editTextDateHoraire, editTextJourHoraire,
                editTextNomCours, editTextHeureDebut, editTextHeureFin;
        ProgressBar saveProgress;
        AlertDialog dialog;

        public AsyncUpdateHoraire(String codeFaculte, String codePromotion,
                                EditText editTextDateHoraire,
                                EditText editTextJourHoraire,
                                EditText editTextNomCours,
                                EditText editTextHeureDebut,
                                EditText editTextHeureFin,
                                ProgressBar saveProgress
        ) {
            this.codeFaculte = codeFaculte;
            this.codePromotion = codePromotion;
            this.editTextDateHoraire = editTextDateHoraire;
            this.editTextJourHoraire = editTextJourHoraire;
            this.editTextNomCours = editTextNomCours;
            this.editTextHeureDebut = editTextHeureDebut;
            this.editTextHeureFin = editTextHeureFin;
            this.saveProgress = saveProgress;
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
            editTextJourHoraire.setText("");
            editTextNomCours.setText("");
            heure_debut.setText("");
            editTextHeureFin.setText("");


        }

        @Override
        protected Void doInBackground(Void... voids) {

            HoraireRepository horaireRepository = HoraireRepository.getInstance();
            HoraireResponse horaireResponse = new HoraireResponse();

            horaireResponse.setCodeFaculte(codeFaculte);
            horaireResponse.setCodePromotion(codePromotion);
            horaireResponse.setDateHoraire(editTextDateHoraire.getText().toString());
            horaireResponse.setJourHoraire(editTextJourHoraire.getText().toString().toUpperCase());
            horaireResponse.setCodeCours(editTextNomCours.getText().toString());
            horaireResponse.setHeureDebut(editTextHeureDebut.getText().toString());
            horaireResponse.setHeureFin(editTextHeureFin.getText().toString());
            horaireResponse.setIdHoraire(myObject.getIdHoraire());
            horaireRepository.horaireConnexion().UpdadeHoraire(horaireResponse).enqueue(new Callback<Reponse>()
            {
                @Override
                public void onResponse(Call<Reponse> call, Response<Reponse> response) {
                    if (response.isSuccessful())
                    {
                        Log.e("Faculte",""+response.body().getMessage());


                        Reponse saveee = response.body();

                        boolean success = saveee.isSuccess();
                        String message = saveee.getMessage();

                        Log.e("OPERATION",response.body().toString());
                        if(success){
                            Toast.makeText(PublierHoraireActivity.this, ""+message, Toast.LENGTH_SHORT).show();

                        }else{
                            Toast.makeText(PublierHoraireActivity.this, "Echec"+message, Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        switch (response.code())
                        {
                            case 404:
                                Toast.makeText(PublierHoraireActivity.this, "Serveur introuvable", Toast.LENGTH_LONG).show();
                                Log.e("Facule",""+response);
                                break;
                            case 500:
                                Toast.makeText(PublierHoraireActivity.this, "Serveur en pane",Toast.LENGTH_LONG).show();
                                Log.e("Faculte",""+response);
                                break;
                            default:
                                Toast.makeText(PublierHoraireActivity.this, "Erreur inconnu", Toast.LENGTH_LONG).show();
                                Log.e("Faculte",""+response);
                                break;
                        }
                    }
                }

                @Override
                public void onFailure(Call<Reponse> call, Throwable t) {
                    Toast.makeText(PublierHoraireActivity.this, "Probleme de connection", Toast.LENGTH_LONG).show();
                }
            });

            return null;
        }
    }
}
package com.example.valveonline.Infos;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.valveonline.DataBaseConnector.Reponse;
import com.example.valveonline.Faculte.NouvelleFaculteActivity;
import com.example.valveonline.Faculte.data.FaculteReponse;
import com.example.valveonline.Faculte.data.FaculteRepository;
import com.example.valveonline.Horaire.PublierHoraireActivity;
import com.example.valveonline.Horaire.data.HoraireRepository;
import com.example.valveonline.Horaire.data.HoraireResponse;
import com.example.valveonline.Infos.data.InfosRepository;
import com.example.valveonline.Infos.data.InfosResponse;
import com.example.valveonline.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PublierInfosActivity extends AppCompatActivity {

    EditText editTextDateInfos, editTextDescriptionInfos, editTextTitreInfos;
    Button saveInfosBtn;
    ProgressBar progressBar;
    InfosRepository infosRepository;
    Calendar calendar;
    String todayDate, type;
    InfosResponse myObject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publier_infos);

        this.getSupportActionBar().setTitle("Publier une information");


        editTextDateInfos = findViewById(R.id.infos_date);
        editTextDescriptionInfos = findViewById(R.id.infos_description);
        editTextTitreInfos = findViewById(R.id.infos_titre);
        progressBar = findViewById(R.id.progress_save_infos);
        saveInfosBtn = findViewById(R.id.infos_confirmer_btn);

        infosRepository = InfosRepository.getInstance();
        progressBar.setVisibility(View.GONE);

        calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        todayDate = format.format(calendar.getTime());

        type = getIntent().getStringExtra("type");
        myObject = (InfosResponse) getIntent().getSerializableExtra("myObject");


        if (type != "")
        {
            if (type.equals("modifier"))
            {
                editTextDateInfos.setText(myObject.getDateInfos());
                editTextTitreInfos.setText(myObject.getTitreInfos());
                editTextDescriptionInfos.setText(myObject.getDesciptionInfos());

            }else
            {
                editTextDateInfos.setText(todayDate);
            }
        }else {
            editTextDateInfos.setText(todayDate);
        }

        saveInfosBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (type.equals("modifier"))
                {
                    new AsyncUpdateInfos(editTextDateInfos.getText().toString(),editTextDescriptionInfos,editTextTitreInfos,progressBar).execute();
                }else
                {
                    new AsyncSaveHoraire(todayDate,editTextDescriptionInfos,editTextTitreInfos,progressBar).execute();
                }
            }
        });
    }


    public class AsyncSaveHoraire extends AsyncTask<Void, Void, Void>
    {
        String date_infos;
        EditText editTextDescription, editTextTitreInfos;
        ProgressBar saveProgress;

        public AsyncSaveHoraire(String date_infos, EditText editTextDescription,EditText editTextTitreInfos,
                                ProgressBar saveProgress) {
            this.date_infos = date_infos;
            this.editTextDescription = editTextDescription;
            this.editTextTitreInfos = editTextTitreInfos;
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
            editTextDescription.setText("");


        }

        @Override
        protected Void doInBackground(Void... voids) {

            InfosRepository infosRepository = InfosRepository.getInstance();
            InfosResponse infosResponse = new InfosResponse();

            infosResponse.setDateInfos(date_infos);
            infosResponse.setTitreInfos(editTextTitreInfos.getText().toString());
            infosResponse.setDesciptionInfos(editTextDescription.getText().toString());
            infosResponse.setEtatInfos(0);
            infosRepository.infosConnexion().SaveInfos(infosResponse).enqueue(new Callback<Reponse>()
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
                            Toast.makeText(PublierInfosActivity.this, ""+message, Toast.LENGTH_SHORT).show();

                        }else{
                            Toast.makeText(PublierInfosActivity.this, "Echec"+message, Toast.LENGTH_SHORT).show();
                        }

                    }
                    else
                    {
                        switch (response.code())
                        {
                            case 404:
                                Toast.makeText(PublierInfosActivity.this, "Serveur introuvable", Toast.LENGTH_LONG).show();
                                Log.e("Facule",""+response);
                                break;
                            case 500:
                                Toast.makeText(PublierInfosActivity.this, "Serveur en pane",Toast.LENGTH_LONG).show();
                                Log.e("Faculte",""+response);
                                break;
                            default:
                                Toast.makeText(PublierInfosActivity.this, "Erreur inconnu", Toast.LENGTH_LONG).show();
                                Log.e("Faculte",""+response);
                                break;
                        }
                    }
                }

                @Override
                public void onFailure(Call<Reponse> call, Throwable t) {
                    Toast.makeText(PublierInfosActivity.this, "Probleme de connection", Toast.LENGTH_LONG).show();
                }
            });

            return null;
        }
    }

    public class AsyncUpdateInfos extends AsyncTask<Void, Void, Void>
    {
        String date_infos;
        EditText editTextDescription, editTextTitreInfos;
        ProgressBar saveProgress;

        public AsyncUpdateInfos(String date_infos, EditText editTextDescription,EditText editTextTitreInfos,
                                ProgressBar saveProgress) {
            this.date_infos = date_infos;
            this.editTextDescription = editTextDescription;
            this.editTextTitreInfos = editTextTitreInfos;
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
            editTextTitreInfos.setText("");
            editTextDescription.setText("");


        }

        @Override
        protected Void doInBackground(Void... voids) {

            InfosRepository infosRepository = InfosRepository.getInstance();
            InfosResponse infosResponse = new InfosResponse();

            infosResponse.setDateInfos(date_infos);
            infosResponse.setTitreInfos(editTextTitreInfos.getText().toString());
            infosResponse.setDesciptionInfos(editTextDescription.getText().toString());
            //infosResponse.setEtatInfos(0);
            infosResponse.setIdInfos(myObject.getIdInfos());
            infosRepository.infosConnexion().UpdadeInfos(infosResponse).enqueue(new Callback<Reponse>()
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
                            Toast.makeText(PublierInfosActivity.this, ""+message, Toast.LENGTH_SHORT).show();

                        }else{
                            Toast.makeText(PublierInfosActivity.this, "Echec"+message, Toast.LENGTH_SHORT).show();
                        }

                    }
                    else
                    {
                        switch (response.code())
                        {
                            case 404:
                                Toast.makeText(PublierInfosActivity.this, "Serveur introuvable", Toast.LENGTH_LONG).show();
                                Log.e("Facule",""+response);
                                break;
                            case 500:
                                Toast.makeText(PublierInfosActivity.this, "Serveur en pane",Toast.LENGTH_LONG).show();
                                Log.e("Faculte",""+response);
                                break;
                            default:
                                Toast.makeText(PublierInfosActivity.this, "Erreur inconnu", Toast.LENGTH_LONG).show();
                                Log.e("Faculte",""+response);
                                break;
                        }
                    }
                }

                @Override
                public void onFailure(Call<Reponse> call, Throwable t) {
                    Toast.makeText(PublierInfosActivity.this, "Probleme de connection", Toast.LENGTH_LONG).show();
                }
            });

            return null;
        }
    }

}
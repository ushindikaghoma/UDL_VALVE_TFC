package com.example.valveonline.Etudiant.data;

import com.example.valveonline.DataBaseConnector.adresseServeur;
import com.example.valveonline.Faculte.data.FaculteConnexion;
import com.example.valveonline.Faculte.data.FaculteRepository;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EtudiantRepository {
    private static EtudiantRepository instance;

    private EtudiantConnexion etudiantConnexion;

    public static EtudiantRepository getInstance() {
        if (instance == null) {
            instance = new EtudiantRepository();
        }
        return instance;
    }

    public EtudiantRepository() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(adresseServeur.getAdresseIP())
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        etudiantConnexion = retrofit.create(EtudiantConnexion.class);

    }

    public EtudiantConnexion etudiantConnexion()
    {
        return etudiantConnexion;
    }
}

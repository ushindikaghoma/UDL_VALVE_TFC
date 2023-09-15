package com.example.valveonline.Faculte.data;

import com.example.valveonline.DataBaseConnector.adresseServeur;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FaculteRepository {
    private static FaculteRepository instance;

    private FaculteConnexion faculteConnexion;

    public static FaculteRepository getInstance() {
        if (instance == null) {
            instance = new FaculteRepository();
        }
        return instance;
    }

    public FaculteRepository() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(adresseServeur.getAdresseIP())
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        faculteConnexion = retrofit.create(FaculteConnexion.class);

    }

    public FaculteConnexion faculteConnexion()
    {
        return faculteConnexion;
    }
}

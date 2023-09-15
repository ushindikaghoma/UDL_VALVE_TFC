package com.example.valveonline.Infos.data;

import com.example.valveonline.DataBaseConnector.adresseServeur;
import com.example.valveonline.Horaire.data.HoraireConnexion;
import com.example.valveonline.Horaire.data.HoraireRepository;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InfosRepository {
    private static InfosRepository instance;

    private InfosConnexion infosConnexion;

    public static InfosRepository getInstance() {
        if (instance == null) {
            instance = new InfosRepository();
        }
        return instance;
    }

    public InfosRepository() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(adresseServeur.getAdresseIP())
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        infosConnexion = retrofit.create(InfosConnexion.class);

    }

    public InfosConnexion infosConnexion()
    {
        return infosConnexion;
    }
}

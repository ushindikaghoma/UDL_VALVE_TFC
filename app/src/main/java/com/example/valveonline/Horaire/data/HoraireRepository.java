package com.example.valveonline.Horaire.data;

import com.example.valveonline.DataBaseConnector.adresseServeur;
import com.example.valveonline.Faculte.data.FaculteConnexion;
import com.example.valveonline.Faculte.data.FaculteRepository;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HoraireRepository {
    private static HoraireRepository instance;

    private HoraireConnexion horaireConnexion;

    public static HoraireRepository getInstance() {
        if (instance == null) {
            instance = new HoraireRepository();
        }
        return instance;
    }

    public HoraireRepository() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(adresseServeur.getAdresseIP())
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        horaireConnexion = retrofit.create(HoraireConnexion.class);

    }

    public HoraireConnexion horaireConnexion()
    {
        return horaireConnexion;
    }
}

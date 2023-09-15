package com.example.valveonline.Utilisateur.data;

import com.example.valveonline.DataBaseConnector.adresseServeur;
import com.example.valveonline.Horaire.data.HoraireConnexion;
import com.example.valveonline.Horaire.data.HoraireRepository;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UtilisateurRepository {
    private static UtilisateurRepository instance;

    private UtilisateurConnexion utilisateurConnexion;

    public static UtilisateurRepository getInstance() {
        if (instance == null) {
            instance = new UtilisateurRepository();
        }
        return instance;
    }

    public UtilisateurRepository() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(adresseServeur.getAdresseIP())
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        utilisateurConnexion = retrofit.create(UtilisateurConnexion.class);

    }

    public UtilisateurConnexion utilisateurConnexion()
    {
        return utilisateurConnexion;
    }
}

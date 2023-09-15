package com.example.valveonline.Promotion.data;

import com.example.valveonline.DataBaseConnector.adresseServeur;
import com.example.valveonline.Faculte.data.FaculteConnexion;
import com.example.valveonline.Faculte.data.FaculteRepository;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PromotionRepository {
    private static PromotionRepository instance;

    private PromotionConnexion promotionConnexion;

    public static PromotionRepository getInstance() {
        if (instance == null) {
            instance = new PromotionRepository();
        }
        return instance;
    }

    public PromotionRepository() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(adresseServeur.getAdresseIP())
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        promotionConnexion = retrofit.create(PromotionConnexion.class);

    }

    public PromotionConnexion promotionConnexion()
    {
        return promotionConnexion;
    }
}

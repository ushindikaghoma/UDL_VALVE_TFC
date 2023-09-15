package com.example.valveonline.Horaire.data;

import com.example.valveonline.DataBaseConnector.Reponse;
import com.example.valveonline.Faculte.data.FaculteReponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface HoraireConnexion {
    @GET("api/Faculte/GetListeFaculte")
    Call<List<FaculteReponse>> getListeFaculte();
    @GET("api/Horaire/HoraireParFaculteParPromotion")
    Call<List<HoraireResponse>> getHoraireParPromotion(@Query("codeFaculte") String codeFaculte,
                                        @Query("codePromotion") String codePromotion,
                                        @Query("jour") String jourHoraire);
    @POST("api/Horaire/SaveHoraire")
    Call<Reponse> SaveHoraire(@Body HoraireResponse horaireResponse);
}

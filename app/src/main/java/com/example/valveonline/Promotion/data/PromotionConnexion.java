package com.example.valveonline.Promotion.data;

import com.example.valveonline.DataBaseConnector.Reponse;
import com.example.valveonline.Faculte.data.FaculteReponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PromotionConnexion {
    @GET("api/Promotion/GetListePromotion")
    Call<List<PromotionResponse>> getListePromotion();
    @GET("api/Promotion/GetCodePromotion")
    Call<String> getCodePromotion(@Query("designationPromotion") String designationPromotion);
    @POST("api/Promotion/SavePromotion")
    Call<Reponse> SavePromotion(@Body PromotionResponse promotionResponse);
}

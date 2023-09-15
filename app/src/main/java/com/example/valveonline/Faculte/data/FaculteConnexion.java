package com.example.valveonline.Faculte.data;

import com.example.valveonline.DataBaseConnector.Reponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface FaculteConnexion {
    @GET("api/Faculte/GetListeFaculte")
    Call<List<FaculteReponse>> getListeFaculte();
    @GET("api/Faculte/GetCodeFaculte")
    Call<String> getCodeFaculte(@Query("designationFaculte") String designationFaculte);
    @POST("api/Faculte/SaveFaculte")
    Call<Reponse> SaveFaculte(@Body FaculteReponse faculteReponse);
}

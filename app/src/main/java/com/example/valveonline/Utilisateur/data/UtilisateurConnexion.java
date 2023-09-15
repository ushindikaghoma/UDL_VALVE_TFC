package com.example.valveonline.Utilisateur.data;

import com.example.valveonline.DataBaseConnector.Reponse;
import com.example.valveonline.Faculte.data.FaculteReponse;
import com.example.valveonline.Horaire.data.HoraireResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UtilisateurConnexion {
    @GET("api/Utilisateur/GetListeUtilisateur")
    Call<List<UtilisateurResponse>> getListeUtilisateur();
    @POST("api/Utilisateur/SaveUtilisateur")
    Call<Reponse> SaveUtilisateur(@Body UtilisateurResponse utilisateurResponse);
}

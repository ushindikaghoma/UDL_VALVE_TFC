package com.example.valveonline.Etudiant.data;

import com.google.gson.annotations.SerializedName;

public class EtudiantResponse {

    @SerializedName("idEtudiant")
    private int IdEtudiant ;
    @SerializedName("matriculeEtudiant")
    private String MatriculeEtudiant;
    @SerializedName("nomEtudiant")
    private String NomEtudiant ;
    @SerializedName("postnomEtudiant")
    private String PostnomEtudiant ;
    @SerializedName("prenomEtudiant")
    private String PrenomEtudiant ;
    @SerializedName("genre")
    private String Genre ;
    @SerializedName("adresse")
    private String Adresse ;
    @SerializedName("codeFaculte")
    private String CodeFaculte ;
    @SerializedName("codePromotion")
    private String CodePromotion ;

    public EtudiantResponse(int idEtudiant, String matriculeEtudiant,
                            String nomEtudiant, String postnomEtudiant,
                            String prenomEtudiant, String genre,
                            String adresse, String codeFaculte,
                            String codePromotion) {
        IdEtudiant = idEtudiant;
        MatriculeEtudiant = matriculeEtudiant;
        NomEtudiant = nomEtudiant;
        PostnomEtudiant = postnomEtudiant;
        PrenomEtudiant = prenomEtudiant;
        Genre = genre;
        Adresse = adresse;
        CodeFaculte = codeFaculte;
        CodePromotion = codePromotion;
    }

    public int getIdEtudiant() {
        return IdEtudiant;
    }

    public void setIdEtudiant(int idEtudiant) {
        IdEtudiant = idEtudiant;
    }

    public String getMatriculeEtudiant() {
        return MatriculeEtudiant;
    }

    public void setMatriculeEtudiant(String matriculeEtudiant) {
        MatriculeEtudiant = matriculeEtudiant;
    }

    public String getNomEtudiant() {
        return NomEtudiant;
    }

    public void setNomEtudiant(String nomEtudiant) {
        NomEtudiant = nomEtudiant;
    }

    public String getPostnomEtudiant() {
        return PostnomEtudiant;
    }

    public void setPostnomEtudiant(String postnomEtudiant) {
        PostnomEtudiant = postnomEtudiant;
    }

    public String getPrenomEtudiant() {
        return PrenomEtudiant;
    }

    public void setPrenomEtudiant(String prenomEtudiant) {
        PrenomEtudiant = prenomEtudiant;
    }

    public String getGenre() {
        return Genre;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }

    public String getAdresse() {
        return Adresse;
    }

    public void setAdresse(String adresse) {
        Adresse = adresse;
    }

    public String getCodeFaculte() {
        return CodeFaculte;
    }

    public void setCodeFaculte(String codeFaculte) {
        CodeFaculte = codeFaculte;
    }

    public String getCodePromotion() {
        return CodePromotion;
    }

    public void setCodePromotion(String codePromotion) {
        CodePromotion = codePromotion;
    }
}

package com.example.valveonline.Horaire.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class HoraireResponse implements Serializable {
    @SerializedName("idHoraire")
    private int IdHoraire;
    @SerializedName("codeCours")
    private String CodeCours;
    @SerializedName("dateHoraire")
    private String DateHoraire;
    @SerializedName("jourHoraire")
    private String JourHoraire;
    @SerializedName("codeFaculte")
    private String CodeFaculte;
    @SerializedName("codePromotion")
    private String CodePromotion;
    @SerializedName("designationCours")
    private String DesignationCours;
    @SerializedName("volumeCours")
    private String VolumeCours ;
    @SerializedName("heureDebut")
    private String HeureDebut;
    @SerializedName("heureFin")
    private String HeureFin;
    @SerializedName("etatHoraire")
    public int etatHoraire;

    public HoraireResponse(int idHoraire, String codeCours,
                           String dateHoraire, String jourHoraire,
                           String codeFaculte, String codePromotion,
                           String designationCours, String volumeCours) {
        IdHoraire = idHoraire;
        CodeCours = codeCours;
        DateHoraire = dateHoraire;
        JourHoraire = jourHoraire;
        CodeFaculte = codeFaculte;
        CodePromotion = codePromotion;
        DesignationCours = designationCours;
        VolumeCours = volumeCours;
    }

    public HoraireResponse(String codeFaculte, String codePromotion,
                           String designationCours, String codeCours,
                           String heureDebut, String heureFin,
                           String dateHoraire, String jourHoraire,
                           int idHoraire) {
        CodeFaculte = codeFaculte;
        CodePromotion = codePromotion;
        DesignationCours = designationCours;
        CodeCours = codeCours;
        HeureDebut = heureDebut;
        HeureFin = heureFin;
        DateHoraire = dateHoraire;
        JourHoraire = jourHoraire;
        IdHoraire = idHoraire;
    }

    public HoraireResponse() {
    }

    public String getHeureDebut() {
        return HeureDebut;
    }

    public int getEtatHoraire() {
        return etatHoraire;
    }

    public void setEtatHoraire(int etatHoraire) {
        this.etatHoraire = etatHoraire;
    }

    public void setHeureDebut(String heureDebut) {
        this.HeureDebut = heureDebut;
    }

    public String getHeureFin() {
        return HeureFin;
    }

    public void setHeureFin(String heureFin) {
        this.HeureFin = heureFin;
    }

    public int getIdHoraire() {
        return IdHoraire;
    }

    public void setIdHoraire(int idHoraire) {
        IdHoraire = idHoraire;
    }

    public String getCodeCours() {
        return CodeCours;
    }

    public void setCodeCours(String codeCours) {
        CodeCours = codeCours;
    }

    public String getDateHoraire() {
        return DateHoraire;
    }

    public void setDateHoraire(String dateHoraire) {
        DateHoraire = dateHoraire;
    }

    public String getJourHoraire() {
        return JourHoraire;
    }

    public void setJourHoraire(String jourHoraire) {
        JourHoraire = jourHoraire;
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

    public String getDesignationCours() {
        return DesignationCours;
    }

    public void setDesignationCours(String designationCours) {
        DesignationCours = designationCours;
    }

    public String getVolumeCours() {
        return VolumeCours;
    }

    public void setVolumeCours(String volumeCours) {
        VolumeCours = volumeCours;
    }
}

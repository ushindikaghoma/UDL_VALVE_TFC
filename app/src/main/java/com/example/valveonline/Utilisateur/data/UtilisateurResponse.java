package com.example.valveonline.Utilisateur.data;

import com.google.gson.annotations.SerializedName;

public class UtilisateurResponse {
    @SerializedName("idUtilisateur")
    private int IdUtilisateur;
    @SerializedName("nomUtilisateur")
    private String NomUtilisateur ;
    @SerializedName("motPasseUtilisateur")
    private String MotPasseUtilisateur;
    @SerializedName("niveauUtilisateur")
    private String NiveauUtilisateur ;
    @SerializedName("designationUtilisateur")
    public String DesignationUtilisateur;
    @SerializedName("fonctionUtilisateur")
    public String FonctionUtilisateur;
    @SerializedName("codeFaculte")
    public String CodeFaculte;
    @SerializedName("codePromotion")
    public String CodePromotion ;

    public UtilisateurResponse(int idUtilisateur, String nomUtilisateur,
                               String motPasseUtilisateur,
                               String niveauUtilisateur,
                               String designationUtilisateur,
                               String fonctionUtilisateur,
                               String codeFaculte, String codePromotion) {
        IdUtilisateur = idUtilisateur;
        NomUtilisateur = nomUtilisateur;
        MotPasseUtilisateur = motPasseUtilisateur;
        NiveauUtilisateur = niveauUtilisateur;
        DesignationUtilisateur = designationUtilisateur;
        FonctionUtilisateur = fonctionUtilisateur;
        CodeFaculte = codeFaculte;
        CodePromotion = codePromotion;
    }

    public UtilisateurResponse(String nomUtilisateur, String niveauUtilisateur,
                               String codeFaculte, String codePromotion,
                               int idUtilisateur, String motPasseUtilisateur) {
        NomUtilisateur = nomUtilisateur;
        NiveauUtilisateur = niveauUtilisateur;
        CodeFaculte = codeFaculte;
        CodePromotion = codePromotion;
        IdUtilisateur = idUtilisateur;
        MotPasseUtilisateur = motPasseUtilisateur;
    }

    public UtilisateurResponse() {
    }

    public int getIdUtilisateur() {
        return IdUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        IdUtilisateur = idUtilisateur;
    }

    public String getNomUtilisateur() {
        return NomUtilisateur;
    }

    public void setNomUtilisateur(String nomUtilisateur) {
        NomUtilisateur = nomUtilisateur;
    }

    public String getMotPasseUtilisateur() {
        return MotPasseUtilisateur;
    }

    public void setMotPasseUtilisateur(String motPasseUtilisateur) {
        MotPasseUtilisateur = motPasseUtilisateur;
    }

    public String getNiveauUtilisateur() {
        return NiveauUtilisateur;
    }

    public void setNiveauUtilisateur(String niveauUtilisateur) {
        NiveauUtilisateur = niveauUtilisateur;
    }

    public String getDesignationUtilisateur() {
        return DesignationUtilisateur;
    }

    public void setDesignationUtilisateur(String designationUtilisateur) {
        DesignationUtilisateur = designationUtilisateur;
    }

    public String getFonctionUtilisateur() {
        return FonctionUtilisateur;
    }

    public void setFonctionUtilisateur(String fonctionUtilisateur) {
        FonctionUtilisateur = fonctionUtilisateur;
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

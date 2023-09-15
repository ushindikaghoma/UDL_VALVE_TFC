package com.example.valveonline.Faculte.data;

import com.google.gson.annotations.SerializedName;

public class FaculteReponse {
    @SerializedName("codeFaculte")
    private String codeFaculte;
    @SerializedName("designationFaculte")
    private String designationFaculte;

    public FaculteReponse(String codeFaculte, String designationFaculte) {
        this.codeFaculte = codeFaculte;
        this.designationFaculte = designationFaculte;
    }

    public FaculteReponse() {
    }

    public String getCodeFaculte() {
        return codeFaculte;
    }

    public String getDesignationFaculte() {
        return designationFaculte;
    }

    public void setCodeFaculte(String codeFaculte) {
        this.codeFaculte = codeFaculte;
    }

    public void setDesignationFaculte(String designationFaculte) {
        this.designationFaculte = designationFaculte;
    }
}

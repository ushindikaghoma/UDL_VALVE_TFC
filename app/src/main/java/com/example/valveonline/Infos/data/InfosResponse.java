package com.example.valveonline.Infos.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class InfosResponse  implements Serializable {
    @SerializedName("idInfos")
    private int IdInfos;
    @SerializedName("titreInfos")
    private String TitreInfos;
    @SerializedName("etatInfos")
    private int  EtatInfos;
    @SerializedName("dateInfos")
    private String DateInfos;
    @SerializedName("descriptionInfos")
    private String  DesciptionInfos;


    public InfosResponse(String titreInfos, String dateInfos, String desciptionInfos,
                         int idInfos) {
        TitreInfos = titreInfos;
        DateInfos = dateInfos;
        DesciptionInfos = desciptionInfos;
        IdInfos = idInfos;
    }

    public InfosResponse() {
    }

    public int getIdInfos() {
        return IdInfos;
    }

    public void setIdInfos(int idInfos) {
        IdInfos = idInfos;
    }

    public String getTitreInfos() {
        return TitreInfos;
    }

    public void setTitreInfos(String titreInfos) {
        TitreInfos = titreInfos;
    }

    public int getEtatInfos() {
        return EtatInfos;
    }

    public void setEtatInfos(int etatInfos) {
        EtatInfos = etatInfos;
    }

    public String getDateInfos() {
        return DateInfos;
    }

    public void setDateInfos(String dateInfos) {
        DateInfos = dateInfos;
    }

    public String getDesciptionInfos() {
        return DesciptionInfos;
    }

    public void setDesciptionInfos(String desciptionInfos) {
        DesciptionInfos = desciptionInfos;
    }
}

package com.example.valveonline.Promotion.data;

import com.google.gson.annotations.SerializedName;

public class PromotionResponse {
    @SerializedName("codePromotion")
    private String codePromotion;
    @SerializedName("codeFaculte")
    private String codeFaculte;
    @SerializedName("designationPromotion")
    private String designationPromotion;

    public PromotionResponse(String codePromotion, String codeFaculte, String designationPromotion) {
        this.codePromotion = codePromotion;
        this.codeFaculte = codeFaculte;
        this.designationPromotion = designationPromotion;
    }

    public PromotionResponse() {
    }

    public String getCodePromotion() {
        return codePromotion;
    }

    public void setCodePromotion(String codePromotion) {
        this.codePromotion = codePromotion;
    }

    public String getCodeFaculte() {
        return codeFaculte;
    }

    public void setCodeFaculte(String codeFaculte) {
        this.codeFaculte = codeFaculte;
    }

    public String getDesignationPromotion() {
        return designationPromotion;
    }

    public void setDesignationPromotion(String designationPromotion) {
        this.designationPromotion = designationPromotion;
    }
}

package com.example.valveonline.DataBaseConnector;

import com.google.gson.annotations.SerializedName;

public class Reponse {
    @SerializedName("success")
    boolean success;
    @SerializedName("message")
    String message;

    public Reponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}

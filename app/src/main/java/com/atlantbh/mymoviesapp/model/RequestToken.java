package com.atlantbh.mymoviesapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class RequestToken {
    @SerializedName("expires_at")
    private Date expiresAt;
    @SerializedName("request_token")
    private String requestToken;
    @SerializedName("success")
    private boolean success;

    public Date getExpiresAt() {
        return expiresAt;
    }

    public String getRequestToken() {
        return requestToken;
    }

    public boolean getSuccess() {
        return success;
    }
}

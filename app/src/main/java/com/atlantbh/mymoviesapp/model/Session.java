package com.atlantbh.mymoviesapp.model;

import com.google.gson.annotations.SerializedName;

public class Session {
    @SerializedName("session_id")
    private String sessionId;
    @SerializedName("success")
    private boolean success;

    public String getSessionId() {
        return sessionId;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}

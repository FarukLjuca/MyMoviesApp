package com.atlantbh.mymoviesapp.model.credits;

import com.google.gson.annotations.SerializedName;

public class CreditsCrew extends Credits {
    @SerializedName("job")
    private String job;

    public String getJob() { return job; }
}

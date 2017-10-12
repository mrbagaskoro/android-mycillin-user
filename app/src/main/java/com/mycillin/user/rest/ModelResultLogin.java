package com.mycillin.user.rest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 16003041 on 12/10/2017.
 */

public class ModelResultLogin {

    @SerializedName("result")
    @Expose
    public ModelResultDataLogin result = new ModelResultDataLogin();

    public ModelResultDataLogin getResult() {
        return result;
    }

    public void setResult(ModelResultDataLogin result) {
        this.result = result;
    }
}
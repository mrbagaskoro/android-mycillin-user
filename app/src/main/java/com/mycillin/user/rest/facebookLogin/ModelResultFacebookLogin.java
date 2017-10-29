package com.mycillin.user.rest.facebookLogin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mrbagaskoro on 29-Oct-17.
 */

public class ModelResultFacebookLogin {

    @SerializedName("result")
    @Expose
    public ModelResultDataFacebookLogin result;

    public ModelResultDataFacebookLogin getResult() {
        return result;
    }

    public void setResult(ModelResultDataFacebookLogin result) {
        this.result = result;
    }
}

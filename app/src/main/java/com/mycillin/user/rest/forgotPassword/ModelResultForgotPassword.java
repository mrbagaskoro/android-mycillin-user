package com.mycillin.user.rest.forgotPassword;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mrbagaskoro on 15-Oct-17.
 */

public class ModelResultForgotPassword {

    @SerializedName("result")
    @Expose
    private ModelResultDataForgotPassword result;

    public ModelResultDataForgotPassword getResult() {
        return result;
    }

    public void setResult(ModelResultDataForgotPassword result) {
        this.result = result;
    }
}

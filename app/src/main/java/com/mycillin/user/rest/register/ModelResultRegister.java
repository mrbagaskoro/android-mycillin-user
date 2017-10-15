package com.mycillin.user.rest.register;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mrbagaskoro on 15-Oct-17.
 */

public class ModelResultRegister {

    @SerializedName("result")
    @Expose
    private ModelResultDataRegister result;

    public ModelResultDataRegister getResult() {
        return result;
    }

    public void setResult(ModelResultDataRegister result) {
        this.result = result;
    }
}

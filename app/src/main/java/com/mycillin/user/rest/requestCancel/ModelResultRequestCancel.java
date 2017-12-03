package com.mycillin.user.rest.requestCancel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mrbagaskoro on 03-Dec-17.
 */

public class ModelResultRequestCancel {

    @SerializedName("result")
    @Expose
    private ModelResultDataRequestCancel result;

    public ModelResultDataRequestCancel getResult() {
        return result;
    }

    public void setResult(ModelResultDataRequestCancel result) {
        this.result = result;
    }
}

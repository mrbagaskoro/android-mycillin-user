package com.mycillin.user.rest.requestTransaction;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mrbagaskoro on 02-Dec-17.
 */

public class ModelResultRequestTransaction {

    @SerializedName("result")
    @Expose
    private ModelResultDataRequestTransaction result;

    public ModelResultDataRequestTransaction getResult() {
        return result;
    }

    public void setResult(ModelResultDataRequestTransaction result) {
        this.result = result;
    }
}

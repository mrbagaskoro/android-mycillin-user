package com.mycillin.user.rest.emailReceipt;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mrbagaskoro on 06-Jan-18.
 */

public class ModelResultEmailReceipt {

    @SerializedName("result")
    @Expose
    private ModelResultDataEmailReceipt result;

    public ModelResultDataEmailReceipt getResult() {
        return result;
    }

    public void setResult(ModelResultDataEmailReceipt result) {
        this.result = result;
    }
}

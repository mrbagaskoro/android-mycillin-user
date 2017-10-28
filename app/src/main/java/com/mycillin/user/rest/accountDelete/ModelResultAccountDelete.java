package com.mycillin.user.rest.accountDelete;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mrbagaskoro on 28-Oct-17.
 */

public class ModelResultAccountDelete {

    @SerializedName("result")
    @Expose
    private ModelResultDataAccountDelete result;

    public ModelResultDataAccountDelete getResult() {
        return result;
    }

    public void setResult(ModelResultDataAccountDelete result) {
        this.result = result;
    }
}

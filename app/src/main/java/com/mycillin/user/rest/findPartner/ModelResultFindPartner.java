package com.mycillin.user.rest.findPartner;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mrbagaskoro on 01-Dec-17.
 */

public class ModelResultFindPartner {

    @SerializedName("result")
    @Expose
    private ModelResultDataFindPartner result;

    public ModelResultDataFindPartner getResult() {
        return result;
    }

    public void setResult(ModelResultDataFindPartner result) {
        this.result = result;
    }
}

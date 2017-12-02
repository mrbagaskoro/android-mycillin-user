package com.mycillin.user.rest.findConsultation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mrbagaskoro on 02-Dec-17.
 */

public class ModelResultFindConsultation {

    @SerializedName("result")
    @Expose
    private ModelResultDataFindConsultation result;

    public ModelResultDataFindConsultation getResult() {
        return result;
    }

    public void setResult(ModelResultDataFindConsultation result) {
        this.result = result;
    }
}

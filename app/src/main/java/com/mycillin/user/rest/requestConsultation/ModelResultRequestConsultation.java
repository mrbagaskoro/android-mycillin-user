package com.mycillin.user.rest.requestConsultation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mrbagaskoro on 02-Dec-17.
 */

public class ModelResultRequestConsultation {

    @SerializedName("result")
    @Expose
    private ModelResultDataRequestConsultation result;

    public ModelResultDataRequestConsultation getResult() {
        return result;
    }

    public void setResult(ModelResultDataRequestConsultation result) {
        this.result = result;
    }
}

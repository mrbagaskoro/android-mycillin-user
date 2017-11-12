package com.mycillin.user.rest.insuranceList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mrbagaskoro on 12-Nov-17.
 */

public class ModelResultInsuranceList {

    @SerializedName("result")
    @Expose
    private ModelResultDataInsuranceList result;

    public ModelResultDataInsuranceList getResult() {
        return result;
    }

    public void setResult(ModelResultDataInsuranceList result) {
        this.result = result;
    }
}

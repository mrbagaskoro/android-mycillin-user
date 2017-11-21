package com.mycillin.user.rest.insuranceDelete;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 16003041 on 21/11/2017.
 */

public class ModelResultInsuranceDelete {

    @SerializedName("result")
    @Expose
    private ModelResultDataInsuranceDelete result;

    public ModelResultDataInsuranceDelete getResult() {
        return result;
    }

    public void setResult(ModelResultDataInsuranceDelete result) {
        this.result = result;
    }
}

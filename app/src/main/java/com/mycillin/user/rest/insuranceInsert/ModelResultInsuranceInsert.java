package com.mycillin.user.rest.insuranceInsert;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mrbagaskoro on 18-Nov-17.
 */

public class ModelResultInsuranceInsert {

    @SerializedName("result")
    @Expose
    private ModelResultDataInsuranceInsert result;

    public ModelResultDataInsuranceInsert getResult() {
        return result;
    }

    public void setResult(ModelResultDataInsuranceInsert result) {
        this.result = result;
    }
}

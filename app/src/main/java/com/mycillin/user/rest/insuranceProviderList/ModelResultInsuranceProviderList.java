package com.mycillin.user.rest.insuranceProviderList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mrbagaskoro on 28-Oct-17.
 */

public class ModelResultInsuranceProviderList {

    @SerializedName("result")
    @Expose
    private ModelResultDataInsuranceProviderList result;

    public ModelResultDataInsuranceProviderList getResult() {
        return result;
    }

    public void setResult(ModelResultDataInsuranceProviderList result) {
        this.result = result;
    }
}

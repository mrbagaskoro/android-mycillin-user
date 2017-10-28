package com.mycillin.user.rest.prescriptionTypeList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mrbagaskoro on 28-Oct-17.
 */

public class ModelResultPrescriptionTypeList {

    @SerializedName("result")
    @Expose
    private ModelResultDataPrescriptionTypeList result;

    public ModelResultDataPrescriptionTypeList getResult() {
        return result;
    }

    public void setResult(ModelResultDataPrescriptionTypeList result) {
        this.result = result;
    }
}

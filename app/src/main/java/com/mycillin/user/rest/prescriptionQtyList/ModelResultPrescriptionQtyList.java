package com.mycillin.user.rest.prescriptionQtyList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mrbagaskoro on 28-Oct-17.
 */

public class ModelResultPrescriptionQtyList {

    @SerializedName("result")
    @Expose
    private ModelResultDataPrescriptionQtyList result;

    public ModelResultDataPrescriptionQtyList getResult() {
        return result;
    }

    public void setResult(ModelResultDataPrescriptionQtyList result) {
        this.result = result;
    }
}

package com.mycillin.user.rest.prescriptionRecordList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mrbagaskoro on 05-Nov-17.
 */

public class ModelResultPrescriptionRecordList {

    @SerializedName("result")
    @Expose
    private ModelResultDataPrescriptionRecordList result;

    public ModelResultDataPrescriptionRecordList getResult() {
        return result;
    }

    public void setResult(ModelResultDataPrescriptionRecordList result) {
        this.result = result;
    }
}

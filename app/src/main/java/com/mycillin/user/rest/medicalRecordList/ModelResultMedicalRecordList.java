package com.mycillin.user.rest.medicalRecordList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 16003041 on 02/11/2017.
 */

public class ModelResultMedicalRecordList {

    @SerializedName("result")
    @Expose
    private ModelResultDataMedicalRecordList result;

    public ModelResultDataMedicalRecordList getResult() {
        return result;
    }

    public void setResult(ModelResultDataMedicalRecordList result) {
        this.result = result;
    }
}

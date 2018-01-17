package com.mycillin.user.rest.emailMedicalRecord;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 16003041 on 17/01/2018.
 */

public class ModelResultEmailMedicalRecord {

    @SerializedName("result")
    @Expose
    private ModelResultDataEmailMedicalRecord result;

    public ModelResultDataEmailMedicalRecord getResult() {
        return result;
    }

    public void setResult(ModelResultDataEmailMedicalRecord result) {
        this.result = result;
    }
}

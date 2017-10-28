package com.mycillin.user.rest.specializationList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mrbagaskoro on 28-Oct-17.
 */

public class ModelResultSpecializationList {

    @SerializedName("result")
    @Expose
    private ModelResultDataSpecializationList result;

    public ModelResultDataSpecializationList getResult() {
        return result;
    }

    public void setResult(ModelResultDataSpecializationList result) {
        this.result = result;
    }
}

package com.mycillin.user.rest.dosageList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mrbagaskoro on 28-Oct-17.
 */

public class ModelResultDosageList {

    @SerializedName("result")
    @Expose
    private ModelResultDataDosageList result;

    public ModelResultDataDosageList getResult() {
        return result;
    }

    public void setResult(ModelResultDataDosageList result) {
        this.result = result;
    }
}

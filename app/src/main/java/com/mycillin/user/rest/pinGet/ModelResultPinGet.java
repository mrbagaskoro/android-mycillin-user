package com.mycillin.user.rest.pinGet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mrbagaskoro on 16-Dec-17.
 */

public class ModelResultPinGet {

    @SerializedName("result")
    @Expose
    private ModelResultDataPinGet result;

    public ModelResultDataPinGet getResult() {
        return result;
    }

    public void setResult(ModelResultDataPinGet result) {
        this.result = result;
    }
}

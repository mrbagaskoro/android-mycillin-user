package com.mycillin.user.rest.findFacility;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mrbagaskoro on 16-Dec-17.
 */

public class ModelResultFindFacility {

    @SerializedName("result")
    @Expose
    private ModelResultDataFindFacility result;

    public ModelResultDataFindFacility getResult() {
        return result;
    }

    public void setResult(ModelResultDataFindFacility result) {
        this.result = result;
    }
}

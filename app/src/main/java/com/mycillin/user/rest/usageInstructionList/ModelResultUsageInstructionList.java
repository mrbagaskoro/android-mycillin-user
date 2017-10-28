package com.mycillin.user.rest.usageInstructionList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mrbagaskoro on 28-Oct-17.
 */

public class ModelResultUsageInstructionList {

    @SerializedName("result")
    @Expose
    private ModelResultDataUsageInstructionList result;

    public ModelResultDataUsageInstructionList getResult() {
        return result;
    }

    public void setResult(ModelResultDataUsageInstructionList result) {
        this.result = result;
    }
}

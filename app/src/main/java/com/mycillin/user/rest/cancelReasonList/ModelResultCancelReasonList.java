package com.mycillin.user.rest.cancelReasonList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mrbagaskoro on 28-Oct-17.
 */

public class ModelResultCancelReasonList {

    @SerializedName("result")
    @Expose
    private ModelResultDataCancelReasonList result;

    public ModelResultDataCancelReasonList getResult() {
        return result;
    }

    public void setResult(ModelResultDataCancelReasonList result) {
        this.result = result;
    }
}

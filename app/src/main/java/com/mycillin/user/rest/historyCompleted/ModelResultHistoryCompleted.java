package com.mycillin.user.rest.historyCompleted;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mrbagaskoro on 03-Dec-17.
 */

public class ModelResultHistoryCompleted {

    @SerializedName("result")
    @Expose
    private ModelResultDataHistoryCompleted result;

    public ModelResultDataHistoryCompleted getResult() {
        return result;
    }

    public void setResult(ModelResultDataHistoryCompleted result) {
        this.result = result;
    }
}

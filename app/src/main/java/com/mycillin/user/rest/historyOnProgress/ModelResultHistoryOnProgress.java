package com.mycillin.user.rest.historyOnProgress;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mrbagaskoro on 03-Dec-17.
 */

public class ModelResultHistoryOnProgress {

    @SerializedName("result")
    @Expose
    private ModelResultDataHistoryOnProgress result;

    public ModelResultDataHistoryOnProgress getResult() {
        return result;
    }

    public void setResult(ModelResultDataHistoryOnProgress result) {
        this.result = result;
    }
}

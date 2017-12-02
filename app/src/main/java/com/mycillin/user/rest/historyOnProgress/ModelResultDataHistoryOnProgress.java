package com.mycillin.user.rest.historyOnProgress;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mrbagaskoro on 03-Dec-17.
 */

public class ModelResultDataHistoryOnProgress {

    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("data")
    @Expose
    private List<ModelDataHistoryOnProgress> data = null;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<ModelDataHistoryOnProgress> getData() {
        return data;
    }

    public void setData(List<ModelDataHistoryOnProgress> data) {
        this.data = data;
    }
}

package com.mycillin.user.rest.historyCompleted;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mrbagaskoro on 03-Dec-17.
 */

public class ModelResultDataHistoryCompleted {

    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("data")
    @Expose
    private List<ModelDataHistoryCompleted> data = null;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<ModelDataHistoryCompleted> getData() {
        return data;
    }

    public void setData(List<ModelDataHistoryCompleted> data) {
        this.data = data;
    }
}

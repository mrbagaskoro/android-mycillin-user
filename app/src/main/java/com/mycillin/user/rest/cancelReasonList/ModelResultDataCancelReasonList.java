package com.mycillin.user.rest.cancelReasonList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mrbagaskoro on 28-Oct-17.
 */

public class ModelResultDataCancelReasonList {

    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("data")
    @Expose
    private List<ModelDataCancelReasonList> data = null;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<ModelDataCancelReasonList> getData() {
        return data;
    }

    public void setData(List<ModelDataCancelReasonList> data) {
        this.data = data;
    }
}

package com.mycillin.user.rest.usageInstructionList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mrbagaskoro on 28-Oct-17.
 */

public class ModelResultDataUsageInstructionList {

    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("data")
    @Expose
    private List<ModelDataUsageInstructionList> data = null;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<ModelDataUsageInstructionList> getData() {
        return data;
    }

    public void setData(List<ModelDataUsageInstructionList> data) {
        this.data = data;
    }
}

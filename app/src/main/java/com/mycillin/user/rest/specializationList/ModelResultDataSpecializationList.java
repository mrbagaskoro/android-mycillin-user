package com.mycillin.user.rest.specializationList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mrbagaskoro on 28-Oct-17.
 */

public class ModelResultDataSpecializationList {

    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("data")
    @Expose
    private List<ModelDataSpecializationList> data = null;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<ModelDataSpecializationList> getData() {
        return data;
    }

    public void setData(List<ModelDataSpecializationList> data) {
        this.data = data;
    }
}

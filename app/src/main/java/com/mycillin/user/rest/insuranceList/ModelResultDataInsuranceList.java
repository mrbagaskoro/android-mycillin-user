package com.mycillin.user.rest.insuranceList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mrbagaskoro on 12-Nov-17.
 */

public class ModelResultDataInsuranceList {

    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("data")
    @Expose
    private List<ModelDataInsuranceList> data = null;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<ModelDataInsuranceList> getData() {
        return data;
    }

    public void setData(List<ModelDataInsuranceList> data) {
        this.data = data;
    }
}

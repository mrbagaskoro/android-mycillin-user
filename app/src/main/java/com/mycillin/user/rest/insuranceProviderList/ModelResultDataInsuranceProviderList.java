package com.mycillin.user.rest.insuranceProviderList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mrbagaskoro on 28-Oct-17.
 */

public class ModelResultDataInsuranceProviderList {

    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("data")
    @Expose
    private List<ModelDataInsuranceProviderList> data = null;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<ModelDataInsuranceProviderList> getData() {
        return data;
    }

    public void setData(List<ModelDataInsuranceProviderList> data) {
        this.data = data;
    }
}

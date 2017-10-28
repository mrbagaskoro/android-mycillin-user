package com.mycillin.user.rest.prescriptionTypeList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mrbagaskoro on 28-Oct-17.
 */

public class ModelResultDataPrescriptionTypeList {

    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("data")
    @Expose
    private List<ModelDataPrescriptionTypeList> data = null;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<ModelDataPrescriptionTypeList> getData() {
        return data;
    }

    public void setData(List<ModelDataPrescriptionTypeList> data) {
        this.data = data;
    }
}

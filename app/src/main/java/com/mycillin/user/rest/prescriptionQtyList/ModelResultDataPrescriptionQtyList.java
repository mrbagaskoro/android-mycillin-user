package com.mycillin.user.rest.prescriptionQtyList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mrbagaskoro on 28-Oct-17.
 */

public class ModelResultDataPrescriptionQtyList {

    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("data")
    @Expose
    private List<ModelDataPrescriptionQtyList> data = null;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<ModelDataPrescriptionQtyList> getData() {
        return data;
    }

    public void setData(List<ModelDataPrescriptionQtyList> data) {
        this.data = data;
    }
}

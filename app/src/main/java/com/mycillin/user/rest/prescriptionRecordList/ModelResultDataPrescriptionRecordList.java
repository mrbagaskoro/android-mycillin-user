package com.mycillin.user.rest.prescriptionRecordList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mrbagaskoro on 05-Nov-17.
 */

public class ModelResultDataPrescriptionRecordList {

    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("data")
    @Expose
    private List<ModelDataPrescriptionRecordList> data = null;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<ModelDataPrescriptionRecordList> getData() {
        return data;
    }

    public void setData(List<ModelDataPrescriptionRecordList> data) {
        this.data = data;
    }
}

package com.mycillin.user.rest.medicalRecordList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 16003041 on 02/11/2017.
 */

public class ModelResultDataMedicalRecordList {

    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("data")
    @Expose
    private List<ModelDataMedicalRecordList> data = null;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<ModelDataMedicalRecordList> getData() {
        return data;
    }

    public void setData(List<ModelDataMedicalRecordList> data) {
        this.data = data;
    }
}

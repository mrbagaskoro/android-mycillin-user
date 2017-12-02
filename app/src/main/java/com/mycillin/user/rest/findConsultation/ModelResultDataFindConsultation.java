package com.mycillin.user.rest.findConsultation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mrbagaskoro on 02-Dec-17.
 */

public class ModelResultDataFindConsultation {

    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("data")
    @Expose
    private List<ModelDataFindConsultation> data = null;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<ModelDataFindConsultation> getData() {
        return data;
    }

    public void setData(List<ModelDataFindConsultation> data) {
        this.data = data;
    }
}

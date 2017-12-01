package com.mycillin.user.rest.findPartner;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mrbagaskoro on 01-Dec-17.
 */

public class ModelResultDataFindPartner {

    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("data")
    @Expose
    private List<ModelDataFindPartner> data = null;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<ModelDataFindPartner> getData() {
        return data;
    }

    public void setData(List<ModelDataFindPartner> data) {
        this.data = data;
    }
}

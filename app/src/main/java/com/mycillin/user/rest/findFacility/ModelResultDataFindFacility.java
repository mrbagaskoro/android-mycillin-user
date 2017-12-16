package com.mycillin.user.rest.findFacility;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mrbagaskoro on 16-Dec-17.
 */

public class ModelResultDataFindFacility {

    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("data")
    @Expose
    private List<ModelDataFindFacility> data = null;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<ModelDataFindFacility> getData() {
        return data;
    }

    public void setData(List<ModelDataFindFacility> data) {
        this.data = data;
    }
}

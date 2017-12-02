package com.mycillin.user.rest.findClinic;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mrbagaskoro on 02-Dec-17.
 */

public class ModelResultDataFindClinic {

    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("data")
    @Expose
    private List<ModelDataFindClinic> data = null;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<ModelDataFindClinic> getData() {
        return data;
    }

    public void setData(List<ModelDataFindClinic> data) {
        this.data = data;
    }
}

package com.mycillin.user.rest.accountPicGet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mrbagaskoro on 28-Oct-17.
 */

public class ModelResultDataAccountPicGet {

    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("data")
    @Expose
    private List<ModelDataAccountPicGet> data = null;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<ModelDataAccountPicGet> getData() {
        return data;
    }

    public void setData(List<ModelDataAccountPicGet> data) {
        this.data = data;
    }
}

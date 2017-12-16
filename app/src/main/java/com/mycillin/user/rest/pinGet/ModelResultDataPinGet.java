package com.mycillin.user.rest.pinGet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mrbagaskoro on 16-Dec-17.
 */

public class ModelResultDataPinGet {

    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("data")
    @Expose
    private ModelDataPinGet data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ModelDataPinGet getData() {
        return data;
    }

    public void setData(ModelDataPinGet data) {
        this.data = data;
    }
}

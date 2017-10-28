package com.mycillin.user.rest.accountDelete;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mrbagaskoro on 28-Oct-17.
 */

public class ModelResultDataAccountDelete {

    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("data")
    @Expose
    private List<ModelDataAccountDelete> data = null;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<ModelDataAccountDelete> getData() {
        return data;
    }

    public void setData(List<ModelDataAccountDelete> data) {
        this.data = data;
    }
}

package com.mycillin.user.rest.findHealthcare;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mrbagaskoro on 02-Dec-17.
 */

public class ModelResultDataFindHomecare {

    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("data")
    @Expose
    private List<ModelDataFindHomecare> data = null;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<ModelDataFindHomecare> getData() {
        return data;
    }

    public void setData(List<ModelDataFindHomecare> data) {
        this.data = data;
    }
}

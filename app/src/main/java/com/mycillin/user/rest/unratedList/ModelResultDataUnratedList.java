package com.mycillin.user.rest.unratedList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 16003041 on 21/11/2017.
 */

public class ModelResultDataUnratedList {

    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("data")
    @Expose
    private List<ModelDataUnratedList> data = null;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<ModelDataUnratedList> getData() {
        return data;
    }

    public void setData(List<ModelDataUnratedList> data) {
        this.data = data;
    }
}

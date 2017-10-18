package com.mycillin.user.rest.relationList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mrbagaskoro on 18-Oct-17.
 */

public class ModelResultDataRelationList {

    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("data")
    @Expose
    private List<ModelDataRelationList> data = null;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<ModelDataRelationList> getData() {
        return data;
    }

    public void setData(List<ModelDataRelationList> data) {
        this.data = data;
    }
}

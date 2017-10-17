package com.mycillin.user.rest.accountList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 16003041 on 17/10/2017.
 */

public class ModelResultDataAccountList {

    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("data")
    @Expose
    private List<ModelDataAccountList> data = null;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<ModelDataAccountList> getData() {
        return data;
    }

    public void setData(List<ModelDataAccountList> data) {
        this.data = data;
    }
}

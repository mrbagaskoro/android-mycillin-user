package com.mycillin.user.rest.partnerTypeList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mrbagaskoro on 28-Oct-17.
 */

public class ModelResultDataPartnerTypeList {

    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("data")
    @Expose
    private List<ModelDataPartnerTypeList> data = null;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<ModelDataPartnerTypeList> getData() {
        return data;
    }

    public void setData(List<ModelDataPartnerTypeList> data) {
        this.data = data;
    }
}

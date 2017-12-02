package com.mycillin.user.rest.partnerDetailGet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mrbagaskoro on 02-Dec-17.
 */

public class ModelResultDataPartnerDetailGet {

    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("data")
    @Expose
    private List<ModelDataPartnerDetailGet> data = null;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<ModelDataPartnerDetailGet> getData() {
        return data;
    }

    public void setData(List<ModelDataPartnerDetailGet> data) {
        this.data = data;
    }
}

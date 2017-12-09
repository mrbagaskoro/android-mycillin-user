package com.mycillin.user.rest.priceGet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mrbagaskoro on 09-Dec-17.
 */

public class ModelResultDataPriceGet {

    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("data")
    @Expose
    private List<ModelDataPriceGet> data = null;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<ModelDataPriceGet> getData() {
        return data;
    }

    public void setData(List<ModelDataPriceGet> data) {
        this.data = data;
    }
}

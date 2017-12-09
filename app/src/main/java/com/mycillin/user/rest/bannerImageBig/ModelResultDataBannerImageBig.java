package com.mycillin.user.rest.bannerImageBig;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mycillin.user.database.BannerBig;

import java.util.List;

/**
 * Created by mrbagaskoro on 09-Dec-17.
 */

public class ModelResultDataBannerImageBig {


    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("data")
    @Expose
    private List<BannerBig> data = null;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<BannerBig> getData() {
        return data;
    }

    public void setData(List<BannerBig> data) {
        this.data = data;
    }
}

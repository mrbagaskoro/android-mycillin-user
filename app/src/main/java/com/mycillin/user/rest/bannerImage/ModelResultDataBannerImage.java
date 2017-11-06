package com.mycillin.user.rest.bannerImage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mycillin.user.database.Banner;

import java.util.List;

/**
 * Created by 16003041 on 06/11/2017.
 */

public class ModelResultDataBannerImage {

    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("data")
    @Expose
    private List<Banner> data = null;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<Banner> getData() {
        return data;
    }

    public void setData(List<Banner> data) {
        this.data = data;
    }
}

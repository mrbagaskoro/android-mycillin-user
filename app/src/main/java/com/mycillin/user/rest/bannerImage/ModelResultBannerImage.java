package com.mycillin.user.rest.bannerImage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 16003041 on 06/11/2017.
 */

public class ModelResultBannerImage {

    @SerializedName("result")
    @Expose
    private ModelResultDataBannerImage result;

    public ModelResultDataBannerImage getResult() {
        return result;
    }

    public void setResult(ModelResultDataBannerImage result) {
        this.result = result;
    }
}

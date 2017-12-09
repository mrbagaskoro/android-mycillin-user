package com.mycillin.user.rest.bannerImageBig;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mrbagaskoro on 09-Dec-17.
 */

public class ModelResultBannerImageBig {

    @SerializedName("result")
    @Expose
    private ModelResultDataBannerImageBig result;

    public ModelResultDataBannerImageBig getResult() {
        return result;
    }

    public void setResult(ModelResultDataBannerImageBig result) {
        this.result = result;
    }
}

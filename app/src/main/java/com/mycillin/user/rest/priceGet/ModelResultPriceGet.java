package com.mycillin.user.rest.priceGet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mrbagaskoro on 09-Dec-17.
 */

public class ModelResultPriceGet {

    @SerializedName("result")
    @Expose
    private ModelResultDataPriceGet result;

    public ModelResultDataPriceGet getResult() {
        return result;
    }

    public void setResult(ModelResultDataPriceGet result) {
        this.result = result;
    }
}

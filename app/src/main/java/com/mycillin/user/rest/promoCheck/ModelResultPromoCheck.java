package com.mycillin.user.rest.promoCheck;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 16003041 on 19/01/2018.
 */

public class ModelResultPromoCheck {

    @SerializedName("result")
    @Expose
    private ModelResultDataPromoCheck result;

    public ModelResultDataPromoCheck getResult() {
        return result;
    }

    public void setResult(ModelResultDataPromoCheck result) {
        this.result = result;
    }
}

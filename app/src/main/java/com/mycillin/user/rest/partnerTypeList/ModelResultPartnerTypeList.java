package com.mycillin.user.rest.partnerTypeList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mrbagaskoro on 28-Oct-17.
 */

public class ModelResultPartnerTypeList {

    @SerializedName("result")
    @Expose
    private ModelResultDataPartnerTypeList result;

    public ModelResultDataPartnerTypeList getResult() {
        return result;
    }

    public void setResult(ModelResultDataPartnerTypeList result) {
        this.result = result;
    }
}

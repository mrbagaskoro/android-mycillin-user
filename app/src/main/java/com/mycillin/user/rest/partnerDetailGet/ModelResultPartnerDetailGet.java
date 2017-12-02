package com.mycillin.user.rest.partnerDetailGet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mrbagaskoro on 02-Dec-17.
 */

public class ModelResultPartnerDetailGet {

    @SerializedName("result")
    @Expose
    private ModelResultDataPartnerDetailGet result;

    public ModelResultDataPartnerDetailGet getResult() {
        return result;
    }

    public void setResult(ModelResultDataPartnerDetailGet result) {
        this.result = result;
    }
}

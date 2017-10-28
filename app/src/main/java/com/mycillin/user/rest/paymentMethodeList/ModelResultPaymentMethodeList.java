package com.mycillin.user.rest.paymentMethodeList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mrbagaskoro on 28-Oct-17.
 */

public class ModelResultPaymentMethodeList {

    @SerializedName("result")
    @Expose
    private ModelResultDataPaymentMethodeList result;

    public ModelResultDataPaymentMethodeList getResult() {
        return result;
    }

    public void setResult(ModelResultDataPaymentMethodeList result) {
        this.result = result;
    }
}

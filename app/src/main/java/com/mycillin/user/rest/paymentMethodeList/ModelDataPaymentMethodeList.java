package com.mycillin.user.rest.paymentMethodeList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mrbagaskoro on 28-Oct-17.
 */

public class ModelDataPaymentMethodeList {

    @SerializedName("pymt_methode_id")
    @Expose
    private String paymentMethodeId;
    @SerializedName("pymt_methode_desc")
    @Expose
    private String paymentMethodeDesc;

    public String getPaymentMethodeId() {
        return paymentMethodeId;
    }

    public void setPaymentMethodeId(String paymentMethodeId) {
        this.paymentMethodeId = paymentMethodeId;
    }

    public String getPaymentMethodeDesc() {
        return paymentMethodeDesc;
    }

    public void setPaymentMethodeDesc(String paymentMethodeDesc) {
        this.paymentMethodeDesc = paymentMethodeDesc;
    }
}

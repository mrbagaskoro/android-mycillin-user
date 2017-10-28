package com.mycillin.user.rest.paymentMethodeList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mrbagaskoro on 28-Oct-17.
 */

public class ModelResultDataPaymentMethodeList {

    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("data")
    @Expose
    private List<ModelDataPaymentMethodeList> data = null;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<ModelDataPaymentMethodeList> getData() {
        return data;
    }

    public void setData(List<ModelDataPaymentMethodeList> data) {
        this.data = data;
    }
}

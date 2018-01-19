package com.mycillin.user.rest.promoCheck;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 16003041 on 19/01/2018.
 */

public class ModelResultDataPromoCheck {

    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("data")
    @Expose
    private List<ModelDataPromoCheck> data = null;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<ModelDataPromoCheck> getData() {
        return data;
    }

    public void setData(List<ModelDataPromoCheck> data) {
        this.data = data;
    }
}

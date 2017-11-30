package com.mycillin.user.rest.accountPicUpdate;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 16003041 on 30/11/2017.
 */

public class ModelResultDataAccountPicUpdate {

    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("message")
    @Expose
    private List<ModelDataAccountPicUpdate> message = null;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<ModelDataAccountPicUpdate> getMessage() {
        return message;
    }

    public void setMessage(List<ModelDataAccountPicUpdate> message) {
        this.message = message;
    }
}

package com.mycillin.user.rest.accountPicGet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mrbagaskoro on 28-Oct-17.
 */

public class ModelResultDataAccountPicGet {

    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("message")
    @Expose
    private List<ModelMessageAccountList> message = null;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<ModelMessageAccountList> getMessage() {
        return message;
    }

    public void setMessage(List<ModelMessageAccountList> message) {
        this.message = message;
    }
}

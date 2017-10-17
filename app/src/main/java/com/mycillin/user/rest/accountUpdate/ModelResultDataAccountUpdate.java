package com.mycillin.user.rest.accountUpdate;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 16003041 on 17/10/2017.
 */

public class ModelResultDataAccountUpdate {

    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("message")
    @Expose
    private String message;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

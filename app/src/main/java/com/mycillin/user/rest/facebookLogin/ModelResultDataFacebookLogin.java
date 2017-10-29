package com.mycillin.user.rest.facebookLogin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mrbagaskoro on 29-Oct-17.
 */

public class ModelResultDataFacebookLogin {

    @SerializedName("status")
    @Expose
    public boolean status;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("data")
    @Expose
    public ModelDataFacebookLogin data = new ModelDataFacebookLogin();
    @SerializedName("token")
    @Expose
    public String token;

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

    public ModelDataFacebookLogin getData() {
        return data;
    }

    public void setData(ModelDataFacebookLogin data) {
        this.data = data;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

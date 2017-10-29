package com.mycillin.user.rest.facebookLogin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mrbagaskoro on 29-Oct-17.
 */

public class ModelDataFacebookLogin {

    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("full_name")
    @Expose
    public String fullName;
    @SerializedName("user_id")
    @Expose
    public String userId;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

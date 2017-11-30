package com.mycillin.user.rest.accountPicUpdate;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 16003041 on 30/11/2017.
 */

public class ModelDataAccountPicUpdate {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("image_profile")
    @Expose
    private String imageProfile;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getImageProfile() {
        return imageProfile;
    }

    public void setImageProfile(String imageProfile) {
        this.imageProfile = imageProfile;
    }
}

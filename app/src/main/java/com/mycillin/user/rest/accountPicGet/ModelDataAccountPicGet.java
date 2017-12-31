package com.mycillin.user.rest.accountPicGet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mrbagaskoro on 28-Oct-17.
 */

public class ModelDataAccountPicGet {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("image_profile")
    @Expose
    private String imageProfile;
    @SerializedName("base_data")
    @Expose
    private String baseData;

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

    public String getBaseData() {
        return baseData;
    }

    public void setBaseData(String baseData) {
        this.baseData = baseData;
    }
}

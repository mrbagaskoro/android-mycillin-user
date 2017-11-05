package com.mycillin.user.rest.specializationList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mrbagaskoro on 28-Oct-17.
 */

public class ModelDataSpecializationList {

    @SerializedName("spesialisasi_id")
    @Expose
    private String specializationId;
    @SerializedName("spesialisasi_desc")
    @Expose
    private String specializationDesc;

    public String getSpecializationId() {
        return specializationId;
    }

    public void setSpecializationId(String specializationId) {
        this.specializationId = specializationId;
    }

    public String getSpecializationDesc() {
        return specializationDesc;
    }

    public void setSpecializationDesc(String specializationDesc) {
        this.specializationDesc = specializationDesc;
    }
}

package com.mycillin.user.rest.insuranceProviderList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mrbagaskoro on 28-Oct-17.
 */

public class ModelDataInsuranceProviderList {

    @SerializedName("insr_provider_id")
    @Expose
    private String insuranceProviderId;
    @SerializedName("insr_provider_desc")
    @Expose
    private String insuranceProviderDesc;

    public String getInsuranceProviderId() {
        return insuranceProviderId;
    }

    public void setInsuranceProviderId(String insuranceProviderId) {
        this.insuranceProviderId = insuranceProviderId;
    }

    public String getInsuranceProviderDesc() {
        return insuranceProviderDesc;
    }

    public void setInsuranceProviderDesc(String insuranceProviderDesc) {
        this.insuranceProviderDesc = insuranceProviderDesc;
    }
}

package com.mycillin.user.rest.insuranceList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mrbagaskoro on 12-Nov-17.
 */

public class ModelDataInsuranceList {

    @SerializedName("member_insr_id")
    @Expose
    private String memberInsuranceId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("relation_id")
    @Expose
    private String relationId;
    @SerializedName("no_polis_insr")
    @Expose
    private String policyNo;
    @SerializedName("insr_provider_id")
    @Expose
    private String insuranceProviderId;
    @SerializedName("nama_asuransi")
    @Expose
    private String insuranceProviderDesc;
    @SerializedName("nama_tertanggung")
    @Expose
    private String insuredName;
    @SerializedName("nama_pemilik_insr")
    @Expose
    private String insuranceHolder;
    @SerializedName("img_insr_card")
    @Expose
    private String insuranceCardImage;

    public String getMemberInsuranceId() {
        return memberInsuranceId;
    }

    public void setMemberInsuranceId(String memberInsuranceId) {
        this.memberInsuranceId = memberInsuranceId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRelationId() {
        return relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }

    public String getPolicyNo() {
        return policyNo;
    }

    public void setPolicyNo(String policyNo) {
        this.policyNo = policyNo;
    }

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

    public String getInsuredName() {
        return insuredName;
    }

    public void setInsuredName(String insuredName) {
        this.insuredName = insuredName;
    }

    public String getInsuranceHolder() {
        return insuranceHolder;
    }

    public void setInsuranceHolder(String insuranceHolder) {
        this.insuranceHolder = insuranceHolder;
    }

    public String getInsuranceCardImage() {
        return insuranceCardImage;
    }

    public void setInsuranceCardImage(String insuranceCardImage) {
        this.insuranceCardImage = insuranceCardImage;
    }
}

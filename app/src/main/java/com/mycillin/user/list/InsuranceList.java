package com.mycillin.user.list;

/**
 * Created by mrbagaskoro on 23-Sep-17.
 */

public class InsuranceList {

    private String insuranceId;
    private String userId;
    private String relationId;
    private String policyNo;
    private String insuranceProviderId;
    private String insuranceProviderDesc;
    private String insuredName;
    private String insuranceHolderName;
    private String insuranceImageUrl;

    public InsuranceList(String insuranceId, String userId, String relationId, String policyNo,
                         String insuranceProviderId, String insuranceProviderDesc, String insuredName,
                         String insuranceHolderName, String insuranceImageUrl){
        this.insuranceId = insuranceId;
        this.userId = userId;
        this.relationId = relationId;
        this.policyNo = policyNo;
        this.insuranceProviderId = insuranceProviderId;
        this.insuranceProviderDesc = insuranceProviderDesc;
        this.insuredName = insuredName;
        this.insuranceHolderName = insuranceHolderName;
        this.insuranceImageUrl = insuranceImageUrl;
    }

    public String getInsuranceId() {
        return insuranceId;
    }

    public void setInsuranceId(String insuranceId) {
        this.insuranceId = insuranceId;
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

    public String getInsuranceHolderName() {
        return insuranceHolderName;
    }

    public void setInsuranceHolderName(String insuranceHolderName) {
        this.insuranceHolderName = insuranceHolderName;
    }

    public String getInsuranceImageUrl() {
        return insuranceImageUrl;
    }

    public void setInsuranceImageUrl(String insuranceImageUrl) {
        this.insuranceImageUrl = insuranceImageUrl;
    }
}

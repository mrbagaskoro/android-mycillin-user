package com.mycillin.user.list;

/**
 * Created by mrbagaskoro on 20/09/2017.
 */

public class AccountList {
    private String accountName;
    private String accountCreatedBy;
    private String accountCreatedDate;
    private String accountUpdatedBy;
    private String accountUpdatedDate;
    private String accountRelationId;
    private String accountRelationType;
    private String accountUserId;
    private String accountGender;
    private String accountAddress;
    private String accountMobileNo;
    private String accountDOB;
    private String accountHeight;
    private String accountWeight;
    private String accountBloodType;
    private String accountInsuranceId;

    public AccountList(String accountName, String accountCreatedBy,
                       String accountCreatedDate, String accountUpdatedBy, String accountUpdatedDate,
                       String accountRelationId, String accountRelationType, String accountUserId,
                       String accountGender, String accountAddress, String accountMobileNo,
                       String accountDOB, String accountHeight, String accountWeight,
                       String accountBloodType, String accountInsuranceId){
        this.accountName = accountName;
        this.accountCreatedBy = accountCreatedBy;
        this.accountCreatedDate = accountCreatedDate;
        this.accountUpdatedBy = accountUpdatedBy;
        this.accountUpdatedDate = accountUpdatedDate;
        this.accountRelationId = accountRelationId;
        this.accountRelationType = accountRelationType;
        this.accountUserId = accountUserId;
        this.accountGender = accountGender;
        this.accountAddress = accountAddress;
        this.accountMobileNo = accountMobileNo;
        this.accountDOB = accountDOB;
        this.accountHeight = accountHeight;
        this.accountWeight = accountWeight;
        this.accountBloodType = accountBloodType;
        this.accountInsuranceId = accountInsuranceId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountCreatedBy() {
        return accountCreatedBy;
    }

    public void setAccountCreatedBy(String accountCreatedBy) {
        this.accountCreatedBy = accountCreatedBy;
    }

    public String getAccountCreatedDate() {
        return accountCreatedDate;
    }

    public void setAccountCreatedDate(String accountCreatedDate) {
        this.accountCreatedDate = accountCreatedDate;
    }

    public String getAccountUpdatedBy() {
        return accountUpdatedBy;
    }

    public void setAccountUpdatedBy(String accountUpdatedBy) {
        this.accountUpdatedBy = accountUpdatedBy;
    }

    public String getAccountUpdatedDate() {
        return accountUpdatedDate;
    }

    public void setAccountUpdatedDate(String accountUpdatedDate) {
        this.accountUpdatedDate = accountUpdatedDate;
    }

    public String getAccountRelationId() {
        return accountRelationId;
    }

    public void setAccountRelationId(String accountRelationId) {
        this.accountRelationId = accountRelationId;
    }

    public String getAccountRelationType() {
        return accountRelationType;
    }

    public void setAccountRelationType(String accountRelationType) {
        this.accountRelationType = accountRelationType;
    }

    public String getAccountUserId() {
        return accountUserId;
    }

    public void setAccountUserId(String accountUserId) {
        this.accountUserId = accountUserId;
    }

    public String getAccountGender() {
        return accountGender;
    }

    public void setAccountGender(String accountGender) {
        this.accountGender = accountGender;
    }

    public String getAccountAddress() {
        return accountAddress;
    }

    public void setAccountAddress(String accountAddress) {
        this.accountAddress = accountAddress;
    }

    public String getAccountMobileNo() {
        return accountMobileNo;
    }

    public void setAccountMobileNo(String accountMobileNo) {
        this.accountMobileNo = accountMobileNo;
    }

    public String getAccountDOB() {
        return accountDOB;
    }

    public void setAccountDOB(String accountDOB) {
        this.accountDOB = accountDOB;
    }

    public String getAccountHeight() {
        return accountHeight;
    }

    public void setAccountHeight(String accountHeight) {
        this.accountHeight = accountHeight;
    }

    public String getAccountWeight() {
        return accountWeight;
    }

    public void setAccountWeight(String accountWeight) {
        this.accountWeight = accountWeight;
    }

    public String getAccountBloodType() {
        return accountBloodType;
    }

    public void setAccountBloodType(String accountBloodType) {
        this.accountBloodType = accountBloodType;
    }

    public String getAccountInsuranceId() {
        return accountInsuranceId;
    }

    public void setAccountInsuranceId(String accountInsuranceId) {
        this.accountInsuranceId = accountInsuranceId;
    }
}

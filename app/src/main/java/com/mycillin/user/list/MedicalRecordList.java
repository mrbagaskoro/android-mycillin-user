package com.mycillin.user.list;

/**
 * Created by mrbagaskoro on 20-Sep-17.
 */

public class MedicalRecordList {
    private String day;
    private String month;
    private String year;
    private String createdDate;
    private String partnerId;
    private String partnerName;
    private String recordId;
    private String userId;
    private String serviceTypeDesc;
    private String bodyTemperature;
    private String bloodSugarLevel;
    private String cholesterolLevel;
    private String bloodPressUpper;
    private String bloodPressLower;
    private String patientCondition;
    private String diagnose;
    private String prescriptionStatus;
    private String prescriptionId;
    private String prescriptionTypeDesc;

    public MedicalRecordList(String day, String month, String year, String createdDate, String partnerId,
                             String partnerName, String recordId, String userId, String serviceTypeDesc,
                             String bodyTemperature, String bloodSugarLevel, String cholesterolLevel,
                             String bloodPressUpper, String bloodPressLower, String patientCondition,
                             String diagnose, String prescriptionStatus, String prescriptionId,
                             String prescriptionTypeDesc){

        this.day = day;
        this.month = month;
        this.year = year;
        this.createdDate = createdDate;
        this.partnerId = partnerId;
        this.partnerName = partnerName;
        this.recordId = recordId;
        this.userId = userId;
        this.serviceTypeDesc = serviceTypeDesc;
        this.bodyTemperature = bodyTemperature;
        this.bloodSugarLevel = bloodSugarLevel;
        this.cholesterolLevel = cholesterolLevel;
        this.bloodPressUpper = bloodPressUpper;
        this.bloodPressLower = bloodPressLower;
        this.patientCondition = patientCondition;
        this.diagnose = diagnose;
        this.prescriptionStatus = prescriptionStatus;
        this.prescriptionId = prescriptionId;
        this.prescriptionTypeDesc = prescriptionTypeDesc;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getServiceTypeDesc() {
        return serviceTypeDesc;
    }

    public void setServiceTypeDesc(String serviceTypeDesc) {
        this.serviceTypeDesc = serviceTypeDesc;
    }

    public String getBodyTemperature() {
        return bodyTemperature;
    }

    public void setBodyTemperature(String bodyTemperature) {
        this.bodyTemperature = bodyTemperature;
    }

    public String getBloodSugarLevel() {
        return bloodSugarLevel;
    }

    public void setBloodSugarLevel(String bloodSugarLevel) {
        this.bloodSugarLevel = bloodSugarLevel;
    }

    public String getCholesterolLevel() {
        return cholesterolLevel;
    }

    public void setCholesterolLevel(String cholesterolLevel) {
        this.cholesterolLevel = cholesterolLevel;
    }

    public String getBloodPressUpper() {
        return bloodPressUpper;
    }

    public void setBloodPressUpper(String bloodPressUpper) {
        this.bloodPressUpper = bloodPressUpper;
    }

    public String getBloodPressLower() {
        return bloodPressLower;
    }

    public void setBloodPressLower(String bloodPressLower) {
        this.bloodPressLower = bloodPressLower;
    }

    public String getPatientCondition() {
        return patientCondition;
    }

    public void setPatientCondition(String patientCondition) {
        this.patientCondition = patientCondition;
    }

    public String getDiagnose() {
        return diagnose;
    }

    public void setDiagnose(String diagnose) {
        this.diagnose = diagnose;
    }

    public String getPrescriptionStatus() {
        return prescriptionStatus;
    }

    public void setPrescriptionStatus(String prescriptionStatus) {
        this.prescriptionStatus = prescriptionStatus;
    }

    public String getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(String prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public String getPrescriptionTypeDesc() {
        return prescriptionTypeDesc;
    }

    public void setPrescriptionTypeDesc(String prescriptionTypeDesc) {
        this.prescriptionTypeDesc = prescriptionTypeDesc;
    }
}

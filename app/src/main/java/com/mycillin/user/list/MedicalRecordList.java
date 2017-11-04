package com.mycillin.user.list;

/**
 * Created by mrbagaskoro on 20-Sep-17.
 */

public class MedicalRecordList {
    private String createdBy;
    private String createdDate;
    private String createdDay;
    private String createdMonth;
    private String createdYear;
    private String updatedBy;
    private String updatedDate;
    private String bookingId;
    private String recordId;
    private String userId;
    private String relationId;
    private String partnerId;
    private String serviceTypeId;
    private String bodyTemperature;
    private String bloodSugarLevel;
    private String cholesterolLevel;
    private String bloodPressUpper;
    private String bloodPressLower;
    private String patientCondition;
    private String diagnose;
    private String prescriptionStatus;
    private String prescriptionId;
    private String prescriptionTypeId;
    private String prescriptionPhoto;

    public MedicalRecordList(String createdBy, String createdDate, String createdDay, String createdMonth,
                             String createdYear, String updatedBy, String updatedDate, String bookingId,
                             String recordId, String userId, String relationId, String partnerId,
                             String serviceTypeId, String bodyTemperature, String bloodSugarLevel,
                             String cholesterolLevel, String bloodPressUpper, String bloodPressLower,
                             String patientCondition, String diagnose, String prescriptionStatus,
                             String prescriptionId, String prescriptionTypeId, String prescriptionPhoto){
        
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.createdDay = createdDay;
        this.createdMonth = createdMonth;
        this.createdYear = createdYear;
        this.updatedBy = updatedBy;
        this.updatedDate = updatedDate;
        this.bookingId = bookingId;
        this.recordId = recordId;
        this.userId = userId;
        this.relationId = relationId;
        this.partnerId = partnerId;
        this.serviceTypeId = serviceTypeId;
        this.bodyTemperature = bodyTemperature;
        this.bloodSugarLevel = bloodSugarLevel;
        this.cholesterolLevel = cholesterolLevel;
        this.bloodPressUpper = bloodPressUpper;
        this.bloodPressLower = bloodPressLower;
        this.patientCondition = patientCondition;
        this.diagnose = diagnose;
        this.prescriptionStatus = prescriptionStatus;
        this.prescriptionId = prescriptionId;
        this.prescriptionTypeId = prescriptionTypeId;
        this.prescriptionPhoto = prescriptionPhoto;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedDay() {
        return createdDay;
    }

    public void setCreatedDay(String createdDay) {
        this.createdDay = createdDay;
    }

    public String getCreatedMonth() {
        return createdMonth;
    }

    public void setCreatedMonth(String createdMonth) {
        this.createdMonth = createdMonth;
    }

    public String getCreatedYear() {
        return createdYear;
    }

    public void setCreatedYear(String createdYear) {
        this.createdYear = createdYear;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
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

    public String getRelationId() {
        return relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(String serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
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

    public String getPrescriptionTypeId() {
        return prescriptionTypeId;
    }

    public void setPrescriptionTypeId(String prescriptionTypeId) {
        this.prescriptionTypeId = prescriptionTypeId;
    }

    public String getPrescriptionPhoto() {
        return prescriptionPhoto;
    }

    public void setPrescriptionPhoto(String prescriptionPhoto) {
        this.prescriptionPhoto = prescriptionPhoto;
    }
}

package com.mycillin.user.rest.medicalRecordList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 16003041 on 02/11/2017.
 */

public class ModelDataMedicalRecordList {

    @SerializedName("created_by")
    @Expose
    private String createdBy;
    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("updated_by")
    @Expose
    private String updatedBy;
    @SerializedName("updated_date")
    @Expose
    private String updatedDate;
    @SerializedName("booking_id")
    @Expose
    private String bookingId;
    @SerializedName("record_id")
    @Expose
    private String recordId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("relation_id")
    @Expose
    private String relationId;
    @SerializedName("partner_id")
    @Expose
    private String partnerId;
    @SerializedName("service_type_id")
    @Expose
    private String serviceTypeId;
    @SerializedName("body_temperature")
    @Expose
    private String bodyTemperature;
    @SerializedName("blood_sugar_level")
    @Expose
    private String bloodSugarLevel;
    @SerializedName("cholesterol_level")
    @Expose
    private String cholesterolLevel;
    @SerializedName("blood_press_upper")
    @Expose
    private String bloodPressUpper;
    @SerializedName("blood_press_lower")
    @Expose
    private String bloodPressLower;
    @SerializedName("patient_condition")
    @Expose
    private String patientCondition;
    @SerializedName("diagnosa")
    @Expose
    private String diagnosa;
    @SerializedName("prescription_status")
    @Expose
    private String prescriptionStatus;
    @SerializedName("prescription_id")
    @Expose
    private String prescriptionId;
    @SerializedName("prescription_type_id")
    @Expose
    private String prescriptionTypeId;
    @SerializedName("prescription_photo")
    @Expose
    private String prescriptionPhoto;

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

    public String getDiagnosa() {
        return diagnosa;
    }

    public void setDiagnosa(String diagnosa) {
        this.diagnosa = diagnosa;
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

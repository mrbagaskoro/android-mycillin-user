package com.mycillin.user.list;

/**
 * Created by mrbagaskoro on 05-Nov-17.
 */

public class PrescriptionRecordList {
    private String createdDate;
    private String prescriptionNo;
    private String prescriptionName;
    private String prescriptionQty;
    private String prescriptionUnitDesc;
    private String dosageDesc;

    public PrescriptionRecordList(String createdDate, String prescriptionNo, String prescriptionName,
                       String prescriptionQty, String prescriptionUnitDesc, String dosageDesc) {
        this.createdDate = createdDate;
        this.prescriptionNo = prescriptionNo;
        this.prescriptionName = prescriptionName;
        this.prescriptionQty = prescriptionQty;
        this.prescriptionUnitDesc = prescriptionUnitDesc;
        this.dosageDesc = dosageDesc;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getPrescriptionNo() {
        return prescriptionNo;
    }

    public void setPrescriptionNo(String prescriptionNo) {
        this.prescriptionNo = prescriptionNo;
    }

    public String getPrescriptionName() {
        return prescriptionName;
    }

    public void setPrescriptionName(String prescriptionName) {
        this.prescriptionName = prescriptionName;
    }

    public String getPrescriptionQty() {
        return prescriptionQty;
    }

    public void setPrescriptionQty(String prescriptionQty) {
        this.prescriptionQty = prescriptionQty;
    }

    public String getPrescriptionUnitDesc() {
        return prescriptionUnitDesc;
    }

    public void setPrescriptionUnitDesc(String prescriptionUnitDesc) {
        this.prescriptionUnitDesc = prescriptionUnitDesc;
    }

    public String getDosageDesc() {
        return dosageDesc;
    }

    public void setDosageDesc(String dosageDesc) {
        this.dosageDesc = dosageDesc;
    }
}

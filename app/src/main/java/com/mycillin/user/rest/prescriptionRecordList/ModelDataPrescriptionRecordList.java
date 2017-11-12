package com.mycillin.user.rest.prescriptionRecordList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mrbagaskoro on 05-Nov-17.
 */

public class ModelDataPrescriptionRecordList {

    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("prescription_no")
    @Expose
    private String prescriptionNo;
    @SerializedName("nama_obat")
    @Expose
    private String prescriptionName;
    @SerializedName("jumlah_obat")
    @Expose
    private String prescriptionQty;
    @SerializedName("satuan_obat_desc")
    @Expose
    private String prescriptionUnitDesc;
    @SerializedName("dosis_pemakaian")
    @Expose
    private String dosageDesc;

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

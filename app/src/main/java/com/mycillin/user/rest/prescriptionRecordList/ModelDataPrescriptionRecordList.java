package com.mycillin.user.rest.prescriptionRecordList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mrbagaskoro on 05-Nov-17.
 */

public class ModelDataPrescriptionRecordList {

    @SerializedName("created_by")
    @Expose
    private String createdBy;
    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("updated_by")
    @Expose
    private Object updatedBy;
    @SerializedName("updated_date")
    @Expose
    private String updatedDate;
    @SerializedName("prescription_no")
    @Expose
    private String prescriptionNo;
    @SerializedName("nama_obat")
    @Expose
    private String namaObat;
    @SerializedName("jumlah_obat")
    @Expose
    private String jumlahObat;
    @SerializedName("satuan_id")
    @Expose
    private String satuanId;
    @SerializedName("dosage_id")
    @Expose
    private String dosageId;
    @SerializedName("use_instruction_id")
    @Expose
    private String useInstructionId;
    @SerializedName("record_id")
    @Expose
    private String recordId;

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

    public Object getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Object updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getPrescriptionNo() {
        return prescriptionNo;
    }

    public void setPrescriptionNo(String prescriptionNo) {
        this.prescriptionNo = prescriptionNo;
    }

    public String getNamaObat() {
        return namaObat;
    }

    public void setNamaObat(String namaObat) {
        this.namaObat = namaObat;
    }

    public String getJumlahObat() {
        return jumlahObat;
    }

    public void setJumlahObat(String jumlahObat) {
        this.jumlahObat = jumlahObat;
    }

    public String getSatuanId() {
        return satuanId;
    }

    public void setSatuanId(String satuanId) {
        this.satuanId = satuanId;
    }

    public String getDosageId() {
        return dosageId;
    }

    public void setDosageId(String dosageId) {
        this.dosageId = dosageId;
    }

    public String getUseInstructionId() {
        return useInstructionId;
    }

    public void setUseInstructionId(String useInstructionId) {
        this.useInstructionId = useInstructionId;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }
}

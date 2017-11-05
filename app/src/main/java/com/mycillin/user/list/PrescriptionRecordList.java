package com.mycillin.user.list;

/**
 * Created by mrbagaskoro on 05-Nov-17.
 */

public class PrescriptionRecordList {
    private String recordId;
    private String prescriptionName;
    private String prescriptionQty;
    private String unitId;
    private String dosageId;
    private String instructionId;

    public PrescriptionRecordList(String recordId, String prescriptionName,
                       String prescriptionQty, String unitId, String dosageId, String instructionId) {
        this.recordId = recordId;
        this.prescriptionName = prescriptionName;
        this.prescriptionQty = prescriptionQty;
        this.unitId = unitId;
        this.dosageId = dosageId;
        this.instructionId = instructionId;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
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

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getDosageId() {
        return dosageId;
    }

    public void setDosageId(String dosageId) {
        this.dosageId = dosageId;
    }

    public String getInstructionId() {
        return instructionId;
    }

    public void setInstructionId(String instructionId) {
        this.instructionId = instructionId;
    }
}

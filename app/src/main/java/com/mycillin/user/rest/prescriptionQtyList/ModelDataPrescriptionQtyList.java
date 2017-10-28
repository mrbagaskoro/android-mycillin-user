package com.mycillin.user.rest.prescriptionQtyList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mrbagaskoro on 28-Oct-17.
 */

public class ModelDataPrescriptionQtyList {

    @SerializedName("satuan_obat_id")
    @Expose
    private String prescriptionQtyId;
    @SerializedName("satuan_obat_desc")
    @Expose
    private String prescriptionQtyDesc;

    public String getPrescriptionQtyId() {
        return prescriptionQtyId;
    }

    public void setPrescriptionQtyId(String prescriptionQtyId) {
        this.prescriptionQtyId = prescriptionQtyId;
    }

    public String getPrescriptionQtyDesc() {
        return prescriptionQtyDesc;
    }

    public void setPrescriptionQtyDesc(String prescriptionQtyDesc) {
        this.prescriptionQtyDesc = prescriptionQtyDesc;
    }
}

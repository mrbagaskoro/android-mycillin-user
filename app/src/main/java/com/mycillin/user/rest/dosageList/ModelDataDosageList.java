package com.mycillin.user.rest.dosageList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mrbagaskoro on 28-Oct-17.
 */

public class ModelDataDosageList {

    @SerializedName("dosis_obat_id")
    @Expose
    private String dosageId;
    @SerializedName("dosis_obat_desc")
    @Expose
    private String dosageDesc;

    public String getDosageId() {
        return dosageId;
    }

    public void setDosageId(String dosageId) {
        this.dosageId = dosageId;
    }

    public String getDosageDesc() {
        return dosageDesc;
    }

    public void setDosageDesc(String dosageDesc) {
        this.dosageDesc = dosageDesc;
    }
}

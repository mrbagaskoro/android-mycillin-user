package com.mycillin.user.rest.findConsultation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mrbagaskoro on 02-Dec-17.
 */

public class ModelDataFindConsultation {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("partner_type_desc")
    @Expose
    private String partnerTypeDesc;
    @SerializedName("spesialisasi_desc")
    @Expose
    private String spesialisasiDesc;
    @SerializedName("wilayah_kerja")
    @Expose
    private String wilayahKerja;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPartnerTypeDesc() {
        return partnerTypeDesc;
    }

    public void setPartnerTypeDesc(String partnerTypeDesc) {
        this.partnerTypeDesc = partnerTypeDesc;
    }

    public String getSpesialisasiDesc() {
        return spesialisasiDesc;
    }

    public void setSpesialisasiDesc(String spesialisasiDesc) {
        this.spesialisasiDesc = spesialisasiDesc;
    }

    public String getWilayahKerja() {
        return wilayahKerja;
    }

    public void setWilayahKerja(String wilayahKerja) {
        this.wilayahKerja = wilayahKerja;
    }
}

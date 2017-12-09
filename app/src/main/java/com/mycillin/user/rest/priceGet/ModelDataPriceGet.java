package com.mycillin.user.rest.priceGet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mrbagaskoro on 09-Dec-17.
 */

public class ModelDataPriceGet {

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
    @SerializedName("service_type_id")
    @Expose
    private String serviceTypeId;
    @SerializedName("pymt_methode_id")
    @Expose
    private String pymtMethodeId;
    @SerializedName("partner_type_id")
    @Expose
    private String partnerTypeId;
    @SerializedName("spesialisasi_id")
    @Expose
    private String spesialisasiId;
    @SerializedName("price_amount")
    @Expose
    private String priceAmount;

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

    public String getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(String serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    public String getPymtMethodeId() {
        return pymtMethodeId;
    }

    public void setPymtMethodeId(String pymtMethodeId) {
        this.pymtMethodeId = pymtMethodeId;
    }

    public String getPartnerTypeId() {
        return partnerTypeId;
    }

    public void setPartnerTypeId(String partnerTypeId) {
        this.partnerTypeId = partnerTypeId;
    }

    public String getSpesialisasiId() {
        return spesialisasiId;
    }

    public void setSpesialisasiId(String spesialisasiId) {
        this.spesialisasiId = spesialisasiId;
    }

    public String getPriceAmount() {
        return priceAmount;
    }

    public void setPriceAmount(String priceAmount) {
        this.priceAmount = priceAmount;
    }
}

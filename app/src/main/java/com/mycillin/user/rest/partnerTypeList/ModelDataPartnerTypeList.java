package com.mycillin.user.rest.partnerTypeList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mrbagaskoro on 28-Oct-17.
 */

public class ModelDataPartnerTypeList {

    @SerializedName("partner_type_id")
    @Expose
    private String partnerTypeId;
    @SerializedName("partner_type_desc")
    @Expose
    private String partnerTypeDesc;

    public String getPartnerTypeId() {
        return partnerTypeId;
    }

    public void setPartnerTypeId(String partnerTypeId) {
        this.partnerTypeId = partnerTypeId;
    }

    public String getPartnerTypeDesc() {
        return partnerTypeDesc;
    }

    public void setPartnerTypeDesc(String partnerTypeDesc) {
        this.partnerTypeDesc = partnerTypeDesc;
    }
}

package com.mycillin.user.rest.unratedList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 16003041 on 21/11/2017.
 */

public class ModelDataUnratedList {

    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("booking_id")
    @Expose
    private String bookingId;
    @SerializedName("partner_selected")
    @Expose
    private String partnerSelected;
    @SerializedName("full_name")
    @Expose
    private String fullName;

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getPartnerSelected() {
        return partnerSelected;
    }

    public void setPartnerSelected(String partnerSelected) {
        this.partnerSelected = partnerSelected;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}

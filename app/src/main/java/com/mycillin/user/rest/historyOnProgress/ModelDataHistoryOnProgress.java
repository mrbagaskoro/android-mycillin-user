package com.mycillin.user.rest.historyOnProgress;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mrbagaskoro on 03-Dec-17.
 */

public class ModelDataHistoryOnProgress {

    @SerializedName("order_date")
    @Expose
    private String orderDate;
    @SerializedName("booking_id")
    @Expose
    private String bookingId;
    @SerializedName("service_type_id")
    @Expose
    private String serviceTypeId;
    @SerializedName("service_type_desc")
    @Expose
    private String serviceTypeDesc;
    @SerializedName("partner_selected")
    @Expose
    private String partnerSelected;
    @SerializedName("partner_name")
    @Expose
    private String partnerName;
    @SerializedName("partner_type_id")
    @Expose
    private String partnerTypeId;
    @SerializedName("spesialisasi_id")
    @Expose
    private String spesialisasiId;
    @SerializedName("profile_photo")
    @Expose
    private String profilePhoto;
    @SerializedName("mobile_no")
    @Expose
    private String mobileNo;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("pymt_methode_id")
    @Expose
    private String pymtMethodeId;
    @SerializedName("pymt_methode_desc")
    @Expose
    private String pymtMethodeDesc;
    @SerializedName("promo_code")
    @Expose
    private String promoCode;
    @SerializedName("price_amount")
    @Expose
    private String priceAmount;
    @SerializedName("booking_status_id")
    @Expose
    private String bookingStatusId;
    @SerializedName("cancel_status")
    @Expose
    private String cancelStatus;
    @SerializedName("latitude_request")
    @Expose
    private String latitudeOrigin;
    @SerializedName("longitude_request")
    @Expose
    private String longitudeOrigin;
    @SerializedName("latitude_praktik")
    @Expose
    private String latitudeDestination;
    @SerializedName("longitude_praktik")
    @Expose
    private String longitudeDestination;
    @SerializedName("booking_status_desc")
    @Expose
    private String bookingStatusDesc;

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(String serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    public String getServiceTypeDesc() {
        return serviceTypeDesc;
    }

    public void setServiceTypeDesc(String serviceTypeDesc) {
        this.serviceTypeDesc = serviceTypeDesc;
    }

    public String getPartnerSelected() {
        return partnerSelected;
    }

    public void setPartnerSelected(String partnerSelected) {
        this.partnerSelected = partnerSelected;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
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

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getPymtMethodeId() {
        return pymtMethodeId;
    }

    public void setPymtMethodeId(String pymtMethodeId) {
        this.pymtMethodeId = pymtMethodeId;
    }

    public String getPymtMethodeDesc() {
        return pymtMethodeDesc;
    }

    public void setPymtMethodeDesc(String pymtMethodeDesc) {
        this.pymtMethodeDesc = pymtMethodeDesc;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    public String getPriceAmount() {
        return priceAmount;
    }

    public void setPriceAmount(String priceAmount) {
        this.priceAmount = priceAmount;
    }

    public String getBookingStatusId() {
        return bookingStatusId;
    }

    public void setBookingStatusId(String bookingStatusId) {
        this.bookingStatusId = bookingStatusId;
    }

    public String getCancelStatus() {
        return cancelStatus;
    }

    public void setCancelStatus(String cancelStatus) {
        this.cancelStatus = cancelStatus;
    }

    public String getLatitudeOrigin() {
        return latitudeOrigin;
    }

    public void setLatitudeOrigin(String latitudeOrigin) {
        this.latitudeOrigin = latitudeOrigin;
    }

    public String getLongitudeOrigin() {
        return longitudeOrigin;
    }

    public void setLongitudeOrigin(String longitudeOrigin) {
        this.longitudeOrigin = longitudeOrigin;
    }

    public String getLatitudeDestination() {
        return latitudeDestination;
    }

    public void setLatitudeDestination(String latitudeDestination) {
        this.latitudeDestination = latitudeDestination;
    }

    public String getLongitudeDestination() {
        return longitudeDestination;
    }

    public void setLongitudeDestination(String longitudeDestination) {
        this.longitudeDestination = longitudeDestination;
    }

    public String getBookingStatusDesc() {
        return bookingStatusDesc;
    }

    public void setBookingStatusDesc(String bookingStatusDesc) {
        this.bookingStatusDesc = bookingStatusDesc;
    }
}

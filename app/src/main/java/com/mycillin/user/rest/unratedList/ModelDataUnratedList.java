package com.mycillin.user.rest.unratedList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 16003041 on 21/11/2017.
 */

public class ModelDataUnratedList {

    @SerializedName("created_by")
    @Expose
    public String createdBy;
    @SerializedName("created_date")
    @Expose
    public String createdDate;
    @SerializedName("updated_by")
    @Expose
    public String updatedBy;
    @SerializedName("updated_date")
    @Expose
    public String updatedDate;
    @SerializedName("booking_id")
    @Expose
    public String bookingId;
    @SerializedName("service_type_id")
    @Expose
    public String serviceTypeId;
    @SerializedName("action_type_id")
    @Expose
    public String actionTypeId;
    @SerializedName("user_id")
    @Expose
    public String userId;
    @SerializedName("relation_id")
    @Expose
    public String relationId;
    @SerializedName("partner_selected")
    @Expose
    public String partnerSelected;
    @SerializedName("pymt_methode_id")
    @Expose
    public String pymtMethodeId;
    @SerializedName("invoice_no")
    @Expose
    public String invoiceNo;
    @SerializedName("pymt_provider_id")
    @Expose
    public String pymtProviderId;
    @SerializedName("promo_code")
    @Expose
    public String promoCode;
    @SerializedName("price_amount")
    @Expose
    public String priceAmount;
    @SerializedName("partner_profit_share")
    @Expose
    public String partnerProfitShare;
    @SerializedName("request_location")
    @Expose
    public String requestLocation;
    @SerializedName("actual_partner_loc")
    @Expose
    public String actualPartnerLoc;
    @SerializedName("service_rating")
    @Expose
    public String serviceRating;
    @SerializedName("user_comment")
    @Expose
    public String userComment;
    @SerializedName("photo_resep_obat")
    @Expose
    public String photoResepObat;
    @SerializedName("booking_status_id")
    @Expose
    public String bookingStatusId;
    @SerializedName("cancel_status")
    @Expose
    public String cancelStatus;
    @SerializedName("cancel_by")
    @Expose
    public String cancelBy;
    @SerializedName("cancel_reason_id")
    @Expose
    public String cancelReasonId;

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

    public String getActionTypeId() {
        return actionTypeId;
    }

    public void setActionTypeId(String actionTypeId) {
        this.actionTypeId = actionTypeId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRelationId() {
        return relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }

    public String getPartnerSelected() {
        return partnerSelected;
    }

    public void setPartnerSelected(String partnerSelected) {
        this.partnerSelected = partnerSelected;
    }

    public String getPymtMethodeId() {
        return pymtMethodeId;
    }

    public void setPymtMethodeId(String pymtMethodeId) {
        this.pymtMethodeId = pymtMethodeId;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getPymtProviderId() {
        return pymtProviderId;
    }

    public void setPymtProviderId(String pymtProviderId) {
        this.pymtProviderId = pymtProviderId;
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

    public String getPartnerProfitShare() {
        return partnerProfitShare;
    }

    public void setPartnerProfitShare(String partnerProfitShare) {
        this.partnerProfitShare = partnerProfitShare;
    }

    public String getRequestLocation() {
        return requestLocation;
    }

    public void setRequestLocation(String requestLocation) {
        this.requestLocation = requestLocation;
    }

    public String getActualPartnerLoc() {
        return actualPartnerLoc;
    }

    public void setActualPartnerLoc(String actualPartnerLoc) {
        this.actualPartnerLoc = actualPartnerLoc;
    }

    public String getServiceRating() {
        return serviceRating;
    }

    public void setServiceRating(String serviceRating) {
        this.serviceRating = serviceRating;
    }

    public String getUserComment() {
        return userComment;
    }

    public void setUserComment(String userComment) {
        this.userComment = userComment;
    }

    public String getPhotoResepObat() {
        return photoResepObat;
    }

    public void setPhotoResepObat(String photoResepObat) {
        this.photoResepObat = photoResepObat;
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

    public String getCancelBy() {
        return cancelBy;
    }

    public void setCancelBy(String cancelBy) {
        this.cancelBy = cancelBy;
    }

    public String getCancelReasonId() {
        return cancelReasonId;
    }

    public void setCancelReasonId(String cancelReasonId) {
        this.cancelReasonId = cancelReasonId;
    }
}

package com.mycillin.user.list;

/**
 * Created by mrbagaskoro on 21-Sep-17.
 */

public class CompletedList {
    private String orderDate;
    private String orderTime;
    private String bookingId;
    private String serviceTypeId;
    private String serviceTypeDesc;
    private String partnerId;
    private String partnerName;
    private String partnerTypeId;
    private String partnerTypeDesc;
    private String partnerSpecializationId;
    private String partnerSpecializationDesc;
    private String partnerPic;
    private String mobileNo;
    private String rating;
    private String paymentId;
    private String paymentDesc;
    private String promoCode;
    private String priceAmount;
    private String cancelBy;
    private String cancelReasonByUser;
    private String cancelReasonByPartner;
    private String diagnose;
    private String actionDesc;
    private String prescriptionTypeId;

    public CompletedList(String orderDate, String orderTime, String bookingId, String serviceTypeId,
                         String serviceTypeDesc, String partnerId, String partnerName, String partnerTypeId,
                         String partnerTypeDesc, String partnerSpecializationId, String partnerSpecializationDesc,
                         String partnerPic, String mobileNo, String rating, String paymentId,
                         String paymentDesc, String promoCode, String priceAmount, String cancelBy,
                         String cancelReasonByUser, String cancelReasonByPartner, String diagnose,
                         String actionDesc, String prescriptionTypeId){

        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.bookingId = bookingId;
        this.serviceTypeId = serviceTypeId;
        this.serviceTypeDesc = serviceTypeDesc;
        this.partnerId = partnerId;
        this.partnerName = partnerName;
        this.partnerTypeId = partnerTypeId;
        this.partnerTypeDesc = partnerTypeDesc;
        this.partnerSpecializationId = partnerSpecializationId;
        this.partnerSpecializationDesc = partnerSpecializationDesc;
        this.partnerPic = partnerPic;
        this.mobileNo = mobileNo;
        this.rating = rating;
        this.paymentId = paymentId;
        this.paymentDesc = paymentDesc;
        this.promoCode = promoCode;
        this.priceAmount = priceAmount;
        this.cancelBy = cancelBy;
        this.cancelReasonByUser = cancelReasonByUser;
        this.cancelReasonByPartner = cancelReasonByPartner;
        this.diagnose = diagnose;
        this.actionDesc = actionDesc;
        this.prescriptionTypeId = prescriptionTypeId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
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

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
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

    public String getPartnerTypeDesc() {
        return partnerTypeDesc;
    }

    public void setPartnerTypeDesc(String partnerTypeDesc) {
        this.partnerTypeDesc = partnerTypeDesc;
    }

    public String getPartnerSpecializationId() {
        return partnerSpecializationId;
    }

    public void setPartnerSpecializationId(String partnerSpecializationId) {
        this.partnerSpecializationId = partnerSpecializationId;
    }

    public String getPartnerSpecializationDesc() {
        return partnerSpecializationDesc;
    }

    public void setPartnerSpecializationDesc(String partnerSpecializationDesc) {
        this.partnerSpecializationDesc = partnerSpecializationDesc;
    }

    public String getPartnerPic() {
        return partnerPic;
    }

    public void setPartnerPic(String partnerPic) {
        this.partnerPic = partnerPic;
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

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getPaymentDesc() {
        return paymentDesc;
    }

    public void setPaymentDesc(String paymentDesc) {
        this.paymentDesc = paymentDesc;
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

    public String getCancelBy() {
        return cancelBy;
    }

    public void setCancelBy(String cancelBy) {
        this.cancelBy = cancelBy;
    }

    public String getCancelReasonByUser() {
        return cancelReasonByUser;
    }

    public void setCancelReasonByUser(String cancelReasonByUser) {
        this.cancelReasonByUser = cancelReasonByUser;
    }

    public String getCancelReasonByPartner() {
        return cancelReasonByPartner;
    }

    public void setCancelReasonByPartner(String cancelReasonByPartner) {
        this.cancelReasonByPartner = cancelReasonByPartner;
    }

    public String getDiagnose() {
        return diagnose;
    }

    public void setDiagnose(String diagnose) {
        this.diagnose = diagnose;
    }

    public String getActionDesc() {
        return actionDesc;
    }

    public void setActionDesc(String actionDesc) {
        this.actionDesc = actionDesc;
    }

    public String getPrescriptionTypeId() {
        return prescriptionTypeId;
    }

    public void setPrescriptionTypeId(String prescriptionTypeId) {
        this.prescriptionTypeId = prescriptionTypeId;
    }
}

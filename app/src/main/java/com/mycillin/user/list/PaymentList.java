package com.mycillin.user.list;

/**
 * Created by mrbagaskoro on 23-Sep-17.
 */

public class PaymentList {
    private String paymentName;
    private String paymentAccount;

    public PaymentList(String paymentName, String paymentAccount){
        this.paymentName = paymentName;
        this.paymentAccount = paymentAccount;
    }

    public String getPaymentName() {
        return paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }

    public String getPaymentAccount() {
        return paymentAccount;
    }

    public void setPaymentAccount(String paymentAccount) {
        this.paymentAccount = paymentAccount;
    }
}

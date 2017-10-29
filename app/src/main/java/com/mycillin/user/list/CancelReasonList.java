package com.mycillin.user.list;

/**
 * Created by mrbagaskoro on 29-Oct-17.
 */

public class CancelReasonList {
    private String cancelReasonId;
    private String cancelReasonDesc;

    public CancelReasonList(String cancelReasonId, String cancelReasonDesc){
        this.cancelReasonId = cancelReasonId;
        this.cancelReasonDesc = cancelReasonDesc;
    }

    public String getCancelReasonId() {
        return cancelReasonId;
    }

    public void setCancelReasonId(String cancelReasonId) {
        this.cancelReasonId = cancelReasonId;
    }

    public String getCancelReasonDesc() {
        return cancelReasonDesc;
    }

    public void setCancelReasonDesc(String cancelReasonDesc) {
        this.cancelReasonDesc = cancelReasonDesc;
    }
}

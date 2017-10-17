package com.mycillin.user.rest.accountList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 16003041 on 17/10/2017.
 */

public class ModelResultAccountList {

    @SerializedName("result")
    @Expose
    private ModelResultDataAccountList result;

    public ModelResultDataAccountList getResult() {
        return result;
    }

    public void setResult(ModelResultDataAccountList result) {
        this.result = result;
    }
}

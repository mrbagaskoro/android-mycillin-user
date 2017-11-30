package com.mycillin.user.rest.accountPicUpdate;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 16003041 on 30/11/2017.
 */

public class ModelResultAccountPicUpdate {

    @SerializedName("result")
    @Expose
    private ModelResultDataAccountPicUpdate result;

    public ModelResultDataAccountPicUpdate getResult() {
        return result;
    }

    public void setResult(ModelResultDataAccountPicUpdate result) {
        this.result = result;
    }
}

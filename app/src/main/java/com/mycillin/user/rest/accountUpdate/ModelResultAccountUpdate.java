package com.mycillin.user.rest.accountUpdate;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 16003041 on 17/10/2017.
 */

public class ModelResultAccountUpdate {

    @SerializedName("result")
    @Expose
    private ModelResultDataAccountUpdate result;

    public ModelResultDataAccountUpdate getResult() {
        return result;
    }

    public void setResult(ModelResultDataAccountUpdate result) {
        this.result = result;
    }
}

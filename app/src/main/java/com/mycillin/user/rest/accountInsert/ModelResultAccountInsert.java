package com.mycillin.user.rest.accountInsert;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 16003041 on 17/10/2017.
 */

public class ModelResultAccountInsert {

    @SerializedName("result")
    @Expose
    private ModelResultDataAccountInsert result;

    public ModelResultDataAccountInsert getResult() {
        return result;
    }

    public void setResult(ModelResultDataAccountInsert result) {
        this.result = result;
    }
}

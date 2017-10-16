package com.mycillin.user.rest.changePassword;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 16003041 on 16/10/2017.
 */

public class ModelResultChangePassword {

    @SerializedName("result")
    @Expose
    private ModelResultDataChangePassword result;

    public ModelResultDataChangePassword getResult() {
        return result;
    }

    public void setResult(ModelResultDataChangePassword result) {
        this.result = result;
    }
}

package com.mycillin.user.rest.accountPicGet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mrbagaskoro on 28-Oct-17.
 */

public class ModelResultAccountPicGet {

    @SerializedName("result")
    @Expose
    private ModelResultDataAccountPicGet result;

    public ModelResultDataAccountPicGet getResult() {
        return result;
    }

    public void setResult(ModelResultDataAccountPicGet result) {
        this.result = result;
    }
}

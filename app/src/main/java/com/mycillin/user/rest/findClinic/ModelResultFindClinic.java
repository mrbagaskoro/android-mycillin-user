package com.mycillin.user.rest.findClinic;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mrbagaskoro on 02-Dec-17.
 */

public class ModelResultFindClinic {

    @SerializedName("result")
    @Expose
    private ModelResultDataFindClinic result;

    public ModelResultDataFindClinic getResult() {
        return result;
    }

    public void setResult(ModelResultDataFindClinic result) {
        this.result = result;
    }
}

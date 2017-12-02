package com.mycillin.user.rest.findHealthcare;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mrbagaskoro on 02-Dec-17.
 */

public class ModelResultFindHealthcare {

    @SerializedName("result")
    @Expose
    private ModelResultDataFindHomecare result;

    public ModelResultDataFindHomecare getResult() {
        return result;
    }

    public void setResult(ModelResultDataFindHomecare result) {
        this.result = result;
    }
}

package com.mycillin.user.rest.unratedList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 16003041 on 21/11/2017.
 */

public class ModelResultUnratedList {

    @SerializedName("result")
    @Expose
    private ModelResultDataUnratedList result;

    public ModelResultDataUnratedList getResult() {
        return result;
    }

    public void setResult(ModelResultDataUnratedList result) {
        this.result = result;
    }
}

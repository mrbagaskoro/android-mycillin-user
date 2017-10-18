package com.mycillin.user.rest.relationList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mrbagaskoro on 18-Oct-17.
 */

public class ModelResultRelationList {

    @SerializedName("result")
    @Expose
    private ModelResultDataRelationList result;

    public ModelResultDataRelationList getResult() {
        return result;
    }

    public void setResult(ModelResultDataRelationList result) {
        this.result = result;
    }
}

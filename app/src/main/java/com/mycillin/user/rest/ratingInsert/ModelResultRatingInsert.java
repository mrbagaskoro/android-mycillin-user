package com.mycillin.user.rest.ratingInsert;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mrbagaskoro on 21-Nov-17.
 */

public class ModelResultRatingInsert {

    @SerializedName("result")
    @Expose
    private ModelResultDataRatingInsert result;

    public ModelResultDataRatingInsert getResult() {
        return result;
    }

    public void setResult(ModelResultDataRatingInsert result) {
        this.result = result;
    }
}

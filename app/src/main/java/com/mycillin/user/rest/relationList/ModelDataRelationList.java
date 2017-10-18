package com.mycillin.user.rest.relationList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mrbagaskoro on 18-Oct-17.
 */

public class ModelDataRelationList {

    @SerializedName("relation_id")
    @Expose
    private String relationId;
    @SerializedName("relation_desc")
    @Expose
    private String relationDesc;

    public String getRelationId() {
        return relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }

    public String getRelationDesc() {
        return relationDesc;
    }

    public void setRelationDesc(String relationDesc) {
        this.relationDesc = relationDesc;
    }
}

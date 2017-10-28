package com.mycillin.user.rest.usageInstructionList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mrbagaskoro on 28-Oct-17.
 */

public class ModelDataUsageInstructionList {

    @SerializedName("use_instruction_id")
    @Expose
    private String usageInstructionId;
    @SerializedName("use_instruction_desc")
    @Expose
    private String usageInstructionDesc;

    public String getUsageInstructionId() {
        return usageInstructionId;
    }

    public void setUsageInstructionId(String usageInstructionId) {
        this.usageInstructionId = usageInstructionId;
    }

    public String getUsageInstructionDesc() {
        return usageInstructionDesc;
    }

    public void setUsageInstructionDesc(String usageInstructionDesc) {
        this.usageInstructionDesc = usageInstructionDesc;
    }
}

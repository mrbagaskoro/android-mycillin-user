package com.mycillin.user.list;

/**
 * Created by mrbagaskoro on 23-Sep-17.
 */

public class InsuranceList {
    private String insuranceCompany;
    private String insurancePolicy;

    public InsuranceList(String insuranceCompany, String insurancePolicy){
        this.insuranceCompany = insuranceCompany;
        this.insurancePolicy = insurancePolicy;
    }

    public String getInsuranceCompany() {
        return insuranceCompany;
    }

    public void setInsuranceCompany(String insuranceCompany) {
        this.insuranceCompany = insuranceCompany;
    }

    public String getInsurancePolicy() {
        return insurancePolicy;
    }

    public void setInsurancePolicy(String insurancePolicy) {
        this.insurancePolicy = insurancePolicy;
    }
}

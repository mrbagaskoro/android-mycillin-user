package com.mycillin.user.rest.findFacility;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mrbagaskoro on 16-Dec-17.
 */

public class ModelDataFindFacility {

    @SerializedName("facility_name")
    @Expose
    private String facilityName;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("distance")
    @Expose
    private String distance;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("phone_no")
    @Expose
    private String phoneNo;
    @SerializedName("facility_picture")
    @Expose
    private String facilityPicture;

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getFacilityPicture() {
        return facilityPicture;
    }

    public void setFacilityPicture(String facilityPicture) {
        this.facilityPicture = facilityPicture;
    }
}

package com.mycillin.user.rest.findClinic;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mrbagaskoro on 02-Dec-17.
 */

public class ModelDataFindClinic {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("latitude_praktik")
    @Expose
    private String latitude;
    @SerializedName("longitude_praktik")
    @Expose
    private String longitude;
    @SerializedName("distance")
    @Expose
    private String distance;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("profile_photo")
    @Expose
    private String profilePhoto;
    @SerializedName("no_SIP")
    @Expose
    private String noSIP;
    @SerializedName("wilayah_kerja")
    @Expose
    private String wilayahKerja;
    @SerializedName("partner_type_desc")
    @Expose
    private String partnerTypeDesc;
    @SerializedName("spesialisasi_desc")
    @Expose
    private String spesialisasiDesc;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getNoSIP() {
        return noSIP;
    }

    public void setNoSIP(String noSIP) {
        this.noSIP = noSIP;
    }

    public String getWilayahKerja() {
        return wilayahKerja;
    }

    public void setWilayahKerja(String wilayahKerja) {
        this.wilayahKerja = wilayahKerja;
    }

    public String getPartnerTypeDesc() {
        return partnerTypeDesc;
    }

    public void setPartnerTypeDesc(String partnerTypeDesc) {
        this.partnerTypeDesc = partnerTypeDesc;
    }

    public String getSpesialisasiDesc() {
        return spesialisasiDesc;
    }

    public void setSpesialisasiDesc(String spesialisasiDesc) {
        this.spesialisasiDesc = spesialisasiDesc;
    }
}

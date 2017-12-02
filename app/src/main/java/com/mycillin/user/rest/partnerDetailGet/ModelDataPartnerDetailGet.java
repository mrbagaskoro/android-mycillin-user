package com.mycillin.user.rest.partnerDetailGet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mrbagaskoro on 02-Dec-17.
 */

public class ModelDataPartnerDetailGet {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("profile_photo")
    @Expose
    private String profilePhoto;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("no_SIP")
    @Expose
    private String noSIP;
    @SerializedName("SIP_berakhir")
    @Expose
    private String sIPBerakhir;
    @SerializedName("photo_SIP")
    @Expose
    private String photoSIP;
    @SerializedName("no_STR")
    @Expose
    private String noSTR;
    @SerializedName("STR_berakhir")
    @Expose
    private String sTRBerakhir;
    @SerializedName("photo_STR")
    @Expose
    private String photoSTR;
    @SerializedName("partner_type_id")
    @Expose
    private String partnerTypeId;
    @SerializedName("partner_type_desc")
    @Expose
    private String partnerTypeDesc;
    @SerializedName("spesialisasi_id")
    @Expose
    private String spesialisasiId;
    @SerializedName("spesialisasi_desc")
    @Expose
    private String spesialisasiDesc;
    @SerializedName("wilayah_kerja")
    @Expose
    private String wilayahKerja;
    @SerializedName("profile_desc")
    @Expose
    private String profileDesc;
    @SerializedName("lama_professi")
    @Expose
    private String lamaProfessi;
    @SerializedName("alamat_praktik")
    @Expose
    private String alamatPraktik;
    @SerializedName("nama_institusi")
    @Expose
    private String namaInstitusi;
    @SerializedName("rating")
    @Expose
    private String rating;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getNoSIP() {
        return noSIP;
    }

    public void setNoSIP(String noSIP) {
        this.noSIP = noSIP;
    }

    public String getsIPBerakhir() {
        return sIPBerakhir;
    }

    public void setsIPBerakhir(String sIPBerakhir) {
        this.sIPBerakhir = sIPBerakhir;
    }

    public String getPhotoSIP() {
        return photoSIP;
    }

    public void setPhotoSIP(String photoSIP) {
        this.photoSIP = photoSIP;
    }

    public String getNoSTR() {
        return noSTR;
    }

    public void setNoSTR(String noSTR) {
        this.noSTR = noSTR;
    }

    public String getsTRBerakhir() {
        return sTRBerakhir;
    }

    public void setsTRBerakhir(String sTRBerakhir) {
        this.sTRBerakhir = sTRBerakhir;
    }

    public String getPhotoSTR() {
        return photoSTR;
    }

    public void setPhotoSTR(String photoSTR) {
        this.photoSTR = photoSTR;
    }

    public String getPartnerTypeId() {
        return partnerTypeId;
    }

    public void setPartnerTypeId(String partnerTypeId) {
        this.partnerTypeId = partnerTypeId;
    }

    public String getPartnerTypeDesc() {
        return partnerTypeDesc;
    }

    public void setPartnerTypeDesc(String partnerTypeDesc) {
        this.partnerTypeDesc = partnerTypeDesc;
    }

    public String getSpesialisasiId() {
        return spesialisasiId;
    }

    public void setSpesialisasiId(String spesialisasiId) {
        this.spesialisasiId = spesialisasiId;
    }

    public String getSpesialisasiDesc() {
        return spesialisasiDesc;
    }

    public void setSpesialisasiDesc(String spesialisasiDesc) {
        this.spesialisasiDesc = spesialisasiDesc;
    }

    public String getWilayahKerja() {
        return wilayahKerja;
    }

    public void setWilayahKerja(String wilayahKerja) {
        this.wilayahKerja = wilayahKerja;
    }

    public String getProfileDesc() {
        return profileDesc;
    }

    public void setProfileDesc(String profileDesc) {
        this.profileDesc = profileDesc;
    }

    public String getLamaProfessi() {
        return lamaProfessi;
    }

    public void setLamaProfessi(String lamaProfessi) {
        this.lamaProfessi = lamaProfessi;
    }

    public String getAlamatPraktik() {
        return alamatPraktik;
    }

    public void setAlamatPraktik(String alamatPraktik) {
        this.alamatPraktik = alamatPraktik;
    }

    public String getNamaInstitusi() {
        return namaInstitusi;
    }

    public void setNamaInstitusi(String namaInstitusi) {
        this.namaInstitusi = namaInstitusi;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}

package com.mycillin.user.list;

/**
 * Created by mrbagaskoro on 21-Sep-17.
 */

public class PartnerList {
    private String doctorId;
    private String doctorName;
    private String doctorType;
    private String doctorSpecialization;
    private String doctorPermitt;
    private String doctorPic;
    private String doctorLatitude;
    private String doctorLongitude;
    private String doctorDistance;

    public PartnerList(String doctorId, String doctorName, String doctorType, String doctorSpecialization,
                       String doctorPermitt, String doctorPic, String doctorLatitude, String doctorLongitude,
                       String doctorDistance){
        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.doctorType = doctorType;
        this.doctorSpecialization = doctorSpecialization;
        this.doctorPermitt = doctorPermitt;
        this.doctorPic = doctorPic;
        this.doctorLatitude = doctorLatitude;
        this.doctorLongitude = doctorLongitude;
        this.doctorDistance = doctorDistance;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorType() {
        return doctorType;
    }

    public void setDoctorType(String doctorType) {
        this.doctorType = doctorType;
    }

    public String getDoctorSpecialization() {
        return doctorSpecialization;
    }

    public void setDoctorSpecialization(String doctorSpecialization) {
        this.doctorSpecialization = doctorSpecialization;
    }

    public String getDoctorPermitt() {
        return doctorPermitt;
    }

    public void setDoctorPermitt(String doctorPermitt) {
        this.doctorPermitt = doctorPermitt;
    }

    public String getDoctorPic() {
        return doctorPic;
    }

    public void setDoctorPic(String doctorPic) {
        this.doctorPic = doctorPic;
    }

    public String getDoctorLatitude() {
        return doctorLatitude;
    }

    public void setDoctorLatitude(String doctorLatitude) {
        this.doctorLatitude = doctorLatitude;
    }

    public String getDoctorLongitude() {
        return doctorLongitude;
    }

    public void setDoctorLongitude(String doctorLongitude) {
        this.doctorLongitude = doctorLongitude;
    }

    public String getDoctorDistance() {
        return doctorDistance;
    }

    public void setDoctorDistance(String doctorDistance) {
        this.doctorDistance = doctorDistance;
    }
}

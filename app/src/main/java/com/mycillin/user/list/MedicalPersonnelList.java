package com.mycillin.user.list;

/**
 * Created by mrbagaskoro on 21-Sep-17.
 */

public class MedicalPersonnelList {
    private String doctorName;
    private String doctorType;
    private String doctorPermitt;
    private String doctorPic;

    public MedicalPersonnelList(String doctorName, String doctorType, String doctorPermitt, String doctorPic){
        this.doctorName = doctorName;
        this.doctorType = doctorType;
        this.doctorPermitt = doctorPermitt;
        this.doctorPic = doctorPic;
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
}

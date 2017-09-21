package com.mycillin.user.list;

/**
 * Created by mrbagaskoro on 21-Sep-17.
 */

public class InProgressList {
    private String doctorPic;
    private String bookDoctor;
    private String bookType;
    private String bookDate;
    private String bookTime;

    public InProgressList(String doctorPic, String bookDoctor, String bookType, String bookDate, String bookTime){
        this.doctorPic = doctorPic;
        this.bookDoctor = bookDoctor;
        this.bookType = bookType;
        this.bookDate = bookDate;
        this.bookTime = bookTime;
    }

    public String getDoctorPic() {
        return doctorPic;
    }

    public void setDoctorPic(String doctorPic) {
        this.doctorPic = doctorPic;
    }

    public String getBookDoctor() {
        return bookDoctor;
    }

    public void setBookDoctor(String bookDoctor) {
        this.bookDoctor = bookDoctor;
    }

    public String getBookType() {
        return bookType;
    }

    public void setBookType(String bookType) {
        this.bookType = bookType;
    }

    public String getBookDate() {
        return bookDate;
    }

    public void setBookDate(String bookDate) {
        this.bookDate = bookDate;
    }

    public String getBookTime() {
        return bookTime;
    }

    public void setBookTime(String bookTime) {
        this.bookTime = bookTime;
    }
}

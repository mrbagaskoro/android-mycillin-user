package com.mycillin.user.list;

/**
 * Created by 16003041 on 10/11/2017.
 */

public class ConsultationMenuList {
    private String image;
    private String title;

    public ConsultationMenuList(String image, String title){
        this.image = image;
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

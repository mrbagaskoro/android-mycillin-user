package com.mycillin.user.list;

/**
 * Created by 16003041 on 10/11/2017.
 */

public class ConsultationMenuList {
    private String id;
    private String title;

    public ConsultationMenuList(String id, String title){
        this.id = id;
        this.title = title;
    }

    public String getImage() {
        return id;
    }

    public void setImage(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

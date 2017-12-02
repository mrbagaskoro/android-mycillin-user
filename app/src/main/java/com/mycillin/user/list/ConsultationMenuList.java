package com.mycillin.user.list;

/**
 * Created by 16003041 on 10/11/2017.
 */

public class ConsultationMenuList {
    private String id;
    private String title;
    private String typeId;
    private String specializationId;

    public ConsultationMenuList(String id, String title, String typeId, String specializationId){
        this.id = id;
        this.title = title;
        this.typeId = typeId;
        this.specializationId = specializationId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getSpecializationId() {
        return specializationId;
    }

    public void setSpecializationId(String specializationId) {
        this.specializationId = specializationId;
    }
}

package com.mycillin.user.database;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by 16003041 on 06/11/2017.
 */

@Entity
public class Banner {
    @Id
    @Unique
    @SerializedName("image_id")
    @Expose
    private String imageId;
    @SerializedName("url_link")
    @Expose
    private String urlLink;
    @SerializedName("image_name")
    @Expose
    private String imageName;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("end_date")
    @Expose
    private String endDate;

    @Generated(hash = 1557948810)
    public Banner(String imageId, String urlLink, String imageName,
            String startDate, String endDate) {
        this.imageId = imageId;
        this.urlLink = urlLink;
        this.imageName = imageName;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Generated(hash = 2026719322)
    public Banner() {
    }

    public String getImageId() {
        return this.imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getImageName() {
        return this.imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getStartDate() {
        return this.startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return this.endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getUrlLink() {
        return this.urlLink;
    }

    public void setUrlLink(String urlLink) {
        this.urlLink = urlLink;
    }
}

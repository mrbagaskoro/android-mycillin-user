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
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("base_data")
    @Expose
    private String baseData;

    @Generated(hash = 1142504453)
    public Banner(String imageId, String urlLink, String startDate, String endDate,
            String baseData) {
        this.imageId = imageId;
        this.urlLink = urlLink;
        this.startDate = startDate;
        this.endDate = endDate;
        this.baseData = baseData;
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

    public String getBaseData() {
        return baseData;
    }

    public void setBaseData(String baseData) {
        this.baseData = baseData;
    }
}

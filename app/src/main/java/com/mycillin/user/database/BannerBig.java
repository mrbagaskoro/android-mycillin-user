package com.mycillin.user.database;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by mrbagaskoro on 09-Dec-17.
 */

@Entity
public class BannerBig {
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
    @SerializedName("base_data")
    @Expose
    private String baseData;
    @Generated(hash = 337060831)
    public BannerBig(String imageId, String urlLink, String imageName,
            String startDate, String endDate, String baseData) {
        this.imageId = imageId;
        this.urlLink = urlLink;
        this.imageName = imageName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.baseData = baseData;
    }
    @Generated(hash = 419270683)
    public BannerBig() {
    }
    public String getImageId() {
        return this.imageId;
    }
    public void setImageId(String imageId) {
        this.imageId = imageId;
    }
    public String getUrlLink() {
        return this.urlLink;
    }
    public void setUrlLink(String urlLink) {
        this.urlLink = urlLink;
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
    public String getBaseData() {
        return this.baseData;
    }
    public void setBaseData(String baseData) {
        this.baseData = baseData;
    }
}

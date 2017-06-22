package com.yonyou.hhtpos.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * 作者：liushuofei on 2016/12/23 14:08
 * 邮箱：lsf@yonyou.com
 */
@Entity
public class Position {

    @Id(autoincrement = true)
    private Long id;

    private String name;// APP名称
    private String lng;// 经度
    private String lat;// 纬度
    private String cityName;//城市名称
    private String cityId;// 城市id
    @Generated(hash = 1891629641)
    public Position(Long id, String name, String lng, String lat, String cityName,
            String cityId) {
        this.id = id;
        this.name = name;
        this.lng = lng;
        this.lat = lat;
        this.cityName = cityName;
        this.cityId = cityId;
    }
    @Generated(hash = 958937587)
    public Position() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLng() {
        return this.lng;
    }
    public void setLng(String lng) {
        this.lng = lng;
    }
    public String getLat() {
        return this.lat;
    }
    public void setLat(String lat) {
        this.lat = lat;
    }
    public String getCityName() {
        return this.cityName;
    }
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
    public String getCityId() {
        return this.cityId;
    }
    public void setCityId(String cityId) {
        this.cityId = cityId;
    }
    
}

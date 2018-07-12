package com.smart.tvpos.bean;

/**
 * Created by JoJo on 2018/6/24.
 * wechat：18510829974
 * description：
 */
public class BranchAddressEntity {

    /**
     * name : 上海哈福养老院合同
     * address : 上海 省
     * areaName : 杨浦
     * id : 726
     * cityName : 上海
     * provinceName : 上海市
     */

    private String name;//分院名称
    private String address;//详细地址
    private String areaName;//县
    private int id;
    private String cityName;//市
    private String provinceName;//省
    private String areaId;
    private String cityId;

    public String getAreaId() {
        return areaId == null ? "" : areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getCityId() {
        return cityId == null ? "" : cityId;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address == null ? "" : address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAreaName() {
        return areaName == null ? "" : areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName == null ? "" : cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getProvinceName() {
        return provinceName == null ? "" : provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }
}

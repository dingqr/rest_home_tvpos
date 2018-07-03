package com.smart.tvpos.bean;

/**
 * Created by JoJo on 2018/7/3.
 * wechat:18510829974
 * description:
 */

public class FloorEntity {
    /**
     * floorId : 1
     * floorName : 1F
     * buildingId : 1
     * buildingName : 哈福主楼
     * id : 1
     */

    private int floorId;
    private String floorName;
    private int buildingId;
    private String buildingName;
    private int id;

    public int getFloorId() {
        return floorId;
    }

    public void setFloorId(int floorId) {
        this.floorId = floorId;
    }

    public String getFloorName() {
        return floorName == null ? "" : floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public int getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(int buildingId) {
        this.buildingId = buildingId;
    }

    public String getBuildingName() {
        return buildingName == null ? "" : buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

package com.smart.tvpos.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JoJo on 2018/7/3.
 * wechat:18510829974
 * description: 楼宇实体
 */

public class BuildingEntity {

    /**
     * buildingId : 1
     * buildingName : 哈福主楼
     * list : [{"floorId":1,"floorName":"1F","buildingId":1,"buildingName":"哈福主楼","id":1},{"floorId":2,"floorName":"2F","buildingId":1,"buildingName":"哈福主楼","id":1},{"floorId":3,"floorName":"3F","buildingId":1,"buildingName":"哈福主楼","id":1},{"floorId":4,"floorName":"4F","buildingId":1,"buildingName":"哈福主楼","id":1},{"floorId":5,"floorName":"5F","buildingId":1,"buildingName":"哈福主楼","id":1},{"floorId":6,"floorName":"6F","buildingId":1,"buildingName":"哈福主楼","id":1}]
     */

    private int buildingId;
    private String buildingName;
    private List<FloorEntity> list;

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

    public List<FloorEntity> getList() {
        if (list == null) {
            return new ArrayList<>();
        }
        return list;
    }

    public void setList(List<FloorEntity> list) {
        this.list = list;
    }
}

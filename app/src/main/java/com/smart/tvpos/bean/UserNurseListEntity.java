package com.smart.tvpos.bean;

import android.text.TextUtils;

/**
 * Created by JoJo on 2018/6/26.
 * wechat:18510829974
 * description:
 */

public class UserNurseListEntity {
    /**
     * bedId : 105
     * userId : 1
     * inOutId : 1
     * userName : 萧平章
     * birth : 1955-11-22
     * headImg : /upload/api/credential/1_user20180622042400.jpg
     * sex : 男
     * id : 1
     * bedName : 1号床位
     * roomId : 21
     * floorId : 6
     * buildingId : 1
     * roomName : 601
     * floorName : 6F
     * buildingName : 哈福主楼
     * age : 62
     * numA : 14
     * numF : 14
     * endTimeMax : null
     * typeChild : 心率异常
     */
    /**
     * //     * userName	老人名称
     * //     * buildingName	楼名
     * //     * floorName	楼层
     * //     * roomName	房间
     * //     * bedName	床位
     * //     * headImg	头像
     * //     * age	年龄
     * //     * numA	总护理项目数
     * //     * numF	已完成护理项目数
     * //     * endTimeMax	最后护理时间
     * //     * typeChild	未解决警报类型
     * //
     */

    private int bedId;
    private int userId;
    private int inOutId;
    private String userName; //     * userName	老人名称
    private String birth;
    private String headImg;//     * headImg	头像
    private String sex;
    private int id;
    private String bedName;//     * bedName	床位
    private int roomId;
    private int floorId;
    private int buildingId;
    private String roomName;//     * roomName	房间
    private String floorName;//     * floorName	楼层
    private String buildingName;//     * buildingName	楼名
    private int age;//     * age	年龄
    private int numA;//     * numA	总护理项目数
    private int numF;//     * numF	已完成护理项目数
    private Object endTimeMax;//     * endTimeMax	最后护理时间
    private String typeChild;//     * typeChild	未解决警报类型
    private int warningLevel; //1:一级 2：二级 3 ：三级
    private float nurseProgress;//护理进度
    private String heartbeat;//心跳
    private String breath;//呼吸
    private String typeNurseName;//护理级别

    public String getTypeNurseName() {
        return typeNurseName == null ? "" : typeNurseName;
    }

    public void setTypeNurseName(String typeNurseName) {
        this.typeNurseName = typeNurseName;
    }

    public String getHeartbeat() {
        return (TextUtils.isEmpty(heartbeat) ? "0" : heartbeat);
    }

    public void setHeartbeat(String heartbeat) {
        this.heartbeat = heartbeat;
    }

    public String getBreath() {
        return (TextUtils.isEmpty(breath) ? "0" : breath);
    }

    public void setBreath(String breath) {
        this.breath = breath;
    }

    private String status;//状态 ['掉线','离床','静卧','体动'] 同理,没有则显示未关联设备

    public String getStatus() {
        return (TextUtils.isEmpty(status) ? "未关联" : status);
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getWarningLevel() {
        return warningLevel;
    }

    public void setWarningLevel(int warningLevel) {
        this.warningLevel = warningLevel;
    }

    public int getBedId() {
        return bedId;
    }

    public void setBedId(int bedId) {
        this.bedId = bedId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getInOutId() {
        return inOutId;
    }

    public void setInOutId(int inOutId) {
        this.inOutId = inOutId;
    }

    public String getUserName() {
        return userName == null ? "" : userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBedName() {
        return bedName;
    }

    public void setBedName(String bedName) {
        this.bedName = bedName;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getFloorId() {
        return floorId;
    }

    public void setFloorId(int floorId) {
        this.floorId = floorId;
    }

    public int getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(int buildingId) {
        this.buildingId = buildingId;
    }

    public String getRoomName() {
        return roomName == null ? "" : roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getFloorName() {
        return floorName == null ? "" : floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public String getBuildingName() {
        return buildingName == null ? "" : buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public int getAge() {
        return age < 0 ? 0 : age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getNumA() {
        return numA == 0 ? 1 : numA;
    }

    public void setNumA(int numA) {
        this.numA = numA;
    }

    public int getNumF() {
        return numF;
    }

    public void setNumF(int numF) {
        this.numF = numF;
    }

    public Object getEndTimeMax() {
        return endTimeMax;
    }

    public void setEndTimeMax(Object endTimeMax) {
        this.endTimeMax = endTimeMax;
    }

    public String getTypeChild() {
        return typeChild;
    }

    public void setTypeChild(String typeChild) {
        this.typeChild = typeChild;
    }
}

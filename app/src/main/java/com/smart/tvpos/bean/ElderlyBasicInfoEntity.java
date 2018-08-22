package com.smart.tvpos.bean;

import com.alibaba.fastjson.JSONObject;

public class ElderlyBasicInfoEntity {

    private String height;
    private String habit;
    private String character;
    private String eat;
    private String weight;
    private String buildingName;
    private String floorName;
    private String roomName;
    private String bedName;
    private String nurseName;
    private String age;
    private String birth;
    private String disease;

    public static final String KEY = "details";

    public String getHeight() {
        return height;
    }

    public void setHeibght(String height) {
        this.height = height;
    }

    public String getHabit() {
        return habit;
    }

    public void setHabit(String habit) {
        this.habit = habit;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getEat() {
        return eat;
    }

    public void setEat(String eat) {
        this.eat = eat;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getBedName() {
        return bedName;
    }

    public void setBedName(String bedName) {
        this.bedName = bedName;
    }

    public String getNurseName() {
        return nurseName;
    }

    public void setNurseName(String nurseName) {
        this.nurseName = nurseName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public  ElderlyBasicInfoEntity fromJsonObject(JSONObject jsonObject){

        ElderlyBasicInfoEntity entity = new ElderlyBasicInfoEntity();

        if(jsonObject == null){
            return null;
        }

        entity.setHeibght(jsonObject.getString("height"));
        entity.setHabit(jsonObject.getString("habit"));
        entity.setCharacter(jsonObject.getString("character"));
        entity.setEat(jsonObject.getString("eat"));
        entity.setWeight(jsonObject.getString("weight"));
        entity.setBuildingName(jsonObject.getString("buildingName"));
        entity.setFloorName(jsonObject.getString("floorName"));
        entity.setRoomName(jsonObject.getString("roomName"));
        entity.setBedName(jsonObject.getString("bedName"));
        entity.setNurseName(jsonObject.getString("nurseName"));
        entity.setAge(jsonObject.getString("age"));
        entity.setBirth(jsonObject.getString("birth"));

        return entity;
    }
}

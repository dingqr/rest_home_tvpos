package com.smart.tvpos.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class ElderlyDetailInfoEntity {

    private ElderlyBasicInfoEntity details;
    private List<ElderlyStaffEntity> staffList;
    private List<ElderlyNurseJobEntity> nurseJobList;
    private List<ElderlyHealthDataEntity> healthDataListS;
    private List<ElderlyMattressEntity> mattressList;
    private String averageT;
    private String averageL;

    public ElderlyBasicInfoEntity getDetails() {
        return details;
    }

    public void setDetails(ElderlyBasicInfoEntity details) {
        this.details = details;
    }

    public List<ElderlyStaffEntity> getStaffList() {
        return staffList;
    }

    public void setStaffList(List<ElderlyStaffEntity> staffList) {
        this.staffList = staffList;
    }

    public List<ElderlyNurseJobEntity> getNurseJobList() {
        return nurseJobList;
    }

    public void setNurseJobList(List<ElderlyNurseJobEntity> nurseJobList) {
        this.nurseJobList = nurseJobList;
    }

    public List<ElderlyHealthDataEntity> getHealthDataListS() {
        return healthDataListS;
    }

    public void setHealthDataListS(List<ElderlyHealthDataEntity> healthDataListS) {
        this.healthDataListS = healthDataListS;
    }

    public List<ElderlyMattressEntity> getMattressList() {
        return mattressList;
    }

    public void setMattressList(List<ElderlyMattressEntity> mattressList) {
        this.mattressList = mattressList;
    }

    public String getAverageT() {
        return averageT;
    }

    public void setAverageT(String averageT) {
        this.averageT = averageT;
    }

    public String getAverageL() {
        return averageL;
    }

    public void setAverageL(String averageL) {
        this.averageL = averageL;
    }

    public void fromJsonString(String jsonStr){

        if(null == jsonStr || jsonStr.isEmpty()){
            return;
        }

        JSONObject jsonObject = JSON.parseObject(jsonStr);

        ElderlyBasicInfoEntity basicInfo = new ElderlyBasicInfoEntity();
        details = basicInfo.fromJsonObject(jsonObject.getJSONObject(ElderlyBasicInfoEntity.KEY));
        nurseJobList = fromJsonArray(jsonObject.getJSONArray(ElderlyNurseJobEntity.KEY), ElderlyNurseJobEntity.class);

        healthDataListS = fromJsonArray(jsonObject.getJSONArray(ElderlyHealthDataEntity.Key), ElderlyHealthDataEntity.class);
        mattressList = fromJsonArray(jsonObject.getJSONArray(ElderlyMattressEntity.KEY), ElderlyMattressEntity.class);
        staffList = fromJsonArray(jsonObject.getJSONArray(ElderlyStaffEntity.KEY), ElderlyStaffEntity.class);

        averageT = jsonObject.getString("averageT");
        averageL = jsonObject.getString("averageL");
        return;
    }

    private <T> List<T> fromJsonArray(JSONArray jsonArray, Class clazz){
        List tList = new ArrayList<>();
        if(null == jsonArray || jsonArray.size() == 0){
            return tList;
        }

        for(int i = 0; i < jsonArray.size(); i++){
            tList.add(JSON.parseObject(jsonArray.get(i).toString(), clazz));
        }
        return tList;
    }
}

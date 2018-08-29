package com.smart.tvpos.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONArray;
import com.smart.tvpos.util.CommonUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ElderlyDetailInfoEntity {

    private ElderlyBasicInfoEntity details;
    private List<ElderlyStaffEntity> staffList;
    private List<ElderlyNurseJobEntity> nurseJobList;
    private List<ElderlyHealthDataEntity> healthDataListS;
    private List<ElderlyMattressEntity> mattressList;
    private int averageT;
    private int averageL;

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
        float hour = averageT / 60f;
        DecimalFormat decimalFormat=new DecimalFormat("0.00");
        String result = decimalFormat.format(hour);
        return result;
    }

    public void setAverageT(int averageT) {
        this.averageT = averageT;
    }

    public String getAverageL() {
        float hour = averageL / 60f;
        DecimalFormat decimalFormat=new DecimalFormat("0.00");
        String result = decimalFormat.format(hour);
        return result;
    }

    public void setAverageL(int averageL) {
        this.averageL = averageL;
    }

    public void fromJsonString(String jsonStr){

        if(null == jsonStr || jsonStr.isEmpty()){
            return;
        }

        JSONObject jsonObject = JSON.parseObject(jsonStr);

        ElderlyBasicInfoEntity basicInfo = new ElderlyBasicInfoEntity();
        details = basicInfo.fromJsonObject(jsonObject.getJSONObject(ElderlyBasicInfoEntity.KEY));
        nurseJobList = CommonUtil.fromJsonArray(jsonObject.getJSONArray(ElderlyNurseJobEntity.KEY), ElderlyNurseJobEntity.class);

        healthDataListS = CommonUtil.fromJsonArray(jsonObject.getJSONArray(ElderlyHealthDataEntity.Key), ElderlyHealthDataEntity.class);
        mattressList = CommonUtil.fromJsonArray(jsonObject.getJSONArray(ElderlyMattressEntity.KEY), ElderlyMattressEntity.class);
        staffList = CommonUtil.fromJsonArray(jsonObject.getJSONArray(ElderlyStaffEntity.KEY), ElderlyStaffEntity.class);

        averageT = jsonObject.getInteger("averageT");
        averageL = jsonObject.getInteger("averageL");
        return;
    }
}

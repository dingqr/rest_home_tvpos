package com.smart.tvpos.bean;

public class SleepDataEntity {

    private float deepSleepTime;
    private float lightSleepTime;

    private int day;

    public SleepDataEntity(float deep, float light, int day){
        this.deepSleepTime = deep;
        this.lightSleepTime = light;
        this.day = day;
    }

    public float getDeepSleepTime() {
        return deepSleepTime;
    }

    public void setDeepSleepTime(float deepSleepTime) {
        this.deepSleepTime = deepSleepTime;
    }

    public float getLightSleepTime() {
        return lightSleepTime;
    }

    public void setLightSleepTime(float lightSleepTime) {
        this.lightSleepTime = lightSleepTime;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}

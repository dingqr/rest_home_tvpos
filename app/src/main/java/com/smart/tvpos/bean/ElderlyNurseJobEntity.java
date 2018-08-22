package com.smart.tvpos.bean;

public class ElderlyNurseJobEntity {

    private String id;
    private String imgPath;
    private String imgPathOriginal;

    public static final String KEY = "nurseJob";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getImgPathOriginal() {
        return imgPathOriginal;
    }

    public void setImgPathOriginal(String imgPathOriginal) {
        this.imgPathOriginal = imgPathOriginal;
    }
}

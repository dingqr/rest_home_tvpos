package com.smart.tvpos.bean;

/**
 * Created by JoJo on 2018/6/23.
 * wechat：18510829974
 * description：护理级别实体
 */
public class NurseLevelEntity  {

    /**
     * nurseId : 1
     * num : 29
     * name : 专护
     * id : 1
     */

    private int nurseId;
    private int num;
    private String name;
    private int id;

    public int getNurseId() {
        return nurseId;
    }

    public void setNurseId(int nurseId) {
        this.nurseId = nurseId;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

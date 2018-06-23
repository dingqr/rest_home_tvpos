package com.smart.tvpos.bean;

/**
 * Created by JoJo on 2018/6/22.
 * wechat：18510829974
 * description：首页：入住养老院、用户总数等数据
 */
public class HomeHeadEntity {

    /**
     * num : 145
     * numBed : 138
     * numBranch : 1
     * numIn : 34
     * numUser : 39
     */

    private int num; //床位数+咨询数
    private int numBed;//床位数
    private int numBranch;//养老院数
    private int numIn;//入住床位数
    private int numUser;//入住用户数

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getNumBed() {
        return numBed;
    }

    public void setNumBed(int numBed) {
        this.numBed = numBed;
    }

    public int getNumBranch() {
        return numBranch;
    }

    public void setNumBranch(int numBranch) {
        this.numBranch = numBranch;
    }

    public int getNumIn() {
        return numIn;
    }

    public void setNumIn(int numIn) {
        this.numIn = numIn;
    }

    public int getNumUser() {
        return numUser;
    }

    public void setNumUser(int numUser) {
        this.numUser = numUser;
    }
}

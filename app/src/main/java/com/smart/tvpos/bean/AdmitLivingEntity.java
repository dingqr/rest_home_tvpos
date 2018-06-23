package com.smart.tvpos.bean;

/**
 * Created by JoJo on 2018/6/23.
 * wechat：18510829974
 * description：
 */
public class AdmitLivingEntity {
    /**
     * adminNum : 7
     * inNum : 39
     * outNum : 2
     */

    private int adminNum; //接待
    private int inNum;//入住
    private int outNum;//退住

    public int getAdminNum() {
        return adminNum;
    }

    public void setAdminNum(int adminNum) {
        this.adminNum = adminNum;
    }

    public int getInNum() {
        return inNum;
    }

    public void setInNum(int inNum) {
        this.inNum = inNum;
    }

    public int getOutNum() {
        return outNum;
    }

    public void setOutNum(int outNum) {
        this.outNum = outNum;
    }
}

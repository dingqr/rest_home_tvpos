package com.smart.tvpos.bean;

/**
 * Created by JoJo on 2018/6/23.
 * wechat：18510829974
 * description：
 */
public class StaffEntity {

    /**
     *  护理员-护士-医师-管理人员-工勤人员
     * workType : 护士
     * num : 8
     [
     {
     "workType": "护士",
     "num": 8
     },
     {
     "workType": "护理员",
     "num": 17
     },
     {
     "workType": "管理人员",
     "num": 1
     }
     ]
     */

    private String workType;
    private int num;

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}

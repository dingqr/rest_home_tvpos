package com.smart.tvpos.bean;

/**
 * Created by JoJo on 2018/7/2.
 * wechat:18510829974
 * description:
 */

public class JobItemEntity {
    /**
     * {
     "1": {
     "numA": 23,
     "itemName": "助便",
     "numD": 0
     },
     "2": {
     "numA": 23,
     "itemName": "注射",
     "numD": 0
     },
     "3": {
     "numA": 20,
     "itemName": "输液",
     "numD": 0
     },
     "103": {
     "numA": 1,
     "itemName": "上药费",
     "numD": 0
     },
     "137": {
     "numA": 1,
     "itemName": "",
     "numD": 0
     }
     }
     */
    private int numA;//今日所有项目数
    private String itemName;//项目名称name
    private int numD;//今日已完成项目数

    public int getNumA() {
        return numA;
    }

    public void setNumA(int numA) {
        this.numA = numA;
    }

    public String getItemName() {
        return itemName == null ? "" : itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getNumD() {
        return numD;
    }

    public void setNumD(int numD) {
        this.numD = numD;
    }
}

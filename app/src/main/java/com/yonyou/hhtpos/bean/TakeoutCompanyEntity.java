package com.yonyou.hhtpos.bean;

import java.io.Serializable;

/**
 * Created by ybing on 2017/7/17.
 * 外卖公司实体类
 */

public class TakeoutCompanyEntity implements Serializable {
    private String takeOutCompanyId;
    private String takeOutCompanyName;

    public String getTakeOutCompanyId() {
        return takeOutCompanyId;
    }

    public void setTakeOutCompanyId(String takeOutCompanyId) {
        this.takeOutCompanyId = takeOutCompanyId;
    }

    public String getTakeOutCompanyName() {
        return takeOutCompanyName;
    }

    public void setTakeOutCompanyName(String takeOutCompanyName) {
        this.takeOutCompanyName = takeOutCompanyName;
    }

    @Override
    public String toString() {
        return "TakeoutCompanyEntity{" +
                "takeOutCompanyId='" + takeOutCompanyId + '\'' +
                ", takeOutCompanyName='" + takeOutCompanyName + '\'' +
                '}';
    }
}

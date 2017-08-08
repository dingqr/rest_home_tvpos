package com.yonyou.hhtpos.bean.check;

import java.io.Serializable;

/**
 * 支付方式实体类
 * 作者：liushuofei on 2017/8/8 14:52
 */
public class PayTypeEntity implements Serializable {

    /**主键 */
    private String id;

    /**编码 */
    private String payWayCode;

    /**名称 */
    private String payWayName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPayWayCode() {
        return payWayCode;
    }

    public void setPayWayCode(String payWayCode) {
        this.payWayCode = payWayCode;
    }

    public String getPayWayName() {
        return payWayName;
    }

    public void setPayWayName(String payWayName) {
        this.payWayName = payWayName;
    }

    @Override
    public String toString() {
        return "PayTypeEntity{" +
                "id='" + id + '\'' +
                ", payWayCode='" + payWayCode + '\'' +
                ", payWayName='" + payWayName + '\'' +
                '}';
    }
}

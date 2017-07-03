package com.yonyou.hhtpos.bean;

import java.io.Serializable;

/**
 * Created by zj on 2017/7/1.
 * 邮箱：zjuan@yonyou.com
 * 描述：收款方式实体类
 */
public class PayTypeEntity implements Serializable {
    /**
     * 收款方式唯一标识
     */
    public String id;
    /**
     * 收款类型
     */
    public String pay_type_name;

    public PayTypeEntity() {
    }

    @Override
    public String toString() {
        return "PayTypeEntity{" +
                "id='" + id + '\'' +
                ", pay_type_name='" + pay_type_name + '\'' +
                '}';
    }
}

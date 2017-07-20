package com.yonyou.hhtpos.bean;

import java.io.Serializable;

/**
 * Created by zj on 2017/7/1.
 * 邮箱：zjuan@yonyou.com
 * 描述：服务员实体类
 */
public class WaiterEntity implements Serializable {
    /**服务员id*/
    public String  waiterId;
    /**服务员名称*/
    public String  waiterName;

    public WaiterEntity() {
    }

    @Override
    public String toString() {
        return "WaiterEntity{" +
                "waiterId='" + waiterId + '\'' +
                ", waiterName='" + waiterName + '\'' +
                '}';
    }
}

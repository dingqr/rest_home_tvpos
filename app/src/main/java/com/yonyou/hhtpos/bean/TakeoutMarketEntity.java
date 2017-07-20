package com.yonyou.hhtpos.bean;

import java.io.Serializable;

/**
 * Created by ybing on 2017/7/17.
 * 外卖市别实体类
 */

public class TakeoutMarketEntity implements Serializable {
    private String scheduleId;
    private String scheduleName;

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getScheduleName() {
        return scheduleName;
    }

    public void setScheduleName(String scheduleName) {
        this.scheduleName = scheduleName;
    }

    @Override
    public String toString() {
        return "TakeoutMarketEntity{" +
                "scheduleId='" + scheduleId + '\'' +
                ", scheduleName='" + scheduleName + '\'' +
                '}';
    }
}

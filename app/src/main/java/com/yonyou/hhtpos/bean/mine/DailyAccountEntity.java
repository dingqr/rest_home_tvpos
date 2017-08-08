package com.yonyou.hhtpos.bean.mine;

/**
 * Created by ybing on 2017/8/2.
 * 邮箱：ybing@yonyou.com
 * 描述：收款类型实体类
 */
public class DailyAccountEntity {
    /**
     * 结算时间
     */
    private String settleTime;
    /**
     * 结算操作
     */
    private String settleOption;

    public DailyAccountEntity(String settleTime, String settleOption) {
        this.settleTime = settleTime;
        this.settleOption = settleOption;
    }
    public DailyAccountEntity() {

    }

    public String getSettleTime() {
        return settleTime;
    }

    public void setSettleTime(String settleTime) {
        this.settleTime = settleTime;
    }

    public String getSettleOption() {
        return settleOption;
    }

    public void setSettleOption(String settleOption) {
        this.settleOption = settleOption;
    }
}

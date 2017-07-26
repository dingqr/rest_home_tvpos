package com.yonyou.hhtpos.bean.ts;

import java.io.Serializable;

/**
 * 堂食开单实体类
 * 作者：liushuofei on 2017/7/19 10:06
 */
public class OpenOrderEntity implements Serializable {

    /**整单备注 */
    private String billRemark;
    /**会员手机号 */
    private String memberId;
    /**开单时间 */
    private String openTime;
    /**就餐人数 */
    private String personNum;
    /**	门店id */
    private String shopId;
    /**桌台号 */
    private String tableNo;
    /**服务员id */
    private String waiterId;
    /**桌台状态*/
    private int tableStatus;

    public int getTableStatus() {
        return tableStatus;
    }

    public void setTableStatus(int tableStatus) {
        this.tableStatus = tableStatus;
    }

    public String getBillRemark() {
        return billRemark;
    }

    public void setBillRemark(String billRemark) {
        this.billRemark = billRemark;
    }


    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getPersonNum() {
        return personNum;
    }

    public void setPersonNum(String personNum) {
        this.personNum = personNum;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getTableNo() {
        return tableNo;
    }

    public void setTableNo(String tableNo) {
        this.tableNo = tableNo;
    }

    public String getWaiterId() {
        return waiterId;
    }

    public void setWaiterId(String waiterId) {
        this.waiterId = waiterId;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    @Override
    public String toString() {
        return "OpenOrderEntity{" +
                "billRemark='" + billRemark + '\'' +
                ", memberId='" + memberId + '\'' +
                ", openTime='" + openTime + '\'' +
                ", personNum='" + personNum + '\'' +
                ", shopId='" + shopId + '\'' +
                ", tableNo='" + tableNo + '\'' +
                ", waiterId='" + waiterId + '\'' +
                ", tableStatus='" + tableStatus + '\'' +
                '}';
    }
}

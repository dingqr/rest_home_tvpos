package com.yonyou.hhtpos.bean;

import com.yonyou.framework.library.common.utils.StringUtil;

import java.io.Serializable;

/**
 * Created by zj on 2017/7/8.
 * 邮箱：zjuan@yonyou.com
 * 描述：堂食-桌台实体类
 */
public class CanteenTableEntity implements Serializable {
    /**
     * 账单金额
     */
    private String billMoney;
    /**
     * 结清：收到的金额
     */
    private String receiveAmount;
    /**
     * 预订人数
     */
    public String personNum ;

    /**
     * 桌台容纳人数
     */
    public String seatNum;

    /**
     * 桌台状态：0 空闲 ，1 占用（消费中），2 占用（部分支付），3 占用（锁定），4 占用（结清），5 预订，传（1,2，3,4）为查询占用，不传默认查询全部
     */
    public int tableStatus;

    /**
     * 桌台名称
     */
    public String tableName;
    /**
     * 桌台id
     */
    public String tableID;

    /**
     * 创建时间
     */
    public Long openTime;
    /**
     * 账单生成时间
     */
    public Long billTime;
    /**
     * 是否选中
     */
    public String isCheck;
    /**预定单号*/
    public String billNo;
    /**账单id*/
    public String tableBillId;
    /**桌台操作 0=清台，1=转台，2=并台，3=拼台，4=菜品转台*/
    public int tableOption;
    /**实收金额*/
    private String relReceiveAmount;

    public int getTableOption() {
        return tableOption;
    }

    public void setTableOption(int tableOption) {
        this.tableOption = tableOption;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public CanteenTableEntity() {
    }

    public String getBillMoney() {
        return StringUtil.getFormattedMoney(billMoney);
    }

    public void setBillMoney(String billMoney) {
        this.billMoney = billMoney;
    }

    public String getReceiveAmount() {
        return StringUtil.getFormattedMoney(receiveAmount);
    }

    public void setReceiveAmount(String receiveAmount) {
        this.receiveAmount = receiveAmount;
    }

    public void isCheck(String isCheck) {
        this.isCheck = isCheck;
    }

    public String getTableID() {
        return tableID;
    }

    public void setTableID(String tableID) {
        this.tableID = tableID;
    }

    public String getRelReceiveAmount() {
        return  StringUtil.getFormattedMoney(relReceiveAmount);
    }

    public void setRelReceiveAmount(String relReceiveAmount) {
        this.relReceiveAmount = relReceiveAmount;
    }
}
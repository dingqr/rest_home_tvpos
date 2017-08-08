package com.yonyou.hhtpos.bean.mine;

/**
 * Created by ybing on 2017/8/2.
 * 邮箱：ybing@yonyou.com
 * 描述：收款类型实体类
 */
public class CashTypeEntity {
    /**
     * 收款类型名称
     */
    private String cashTypeName;
    /**
     * 收款金额
     */
    private String cashAmount;

    private boolean isCheck;

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getCashTypeName() {
        return cashTypeName;
    }

    public void setCashTypeName(String cashTypeName) {
        this.cashTypeName = cashTypeName;
    }

    public String getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(String cashAmount) {
        this.cashAmount = cashAmount;
    }
    public CashTypeEntity() {

    }
    public CashTypeEntity(String cashTypeName, String cashAmount) {
        this.cashTypeName = cashTypeName;
        this.cashAmount = cashAmount;
    }
    public CashTypeEntity(String cashTypeName, boolean isCheck) {
        this.cashTypeName = cashTypeName;
       this.isCheck = isCheck;
    }
}

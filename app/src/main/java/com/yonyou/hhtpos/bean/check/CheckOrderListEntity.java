package com.yonyou.hhtpos.bean.check;

import com.yonyou.framework.library.common.utils.StringUtil;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zj on 2017/7/28.
 * 邮箱：zjuan@yonyou.com
 * 描述：结账-下单菜品列表实体类
 */
public class CheckOrderListEntity implements Serializable {
    /**
     * 桌台唯一标识
     */
    public String tableId;
    /**
     * 桌台名称
     */
    public String tableName;
    /**
     * 异常菜品列表（赠／退）
     */
    public List<AbnormalOrderedEntity> abnormalList;
    /**
     * companyId
     */
    public String companyId;
    /**
     * details
     */
    public String details;
    /**
     * 异常标识
     */
    public String dishAbnormalStatus;
    /**
     * 菜类
     */
    public String dishClassId;
    /**
     * dishId
     */
    public String dishId;
    /**
     * 菜品名称
     */
    public String dishName;
    /**
     * 菜品价格
     */
    private String dishPrice;
    /**
     * dishRelateId
     */
    public String dishRelateId;
    /**
     * dishStatus
     */
    public String dishStatus;
    /**
     * dishType
     */
    public String dishType;
    /**
     * id
     */
    public String id;
    /**
     * isPrint
     */
    public String isPrint;
    /**
     * isSync
     */
    public String isSync;
    /**
     * 做法
     */
    public String listShowPractice;
    /**
     * 备注
     */
    public String listShowRemark;
    /**
     * 备注
     */
    public String remark;
    /**
     * remarks
     */
    public String remarks;
    /**
     * 规格
     */
    public String standardId;
    /**
     * 下单时间
     */
    public Long orderTime;
    /**
     * practices
     */
    public String practices;
    /**
     * proOcDishListDetails
     */
    public String proOcDishListDetails;

    /**
     * 数量
     */
    public String quantity;
    /**
     * 账单id
     */
    public String tableBillId;
    /**
     * unit
     */
    public String unit;

    public CheckOrderListEntity() {
    }

    public String getDishPrice() {
        return StringUtil.getFormattedMoney(dishPrice);
    }

    public void setDishPrice(String dishPrice) {
        this.dishPrice = dishPrice;
    }

    @Override
    public String toString() {
        return "CheckOrderListEntity{" +
                "abnormalList=" + abnormalList +
                ", companyId='" + companyId + '\'' +
                ", details='" + details + '\'' +
                ", dishAbnormalStatus='" + dishAbnormalStatus + '\'' +
                ", dishClassId='" + dishClassId + '\'' +
                ", dishId='" + dishId + '\'' +
                ", dishName='" + dishName + '\'' +
                ", dishPrice='" + dishPrice + '\'' +
                ", dishRelateId='" + dishRelateId + '\'' +
                ", dishStatus='" + dishStatus + '\'' +
                ", dishType='" + dishType + '\'' +
                ", id='" + id + '\'' +
                ", isPrint='" + isPrint + '\'' +
                ", isSync='" + isSync + '\'' +
                ", listShowPractice='" + listShowPractice + '\'' +
                ", listShowRemark='" + listShowRemark + '\'' +
                ", remark='" + remark + '\'' +
                ", remarks='" + remarks + '\'' +
                ", standardId='" + standardId + '\'' +
                ", orderTime='" + orderTime + '\'' +
                ", practices='" + practices + '\'' +
                ", proOcDishListDetails='" + proOcDishListDetails + '\'' +
                ", quantity='" + quantity + '\'' +
                ", tableBillId='" + tableBillId + '\'' +
                ", unit='" + unit + '\'' +
                '}';
    }
}

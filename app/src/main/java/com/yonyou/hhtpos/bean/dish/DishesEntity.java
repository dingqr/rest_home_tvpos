package com.yonyou.hhtpos.bean.dish;

import com.yonyou.framework.library.common.utils.StringUtil;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zj on 2017/7/15.
 * 邮箱：zjuan@yonyou.com
 * 描述：外卖-点菜列表实体类
 */
public class DishesEntity implements Serializable {
    /**
     * 套餐菜品价格
     */
    private String comboDishPrice;

    /**
     * 适合人数
     */
    public int comboForPsnnum;

    /**
     * 公司id
     */
    public String companyId;

    /**
     * 描述
     */
    public String description;

    /**
     * 菜品编码
     */
    public String dishCode;

    /**
     * 菜品名称
     */
    public String dishName;

    /**
     * 菜品类型
     */
    public String dishType;
    /**
     * 与菜类关联的菜品id
     */
    public String dishTypeRelateId;

    /**
     * 套餐菜品组
     */
    public List<GroupDishEntity> groups;

    /**
     * id
     */
    public String id;

    /**
     * 介绍
     */
    public String introduction;

    /**
     * 标签列表
     */
    public List<DishLabelEntity> labels;

    /**
     * 做法列表
     */
    public List<DishPriceEntity> practices;

    /**
     * 关联id
     */
    public String relateId;

    /**
     * 备注
     */
    public String remark;

    /***/
    public List<DishRemarkEntity> remarks;

    /**
     * 销售模式
     */
    public String saleManne;

    /**
     * 门店菜类关联ID
     */
    public String shopDishTypeRelateId;
    /**
     * shopId
     */
    public String shopId;
    /**
     * 排序号
     */
    public String sortNo;

    /**
     * 规格列表
     */
    public List<DishStandardEntity> standards;

    /**
     * 口味列表
     */
    public List<DishTastesEntity> tastes;

    /**
     * 标记是否选中
     */
    public boolean isCheck;

    public DishesEntity() {
    }

    public String getComboDishPrice() {
        return StringUtil.getFormattedMoney(comboDishPrice);
    }

    public void setComboDishPrice(String comboDishPrice) {
        this.comboDishPrice = comboDishPrice;
    }

    @Override
    public String toString() {
        return "DishesEntity{" +
                "comboDishPrice='" + comboDishPrice + '\'' +
                ", comboForPsnnum=" + comboForPsnnum +
                ", companyId='" + companyId + '\'' +
                ", description='" + description + '\'' +
                ", dishCode='" + dishCode + '\'' +
                ", dishName='" + dishName + '\'' +
                ", dishType='" + dishType + '\'' +
                ", groups=" + groups +
                ", id='" + id + '\'' +
                ", introduction='" + introduction + '\'' +
                ", Labels=" + labels +
                ", practices=" + practices +
                ", relateId='" + relateId + '\'' +
                ", remark='" + remark + '\'' +
                ", remarks=" + remarks +
                ", saleManne='" + saleManne + '\'' +
                ", shopDishTypeRelateId='" + shopDishTypeRelateId + '\'' +
                ", shopId='" + shopId + '\'' +
                ", sortNo='" + sortNo + '\'' +
                ", standards=" + standards +
                ", tastes=" + tastes +
                '}';
    }
}

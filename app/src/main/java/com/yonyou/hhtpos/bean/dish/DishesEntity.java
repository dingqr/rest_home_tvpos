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
     * 套餐包含菜品集合
     */
    public List<RecommendDishList> dishStandards;

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
     * 菜品类型：菜品：1，固定套餐：2，N选N套餐：3，临时菜：4
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
     * 是否打折
     */
    public String isDiscount;
    /**
     * 赠与否
     */
    public String isGift;
    /**
     * 是否是称重菜
     */
    public String isWeigh;
    /**
     * 是否是时价菜
     */
    public String isCurrentDish;
    /**
     * 菜品价格
     */
    private String price;
    /**
     * 菜品会员价格
     */
    private String vipPrice;
    /**
     * 标签列表
     */
    public List<DishLabelEntity> labels;

    /**
     * 做法列表
     */

    public List<DishPracticeEntity> practices;

    /***/
    public String printOutTypeId;

    /**
     * 关联id
     */
    public String relateId;


    /**
     * 备注列表
     */
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
     * 重量
     */
    public String weight;

    /**
     * 标记是否选中
     */
    public boolean isCheck;
    /**
     * 单位
     */
    public DishUnitEntity dishUnit;

    public DishesEntity() {
    }

    public String getComboDishPrice() {
        return StringUtil.getFormattedMoney(comboDishPrice);
    }

    public void setComboDishPrice(String comboDishPrice) {
        this.comboDishPrice = comboDishPrice;
    }

    public String getPrice() {
        return StringUtil.getFormattedMoney(price);
    }

    public void setPrice(String dishPrice) {
        this.price = dishPrice;
    }

    public String getVipPrice() {
        return StringUtil.getFormattedMoney(vipPrice);
    }

    public void setVipPrice(String vipPrice) {
        this.vipPrice = vipPrice;
    }

    public class DishUnitEntity implements Serializable {
        public DishUnitEntity() {
        }

        private String id;
        private String unitName;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUnitName() {
            return unitName;
        }

        public void setUnitName(String unitName) {
            this.unitName = unitName;
        }
    }

    public class RecommendDishList implements Serializable {
        public RecommendDishList() {
        }
        private String companyId;
        private String countState;
        private String dishPrice;
        private String showName;
        private String dishRelateId;
        private String id;
        private List<DishLabelEntity> labels;
        private List<DishPracticeEntity> practices;
        private String relateId;
        private List<DishRemarkEntity> remarks;
        private List<DishStandardEntity> standards;
        private List<DishTastesEntity> tastes;
        private String isDefault;

        public String getCompanyId() {
            return companyId;
        }

        public void setCompanyId(String companyId) {
            this.companyId = companyId;
        }

        public String getCountState() {
            return countState;
        }

        public void setCountState(String countState) {
            this.countState = countState;
        }

        public String getDishPrice() {
            return StringUtil.getFormattedMoney(dishPrice);
        }

        public void setDishPrice(String dishPrice) {
            this.dishPrice = dishPrice;
        }

        public String getShowName() {
            return showName;
        }

        public void setShowName(String showName) {
            this.showName = showName;
        }

        public String getDishRelateId() {
            return dishRelateId;
        }

        public void setDishRelateId(String dishRelateId) {
            this.dishRelateId = dishRelateId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<DishLabelEntity> getLabels() {
            return labels;
        }

        public void setLabels(List<DishLabelEntity> labels) {
            this.labels = labels;
        }

        public List<DishPracticeEntity> getPractices() {
            return practices;
        }

        public void setPractices(List<DishPracticeEntity> practices) {
            this.practices = practices;
        }

        public String getRelateId() {
            return relateId;
        }

        public void setRelateId(String relateId) {
            this.relateId = relateId;
        }

        public List<DishRemarkEntity> getRemarks() {
            return remarks;
        }

        public void setRemarks(List<DishRemarkEntity> remarks) {
            this.remarks = remarks;
        }

        public List<DishStandardEntity> getStandards() {
            return standards;
        }

        public void setStandards(List<DishStandardEntity> standards) {
            this.standards = standards;
        }

        public List<DishTastesEntity> getTastes() {
            return tastes;
        }

        public void setTastes(List<DishTastesEntity> tastes) {
            this.tastes = tastes;
        }

        public String getIsDefault() {
            return isDefault;
        }

        public void setIsDefault(String isDefault) {
            this.isDefault = isDefault;
        }

        @Override
        public String toString() {
            return "RecommendDishList{" +
                    "companyId='" + companyId + '\'' +
                    ", countState='" + countState + '\'' +
                    ", dishPrice='" + dishPrice + '\'' +
                    ", showName='" + showName + '\'' +
                    ", dishRelateId='" + dishRelateId + '\'' +
                    ", id='" + id + '\'' +
                    ", labels=" + labels +
                    ", practices=" + practices +
                    ", relateId='" + relateId + '\'' +
                    ", remarks=" + remarks +
                    ", standards=" + standards +
                    ", tastes=" + tastes +
                    ", isDefault='" + isDefault + '\'' +
                    '}';
        }
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
                ", dishTypeRelateId='" + dishTypeRelateId + '\'' +
                ", groups=" + groups +
                ", id='" + id + '\'' +
                ", introduction='" + introduction + '\'' +
                ", isDiscount='" + isDiscount + '\'' +
                ", isGift='" + isGift + '\'' +
                ", isWeigh='" + isWeigh + '\'' +
                ", isCurrentDish='" + isCurrentDish + '\'' +
                ", price='" + price + '\'' +
                ", vipPrice='" + vipPrice + '\'' +
                ", labels=" + labels +
                ", practices=" + practices +
                ", printOutTypeId='" + printOutTypeId + '\'' +
                ", relateId='" + relateId + '\'' +
                ", remarks=" + remarks +
                ", saleManne='" + saleManne + '\'' +
                ", shopDishTypeRelateId='" + shopDishTypeRelateId + '\'' +
                ", shopId='" + shopId + '\'' +
                ", sortNo='" + sortNo + '\'' +
                ", standards=" + standards +
                ", tastes=" + tastes +
                ", weight='" + weight + '\'' +
                ", isCheck=" + isCheck +
                ", dishUnit='" + dishUnit + '\'' +
                '}';
    }
}

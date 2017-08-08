package com.yonyou.hhtpos.bean;

import com.yonyou.framework.library.common.utils.StringUtil;
import com.yonyou.hhtpos.bean.dish.DishLabelEntity;
import com.yonyou.hhtpos.bean.dish.DishPracticeEntity;
import com.yonyou.hhtpos.bean.dish.DishRemarkEntity;
import com.yonyou.hhtpos.bean.dish.DishStandardEntity;
import com.yonyou.hhtpos.bean.dish.DishTastesEntity;
import com.yonyou.hhtpos.bean.dish.GroupDishEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zj on 2017/8/7.
 * 邮箱：zjuan@yonyou.com
 * 描述：推荐套餐菜品实体类
 */
public class RecommendDataEntity implements Serializable {
    public RecommendDataEntity() {
    }

    /**
     * 套餐总价格
     */
    public String comboDishPrice;

    /**
     * comboForPsnnum
     */
    public String comboForPsnnum;
    /**
     * 描述
     */
    public String description;

    /**
     * 套餐名称
     */
    public String dishName;
//    /**
//     * 套餐包含菜品集合
//     */
    public List<RecommendDishList> dishStandards;
    public String dishType;
    /**
     * 套餐菜品组
     */
    public List<GroupDishEntity> groups;
    public String id;
    /**
     * 是否是时价菜
     */
    public String isCurrentDish;
    /**
     * 是否打折
     */
    public String isDiscount;
    /**
     * 退赠与否
     */
    public String isGift;
    /**
     * 是否称重
     */
    public String isWeigh;

    public String relateId;
    public List<DishLabelEntity> labels;
    public List<DishPracticeEntity> practices;
    public List<DishRemarkEntity> remarks;
    public List<DishStandardEntity> standards;
    public List<DishTastesEntity> tastes;

    public String saleManner;

    private class RecommendDishList implements Serializable {
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
                    '}';
        }
    }

    public String getComboDishPrice() {
        return StringUtil.getFormatDiscount(comboDishPrice);
    }

    public void setComboDishPrice(String comboDishPrice) {
        this.comboDishPrice = comboDishPrice;
    }

    @Override
    public String toString() {
        return "RecommendDataEntity{" +
                "comboDishPrice='" + comboDishPrice + '\'' +
                ", comboForPsnnum='" + comboForPsnnum + '\'' +
                ", description='" + description + '\'' +
                ", dishName='" + dishName + '\'' +
                ", dishStandards=" + dishStandards +
                ", dishType='" + dishType + '\'' +
                ", groups=" + groups +
                ", id='" + id + '\'' +
                ", isCurrentDish='" + isCurrentDish + '\'' +
                ", isDiscount='" + isDiscount + '\'' +
                ", isGift='" + isGift + '\'' +
                ", isWeigh='" + isWeigh + '\'' +
                ", relateId='" + relateId + '\'' +
                ", labels=" + labels +
                ", practices=" + practices +
                ", remarks=" + remarks +
                ", standards=" + standards +
                ", tastes=" + tastes +
                ", saleManner='" + saleManner + '\'' +
                '}';
    }
}

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
     * comboForPsnnum
     */
    public String comboForPsnnum;

    /**
     * 套餐名称
     */
    public String dishName;
    /**
     * 套餐包含菜品集合
     */
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

    public String price;
//    public String saleManner;

    private class RecommendDishList {
        public RecommendDishList() {
        }

        private String companyId;
        private String countState;
        private String dishPrice;
        private String dishRelateId;
        private String id;
        private List<DishLabelEntity> labels;
        private List<DishPracticeEntity> practices;
        private String relateId;
        private List<DishRemarkEntity> remarks;
        private List<DishStandardEntity> standards;
        private List<DishTastesEntity> tastes;
    }

    public List<RecommendDishList> getDishStandards() {
        return dishStandards;
    }

    public void setDishStandards(List<RecommendDishList> dishStandards) {
        this.dishStandards = dishStandards;
    }

    public String getPrice() {
        return StringUtil.getFormattedMoney(price);
    }

    public void setPrice(String price) {
        this.price = price;
    }
}

package com.yonyou.hhtpos.bean.dish;

import com.yonyou.framework.library.common.utils.StringUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zj on 2017/7/21.
 * 邮箱：zjuan@yonyou.com
 * 描述：点菜时，传入不同弹窗的实体
 */
public class DataBean implements Serializable {
    /**
     * 菜品名称
     */
    private String dishName;
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
    private List<DishLabelEntity> labels;
    /**
     * 口味列表
     */
    private List<DishTastesEntity> tastes;


    private List<DishPracticeEntity> practices = new ArrayList<>();
    private List<DishRemarkEntity> remarks = new ArrayList<>();
    private List<DishStandardEntity> standards = new ArrayList<>();

    public List<DishPracticeEntity> getPractices() {
        return practices;
    }

    public void setPractices(List<DishPracticeEntity> practices) {
        this.practices = practices;
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

    public List<DishLabelEntity> getLabels() {
        return labels;
    }

    public void setLabels(List<DishLabelEntity> labels) {
        this.labels = labels;
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

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    @Override
    public String toString() {
        return "DataBean{" +
                "dishName='" + dishName + '\'' +
                ", price='" + price + '\'' +
                ", vipPrice='" + vipPrice + '\'' +
                ", labels=" + labels +
                ", tastes=" + tastes +
                ", practices=" + practices +
                ", remarks=" + remarks +
                ", standards=" + standards +
                '}';
    }
}

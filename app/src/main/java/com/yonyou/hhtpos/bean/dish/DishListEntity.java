package com.yonyou.hhtpos.bean.dish;

import java.io.Serializable;
import java.util.List;

/**
 * 已点菜品列表实体类
 * 作者：liushuofei on 2017/7/17 19:16
 */
public class DishListEntity implements Serializable {

    /**菜类已点个数 */
    //private List<String> dishTypeNum;
    /**已点菜品 */
    private List<Dishes> dishes;
    /**已点菜品分类数量 */
    private List<DishType> dishTypelist;

    public List<Dishes> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dishes> dishes) {
        this.dishes = dishes;
    }

    public List<DishType> getDishTypelist() {
        return dishTypelist;
    }

    public void setDishTypelist(List<DishType> dishTypelist) {
        this.dishTypelist = dishTypelist;
    }

    /**
     * 菜品实体
     */
    public class Dishes implements Serializable{
//        /**套餐详情列表 */
//        private List<String> details;
//        /**分类id */
//        private String dishClassId;
        /**名称 */
        private String dishName;
        /**价格 */
        private String dishPrice;
        /**状态 */
        private String dishStatus;
//        /**类型 */
//        private String dishType;
        /**展示做法 */
        private String listShowPractice;
        /**展示备注 */
        private String listShowRemark;
//        /**规格id列表 */
//        private List<String> practices;
        /**数量 */
        private int quantity;
//        /**菜品备注 */
//        //private String remark;
//        /**备注id列表 */
//        private List<String> remarks;
//        /**规格ID */
//        private String standardId;

        /**下单时间 */
        private String orderTime;

        /**是否为称重菜（0：不是  1：是） */
        private String unit;

        /**id */
        private String id;

        public String getDishName() {
            return dishName;
        }

        public void setDishName(String dishName) {
            this.dishName = dishName;
        }

        public String getDishPrice() {
            return dishPrice;
        }

        public void setDishPrice(String dishPrice) {
            this.dishPrice = dishPrice;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public String getDishStatus() {
            return dishStatus;
        }

        public void setDishStatus(String dishStatus) {
            this.dishStatus = dishStatus;
        }

        public String getOrderTime() {
            return orderTime;
        }

        public void setOrderTime(String orderTime) {
            this.orderTime = orderTime;
        }

        public String getListShowPractice() {
            return listShowPractice;
        }

        public void setListShowPractice(String listShowPractice) {
            this.listShowPractice = listShowPractice;
        }

        public String getListShowRemark() {
            return listShowRemark;
        }

        public void setListShowRemark(String listShowRemark) {
            this.listShowRemark = listShowRemark;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public class DishType implements Serializable{
        /**菜品id */
        private String dishId;
        /**数量 */
        private String quantity;

        public String getDishId() {
            return dishId;
        }

        public void setDishId(String dishId) {
            this.dishId = dishId;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }
    }
}

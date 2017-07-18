package com.yonyou.hhtpos.bean.dish;

import java.io.Serializable;
import java.util.List;

/**
 * 已点菜品列表实体类
 * 作者：liushuofei on 2017/7/17 19:16
 */
public class DishListEntity implements Serializable {

    /**菜类已点个数 */
    private List<String> dishTypeNum;
    /**已点菜品 */
    private List<Dishes> dishes;

    public List<String> getDishTypeNum() {
        return dishTypeNum;
    }

    public void setDishTypeNum(List<String> dishTypeNum) {
        this.dishTypeNum = dishTypeNum;
    }

    public List<Dishes> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dishes> dishes) {
        this.dishes = dishes;
    }

    public class Dishes implements Serializable{
        /**套餐详情列表 */
        private List<String> details;
        /**分类id */
        private String dishClassId;
        /**名称 */
        private String dishName;
        /**价格 */
        private String dishPrice;
        /**状态 */
        private String dishStatus;
        /**类型 */
        private String dishType;
        /**展示做法 */
        private String listShowPractice;
        /**展示备注 */
        private String listShowRemark;
        /**规格id列表 */
        private List<String> practices;
        /**数量 */
        private String quantity;
        /**菜品备注 */
        private String remark;
        /**备注id列表 */
        private List<String> remarks;
        /**规格ID */
        private String standardId;

        public List<String> getDetails() {
            return details;
        }

        public void setDetails(List<String> details) {
            this.details = details;
        }

        public String getDishClassId() {
            return dishClassId;
        }

        public void setDishClassId(String dishClassId) {
            this.dishClassId = dishClassId;
        }

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

        public String getDishStatus() {
            return dishStatus;
        }

        public void setDishStatus(String dishStatus) {
            this.dishStatus = dishStatus;
        }

        public String getDishType() {
            return dishType;
        }

        public void setDishType(String dishType) {
            this.dishType = dishType;
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

        public List<String> getPractices() {
            return practices;
        }

        public void setPractices(List<String> practices) {
            this.practices = practices;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public List<String> getRemarks() {
            return remarks;
        }

        public void setRemarks(List<String> remarks) {
            this.remarks = remarks;
        }

        public String getStandardId() {
            return standardId;
        }

        public void setStandardId(String standardId) {
            this.standardId = standardId;
        }
    }
}

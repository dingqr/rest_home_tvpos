package com.yonyou.hhtpos.interactor;

/**
 * Created by zj on 2017/8/7.
 * 邮箱：zjuan@yonyou.com
 * 描述：查询所有推荐套餐
 */
public interface IRecommendDishInteractor {
    void getRecommendDishes(String compId, String shopId, int saleManner);
}

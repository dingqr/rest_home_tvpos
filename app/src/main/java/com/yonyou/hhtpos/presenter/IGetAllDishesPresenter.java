package com.yonyou.hhtpos.presenter;

/**
 * Created by zj on 2017/7/15.
 * 邮箱：zjuan@yonyou.com
 * 描述：获取所有菜类/菜品
 */
public interface IGetAllDishesPresenter  {
    void getAllDishes(String compId,String shopId,int saleManner);
}

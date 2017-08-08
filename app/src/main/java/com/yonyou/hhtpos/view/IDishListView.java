package com.yonyou.hhtpos.view;

import com.yonyou.framework.library.view.BaseView;
import com.yonyou.hhtpos.bean.dish.DishListEntity;

import java.util.List;

/**
 * 作者：liushuofei on 2017/7/17 19:12
 * 邮箱：lsf@yonyou.com
 */
public interface IDishListView extends BaseView {

    /**
     * 请求已点菜品列表
     * @param bean 已点菜品数据
     */
    void requestDishList(DishListEntity bean);

    /**
     * 下单
     */
    void requestPlaceOrder();

    /**
     * 删除未下单菜品接口
     */
    void deleteNoOrderDishes();
}

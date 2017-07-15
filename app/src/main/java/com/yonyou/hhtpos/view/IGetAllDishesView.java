package com.yonyou.hhtpos.view;

import com.yonyou.framework.library.view.BaseView;
import com.yonyou.hhtpos.bean.dish.DishDataEntity;

/**
 * Created by zj on 2017/7/15.
 * 邮箱：zjuan@yonyou.com
 * 描述：获取菜品-View层接口
 */
public interface IGetAllDishesView extends BaseView {
    void getAllDishes(DishDataEntity dishDataEntity);
}

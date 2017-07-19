package com.yonyou.hhtpos.presenter;

import com.yonyou.hhtpos.bean.dish.RequestAddDishEntity;

/**
 * Created by zj on 2017/7/19.
 * 邮箱：zjuan@yonyou.com
 * 描述：新加菜品请求参数实体类-（（套餐、临时菜、菜品））
 */
public interface IAddDishPresenter {
    void requestAddDish(RequestAddDishEntity requestAddDishEntity);
}

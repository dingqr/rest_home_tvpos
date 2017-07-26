package com.yonyou.hhtpos.view;

import com.yonyou.framework.library.view.BaseView;

/**
 * 作者：liushuofei on 2017/7/22 11:47
 * 邮箱：lsf@yonyou.com
 */
public interface IDishEditView extends BaseView {

    /**
     * 修改数量接口成功回调
     */
    void updateQuantitySuccess();

    /**
     * 修改菜品接口成功回调
     */
    void updateDishSuccess();

    /**
     * 删除菜品接口成功回调
     */
    void deleteDishSuccess();

    /**
     * 修改菜品状态接口成功回调
     */
    void updateDishStatusSuccess();

    /**
     * 退菜和赠菜成功回调
     */
    void handleDishSuccess();

    /**
     * 称重确认接口成功回调
     */
    void confirmWeightSuccess();
}

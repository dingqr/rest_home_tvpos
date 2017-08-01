package com.yonyou.hhtpos.interactor;

import com.yonyou.hhtpos.bean.MealAreaEntity;

/**
 * 作者：ybing on 2017/8/1 10:10
 * 邮箱：ybing@yonyou.com
 */
public interface ITSTableAreaInteractor {
    /**
     * 获取右侧餐区列表
     *
     */
    void getTableList(String shopId,String diningAreaRelatedId,String tableStatus);
}

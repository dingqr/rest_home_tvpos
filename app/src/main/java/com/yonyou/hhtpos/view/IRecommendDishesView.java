package com.yonyou.hhtpos.view;

import com.yonyou.framework.library.view.BaseView;
import com.yonyou.hhtpos.bean.RecommendDataEntity;

import java.util.List;

/**
 * Created by zj on 2017/7/15.
 * 邮箱：zjuan@yonyou.com
 * 描述：查询所有推荐套餐-View层接口
 */
public interface IRecommendDishesView extends BaseView {
    void getRecommendDishes(List<RecommendDataEntity> dataList);
}

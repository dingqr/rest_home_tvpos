package com.yonyou.hhtpos.view;

import com.yonyou.framework.library.view.BaseView;
import com.yonyou.hhtpos.bean.check.DiscountEntity;

import java.util.List;

/**
 * Created by zj on 2017/8/7.
 * 邮箱：zjuan@yonyou.com
 * 描述：获取所有折扣方案
 */
public interface IDiscountPlanView extends BaseView {
    void getAllDiscountPlan(List<DiscountEntity> dataList);
}

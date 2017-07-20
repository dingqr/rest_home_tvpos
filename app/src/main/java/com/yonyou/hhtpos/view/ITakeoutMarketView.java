package com.yonyou.hhtpos.view;

import com.yonyou.framework.library.view.BaseView;
import com.yonyou.hhtpos.bean.TakeoutMarketEntity;

import java.util.List;

/**
 * Created by ybing on 2017/7/5.
 * 获取外卖筛选框中的外卖市别
 */

public interface ITakeoutMarketView extends BaseView{
    void getAllTakeOutMarket(List<TakeoutMarketEntity> list);
}

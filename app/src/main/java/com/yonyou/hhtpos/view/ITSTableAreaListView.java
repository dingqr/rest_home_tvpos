package com.yonyou.hhtpos.view;

import com.yonyou.framework.library.view.BaseView;
import com.yonyou.hhtpos.bean.MealAreaEntity;

import java.util.List;

/**
 * Created by ybing on 2017/8/1.
 * 堂食右侧导航 获取所有餐区列表
 */

public interface ITSTableAreaListView extends BaseView {
    void getTableAreaList( List<MealAreaEntity> bean);
}

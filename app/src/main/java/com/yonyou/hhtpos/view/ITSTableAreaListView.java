package com.yonyou.hhtpos.view;

import com.yonyou.framework.library.view.BaseView;
import com.yonyou.hhtpos.bean.MealAreaEntity;

/**
 * Created by ybing on 2017/8/1.
 */

public interface ITSTableAreaListView extends BaseView {
    void getTableAreaList(MealAreaEntity bean);
}

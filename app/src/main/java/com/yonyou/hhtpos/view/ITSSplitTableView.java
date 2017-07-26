package com.yonyou.hhtpos.view;


import com.yonyou.framework.library.view.BaseView;
import com.yonyou.hhtpos.bean.CanteenTableEntity;

/**
 * Created by ybing on 2017/7/25.
 * 堂食拼台view
 */

public interface ITSSplitTableView extends BaseView {
    void splitOrder(CanteenTableEntity canteenTableEntity);
}

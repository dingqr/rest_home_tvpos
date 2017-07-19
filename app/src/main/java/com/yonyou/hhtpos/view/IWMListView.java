package com.yonyou.hhtpos.view;

import com.yonyou.framework.library.view.BaseView;
import com.yonyou.hhtpos.bean.wm.OrderListEntity;

import java.util.List;

/**
 * 作者：liushuofei on 2017/7/14 18:02
 * 邮箱：lsf@yonyou.com
 */
public interface IWMListView extends BaseView {
    /**
     * 请求外卖列表
     * @param dataList 外卖列表数据
     * @param isRefresh 是否为刷新
     */
    void requestTakeOutList(List<OrderListEntity> dataList, boolean isRefresh);
}

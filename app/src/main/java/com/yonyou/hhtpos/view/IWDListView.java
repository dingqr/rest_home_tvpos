package com.yonyou.hhtpos.view;

import com.yonyou.framework.library.view.BaseView;
import com.yonyou.hhtpos.bean.wd.OrderListEntity;

import java.util.List;

/**
 * 作者：liushuofei on 2017/7/17 16:24
 * 邮箱：lsf@yonyou.com
 */
public interface IWDListView extends BaseView{

    /**
     * 请求外带列表
     * @param dataList 外带列表数据
     * @param isRefresh 是否为刷新
     */
    void requestPackingList(List<OrderListEntity> dataList, boolean isRefresh);
}

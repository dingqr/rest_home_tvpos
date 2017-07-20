package com.yonyou.hhtpos.view;

import com.yonyou.framework.library.view.BaseView;
import com.yonyou.hhtpos.bean.CanteenTableEntity;

import java.util.List;

/**
 * Created by zj on 2017/7/20.
 * 邮箱：zjuan@yonyou.com
 * 描述：堂食-查询所有桌台
 */
public interface ITableListView extends BaseView {
    void requestTableList(List<CanteenTableEntity> tableList);
}

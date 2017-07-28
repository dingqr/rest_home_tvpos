package com.yonyou.hhtpos.view;


import com.yonyou.framework.library.view.BaseView;
import com.yonyou.hhtpos.bean.CanteenTableEntity;

import java.util.List;

/**
 * Created by ybing on 2017/7/27.
 * 邮箱：ybing@yonyou.com
 * 描述：堂食-不同桌台操作下查询所有可用的桌台
 */

public interface ITSFiltrateTableView extends BaseView {
    void filtrateTable(List<CanteenTableEntity> tableList);
}

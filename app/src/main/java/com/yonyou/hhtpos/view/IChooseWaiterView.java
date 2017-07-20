package com.yonyou.hhtpos.view;

import com.yonyou.framework.library.view.BaseView;
import com.yonyou.hhtpos.bean.WaiterEntity;

import java.util.List;

/**
 * Created by zj on 2017/7/20.
 * 邮箱：zjuan@yonyou.com
 * 描述：选择服务员
 */
public interface IChooseWaiterView extends BaseView {
    void requestWaiterList(List<WaiterEntity> waiterList);
}

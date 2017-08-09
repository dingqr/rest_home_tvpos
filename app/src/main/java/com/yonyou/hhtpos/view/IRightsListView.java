package com.yonyou.hhtpos.view;

import com.yonyou.framework.library.view.BaseView;
import com.yonyou.hhtpos.bean.RightsEntity;


/**
 * Created by zj on 2017/8/9.
 * 邮箱：zjuan@yonyou.com
 * 描述：查询权限列表
 */
public interface IRightsListView extends BaseView {
    void requestRightsList(RightsEntity rightsEntity);
}

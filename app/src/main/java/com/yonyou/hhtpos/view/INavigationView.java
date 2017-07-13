package com.yonyou.hhtpos.view;

import com.yonyou.framework.library.view.BaseView;
import com.yonyou.hhtpos.bean.NavigationNewEntity;

import java.util.List;

/**
 * 作者：liushuofei on 2017/7/11 17:19
 * 邮箱：lsf@yonyou.com
 */
public interface INavigationView extends BaseView {

    /**
     * 请求导航栏数据
     * @param dataList
     */
    void requestNavigationList(List<NavigationNewEntity> dataList);
}

package com.yonyou.hhtpos.view;

import com.yonyou.framework.library.view.BaseView;

/**
 * 作者：liushuofei on 2017/6/28 10:40
 * 邮箱：lsf@yonyou.com
 */
public interface IActivateAppView extends BaseView {

    void requestActivateCode();

    void doActivate();
}

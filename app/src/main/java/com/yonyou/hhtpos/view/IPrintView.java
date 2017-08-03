package com.yonyou.hhtpos.view;

import com.yonyou.framework.library.view.BaseView;

/**
 * 作者：liushuofei on 2017/8/2 15:15
 * 邮箱：lsf@yonyou.com
 */
public interface IPrintView extends BaseView {

    void requestPrintOrder(String[] bytes);
}

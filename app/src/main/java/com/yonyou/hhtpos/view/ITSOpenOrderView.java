package com.yonyou.hhtpos.view;

import com.yonyou.framework.library.view.BaseView;
import com.yonyou.hhtpos.bean.ts.TSTableBillIdEntity;

/**
 * 作者：liushuofei on 2017/7/19 09:43
 * 邮箱：lsf@yonyou.com
 */
public interface ITSOpenOrderView extends BaseView {
    void openOrder(TSTableBillIdEntity result);
}

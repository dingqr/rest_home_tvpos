package com.yonyou.hhtpos.ui.dinner.wm;

import android.os.Bundle;
import android.view.View;

import com.yonyou.framework.library.base.BaseFragment;
import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.hhtpos.R;

/**
 * 外卖列表fragment
 * 作者：liushuofei on 2017/7/6 10:47
 */
public class FRA_TakeOutList extends BaseFragment {

    /**传入数据 */
    public static final String TYPE = "type";
    private int type;

    public static final FRA_TakeOutList newInstance(int type) {
        FRA_TakeOutList f = new FRA_TakeOutList();
        Bundle bdl = new Bundle(1);
        bdl.putInt(TYPE, type);
        f.setArguments(bdl);
        return f;
    }

    @Override
    protected void onFirstUserVisible() {

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {
        // 无数据页面
         showEmpty(R.drawable.default_no_order, mContext.getString(R.string.take_out_order_no_data));
    }

    @Override
    protected int getContentViewLayoutID() {
        return 0;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    public void showBusinessError(ErrorBean error) {

    }
}

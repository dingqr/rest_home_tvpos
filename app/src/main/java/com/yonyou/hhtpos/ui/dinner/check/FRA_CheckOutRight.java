package com.yonyou.hhtpos.ui.dinner.check;

import android.view.View;
import android.widget.GridView;

import com.yonyou.framework.library.base.BaseFragment;
import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.adapter.ADA_CheckOutPayType;
import com.yonyou.hhtpos.adapter.ADA_DiscountType;

import butterknife.Bind;

/**
 * 结账页面右侧fragment
 * 作者：liushuofei on 2017/7/19 14:49
 */
public class FRA_CheckOutRight extends BaseFragment {

    @Bind(R.id.gv_discount_type)
    GridView mDiscountTypeGv;
    @Bind(R.id.gv_pay_type)
    GridView mPayTypeGv;

    private ADA_DiscountType mDiscountAdapter;
    private ADA_CheckOutPayType mPayTypeAdapter;

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
        mDiscountAdapter = new ADA_DiscountType(mContext);
        mPayTypeAdapter = new ADA_CheckOutPayType(mContext);
        mDiscountTypeGv.setAdapter(mDiscountAdapter);
        mPayTypeGv.setAdapter(mPayTypeAdapter);

        for (int i = 0; i < 3; i++){
            mDiscountAdapter.update("");
        }

        for (int i = 0; i < 9; i++){
            mPayTypeAdapter.update("");
        }
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fra_check_out_right;
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

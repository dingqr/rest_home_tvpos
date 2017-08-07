package com.yonyou.hhtpos.ui.member;

import android.view.View;

import com.yonyou.framework.library.base.BaseFragment;
import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.dialog.DIA_MemberCouponList;
import com.yonyou.hhtpos.dialog.DIA_TransactionList;

import butterknife.OnClick;

/**
 * Created by zj on 2017/8/4.
 * 邮箱：zjuan@yonyou.com
 * 描述：会员信息详情页面
 */
public class FRA_MemberDetail extends BaseFragment {

    private DIA_TransactionList mDiaTransactionList;
    private DIA_MemberCouponList mDiaMemberCouponList;

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
        mDiaTransactionList = new DIA_TransactionList(mContext);
        mDiaMemberCouponList = new DIA_MemberCouponList(mContext);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fra_member_detail;
    }

    @OnClick({R.id.tv_check_transaction_records,R.id.ll_coupon})
    public void onClick(View view) {
        switch (view.getId()) {
            //查看交易记录
            case R.id.tv_check_transaction_records:
                mDiaTransactionList.show();
                break;
            //优惠券
            case R.id.ll_coupon:
                mDiaMemberCouponList.show();
                break;
        }
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

package com.yonyou.hhtpos.presenter.Impl;

import android.content.Context;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.CommonUtils;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.interactor.IWDCloseOrderInteractor;
import com.yonyou.hhtpos.interactor.Impl.WDCloseOrderInteractor;
import com.yonyou.hhtpos.presenter.IWDCloseOrderPresenter;
import com.yonyou.hhtpos.view.IWDCloseOrderView;

/**
 * 作者：liushuofei on 2017/7/25 19:01
 * 邮箱：lsf@yonyou.com
 */
public class WDCloseOrderPresenterImpl implements IWDCloseOrderPresenter {

    private Context mContext;
    private IWDCloseOrderView mView;
    private IWDCloseOrderInteractor mInteractor;

    public WDCloseOrderPresenterImpl(Context mContext, final IWDCloseOrderView mView) {
        this.mContext = mContext;
        this.mView = mView;
        mInteractor = new WDCloseOrderInteractor(new CloseOrderListener());
    }

    @Override
    public void closeOrder(String tableBillId) {
        mInteractor.closeOrder(tableBillId);
    }

    private class CloseOrderListener implements BaseLoadedListener<String> {

        @Override
        public void onSuccess(int event_tag, String data) {
            mView.closeOrder();
        }

        @Override
        public void onError(String msg) {
            CommonUtils.makeEventToast(mContext, msg, false);
        }

        @Override
        public void onException(String msg) {
            CommonUtils.makeEventToast(mContext, msg, false);
        }

        @Override
        public void onBusinessError(ErrorBean error) {
            CommonUtils.makeEventToast(mContext, error.getMsg(), false);
        }
    }
}

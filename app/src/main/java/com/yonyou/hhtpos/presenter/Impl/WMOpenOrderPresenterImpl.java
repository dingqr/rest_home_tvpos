package com.yonyou.hhtpos.presenter.Impl;

import android.content.Context;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.CommonUtils;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.bean.wm.OpenOrderEntity;
import com.yonyou.hhtpos.interactor.IWMOpenOrderInteractor;
import com.yonyou.hhtpos.interactor.Impl.WMOpenOrderInteractorImpl;
import com.yonyou.hhtpos.presenter.IWMOpenOrderPresenter;
import com.yonyou.hhtpos.view.IWMOpenOrderView;

/**
 * 作者：liushuofei on 2017/7/15 17:52
 * 邮箱：lsf@yonyou.com
 */
public class WMOpenOrderPresenterImpl implements IWMOpenOrderPresenter {

    private Context mContext;
    private IWMOpenOrderView mView;
    private IWMOpenOrderInteractor mInteractor;

    public WMOpenOrderPresenterImpl(Context mContext, final IWMOpenOrderView mView) {
        this.mContext = mContext;
        this.mView = mView;
        mInteractor = new WMOpenOrderInteractorImpl(new TakeOutListener());
    }

    @Override
    public void openOrder(OpenOrderEntity bean) {
        mView.showDialogLoading(mContext.getResources().getString(R.string.common_loading_message));
        mInteractor.openOrder(bean);
    }

    private class TakeOutListener implements BaseLoadedListener<String> {

        @Override
        public void onSuccess(int event_tag, String bean) {
            mView.dismissDialogLoading();
            mView.openOrder();
        }

        @Override
        public void onError(String msg) {
            mView.dismissDialogLoading();
            CommonUtils.makeEventToast(mContext, msg, false);
        }

        @Override
        public void onException(String msg) {
            mView.dismissDialogLoading();
            mView.showException(msg);
            //CommonUtils.makeEventToast(mContext, msg, false);
        }

        @Override
        public void onBusinessError(ErrorBean error) {
            mView.dismissDialogLoading();
            mView.showBusinessError(error);
            //CommonUtils.makeEventToast(mContext, error.getMsg(), false);
        }
    }
}

package com.yonyou.hhtpos.presenter.Impl;

import android.content.Context;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.CommonUtils;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.bean.WMOpenOrderEntity;
import com.yonyou.hhtpos.interactor.ITakeOutInteractor;
import com.yonyou.hhtpos.interactor.Impl.TakeOutInteractorImpl;
import com.yonyou.hhtpos.presenter.ITakeOutPresenter;
import com.yonyou.hhtpos.view.ITakeOutView;

/**
 * 作者：liushuofei on 2017/7/15 17:52
 * 邮箱：lsf@yonyou.com
 */
public class TakeOutPresenterImpl implements ITakeOutPresenter {

    private Context mContext;
    private ITakeOutView mView;
    private ITakeOutInteractor mInteractor;

    public TakeOutPresenterImpl(Context mContext, final ITakeOutView mView) {
        this.mContext = mContext;
        this.mView = mView;
        mInteractor = new TakeOutInteractorImpl(new TakeOutListener());
    }

    @Override
    public void openOrder(WMOpenOrderEntity bean) {
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

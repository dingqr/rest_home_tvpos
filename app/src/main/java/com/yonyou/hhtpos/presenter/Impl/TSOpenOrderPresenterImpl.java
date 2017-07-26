package com.yonyou.hhtpos.presenter.Impl;

import android.content.Context;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.CommonUtils;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.bean.ts.OpenOrderEntity;
import com.yonyou.hhtpos.interactor.ITSOpenOrderInteractor;
import com.yonyou.hhtpos.interactor.Impl.TSOpenOrderInteractorImpl;
import com.yonyou.hhtpos.presenter.ITSOpenOrderPresenter;
import com.yonyou.hhtpos.view.ITSOpenOrderView;

/**
 * 作者：liushuofei on 2017/7/19 10:01
 * 邮箱：lsf@yonyou.com
 */
public class TSOpenOrderPresenterImpl implements ITSOpenOrderPresenter {

    private Context mContext;
    private ITSOpenOrderView mTSOpenOrderView;
    private ITSOpenOrderInteractor mTSOpenOrderInteractor;

    public TSOpenOrderPresenterImpl(Context mContext, ITSOpenOrderView mTSOpenOrderView) {
        this.mContext = mContext;
        this.mTSOpenOrderView = mTSOpenOrderView;
        mTSOpenOrderInteractor = new TSOpenOrderInteractorImpl(new OpenOrderListener());
    }

    @Override
    public void openOrder(OpenOrderEntity bean) {
        mTSOpenOrderView.showDialogLoading(mContext.getString(R.string.network_loading));
        mTSOpenOrderInteractor.openOrder(bean);
    }

    private class OpenOrderListener implements BaseLoadedListener<String> {

        @Override
        public void onSuccess(int event_tag, String dataList) {
            mTSOpenOrderView.dismissDialogLoading();
            mTSOpenOrderView.openOrder(dataList);
        }

        @Override
        public void onError(String msg) {
            mTSOpenOrderView.dismissDialogLoading();
            CommonUtils.makeEventToast(mContext, msg, false);
        }

        @Override
        public void onException(String msg) {
            mTSOpenOrderView.dismissDialogLoading();
            mTSOpenOrderView.showException(msg);
            //CommonUtils.makeEventToast(mContext, msg, false);
        }

        @Override
        public void onBusinessError(ErrorBean error) {
            mTSOpenOrderView.dismissDialogLoading();
            mTSOpenOrderView.showBusinessError(error);
            //CommonUtils.makeEventToast(mContext, error.getMsg(), false);
        }
    }
}

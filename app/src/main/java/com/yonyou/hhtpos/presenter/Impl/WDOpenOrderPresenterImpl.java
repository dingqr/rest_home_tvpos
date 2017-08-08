package com.yonyou.hhtpos.presenter.Impl;

import android.content.Context;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.CommonUtils;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.bean.wd.OpenOrderEntity;
import com.yonyou.hhtpos.bean.wd.OpenOrderSuccessEntity;
import com.yonyou.hhtpos.interactor.IWDOpenOrderInteractor;
import com.yonyou.hhtpos.interactor.Impl.WDOpenOrderInteractorImpl;
import com.yonyou.hhtpos.presenter.IWDOpenOrderPresenter;
import com.yonyou.hhtpos.view.IWDOpenOrderView;

/**
 * 作者：liushuofei on 2017/7/18 10:58
 * 邮箱：lsf@yonyou.com
 */
public class WDOpenOrderPresenterImpl implements IWDOpenOrderPresenter {

    private Context mContext;
    private IWDOpenOrderView mPackingLeftView;
    private IWDOpenOrderInteractor mPackingListInteractor;

    public WDOpenOrderPresenterImpl(Context mContext, IWDOpenOrderView mPackingLeftView) {
        this.mContext = mContext;
        this.mPackingLeftView = mPackingLeftView;
        mPackingListInteractor = new WDOpenOrderInteractorImpl(new OpenOrderListener());
    }

    @Override
    public void openOrder(OpenOrderEntity bean) {
        mPackingLeftView.showDialogLoading(mContext.getString(R.string.network_loading));
        mPackingListInteractor.openOrder(bean);
    }

    private class OpenOrderListener implements BaseLoadedListener<OpenOrderSuccessEntity> {

        @Override
        public void onSuccess(int event_tag, OpenOrderSuccessEntity result) {
            mPackingLeftView.dismissDialogLoading();
            mPackingLeftView.openOrder(result);
        }

        @Override
        public void onError(String msg) {
            mPackingLeftView.dismissDialogLoading();
            CommonUtils.makeEventToast(mContext, msg, false);
        }

        @Override
        public void onException(String msg) {
            mPackingLeftView.dismissDialogLoading();
            mPackingLeftView.showException(msg);
            //CommonUtils.makeEventToast(mContext, msg, false);
        }

        @Override
        public void onBusinessError(ErrorBean error) {
            mPackingLeftView.dismissDialogLoading();
            mPackingLeftView.showBusinessError(error);
            //CommonUtils.makeEventToast(mContext, error.getMsg(), false);
        }
    }

}

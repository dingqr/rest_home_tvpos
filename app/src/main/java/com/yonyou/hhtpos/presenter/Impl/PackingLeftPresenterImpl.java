package com.yonyou.hhtpos.presenter.Impl;

import android.content.Context;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.CommonUtils;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.bean.WDOpenOrderEntity;
import com.yonyou.hhtpos.interactor.IPackingLeftInteractor;
import com.yonyou.hhtpos.interactor.Impl.PackingLeftInteractorImpl;
import com.yonyou.hhtpos.presenter.IPackingLeftPresenter;
import com.yonyou.hhtpos.view.IPackingLeftView;

/**
 * 作者：liushuofei on 2017/7/18 10:58
 * 邮箱：lsf@yonyou.com
 */
public class PackingLeftPresenterImpl implements IPackingLeftPresenter {

    private Context mContext;
    private IPackingLeftView mPackingLeftView;
    private IPackingLeftInteractor mPackingListInteractor;

    public PackingLeftPresenterImpl(Context mContext, IPackingLeftView mPackingLeftView) {
        this.mContext = mContext;
        this.mPackingLeftView = mPackingLeftView;
        mPackingListInteractor = new PackingLeftInteractorImpl(new OpenOrderListener());
    }

    @Override
    public void openOrder(WDOpenOrderEntity bean) {
        mPackingLeftView.showDialogLoading(mContext.getString(R.string.network_loading));
        mPackingListInteractor.openOrder(bean);
    }

    private class OpenOrderListener implements BaseLoadedListener<String> {

        @Override
        public void onSuccess(int event_tag, String dataList) {
            mPackingLeftView.dismissDialogLoading();
            mPackingLeftView.openOrder();
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

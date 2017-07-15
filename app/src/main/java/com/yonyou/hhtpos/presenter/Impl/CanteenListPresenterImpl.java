package com.yonyou.hhtpos.presenter.Impl;

import android.content.Context;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.CommonUtils;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.interactor.ICanteenListInteractor;
import com.yonyou.hhtpos.interactor.Impl.CanteenListInteractorImpl;
import com.yonyou.hhtpos.presenter.ICanteenListPresenter;
import com.yonyou.hhtpos.view.ICanteenListView;

/**
 * 作者：liushuofei on 2017/7/14 17:23
 * 邮箱：lsf@yonyou.com
 */
public class CanteenListPresenterImpl implements ICanteenListPresenter {

    private Context mContext;
    private ICanteenListView mCanteenListView;
    private ICanteenListInteractor mCanteenListInteractor;

    public CanteenListPresenterImpl(Context mContext, ICanteenListView mCanteenListView) {
        this.mContext = mContext;
        this.mCanteenListView = mCanteenListView;
        mCanteenListInteractor = new CanteenListInteractorImpl(new OpenOrderListener());
    }

    @Override
    public void openOrder(String billRemark, String companyId, String memberId, String personNum, String shopId, String tableNo, String waiterId) {
        mCanteenListView.showDialogLoading(mContext.getString(R.string.network_loading));
        mCanteenListInteractor.openOrder(billRemark, companyId, memberId, personNum, shopId, tableNo, waiterId);
    }

    private class OpenOrderListener implements BaseLoadedListener<String> {

        @Override
        public void onSuccess(int event_tag, String bean) {
            mCanteenListView.dismissDialogLoading();
            mCanteenListView.openOrder();
        }

        @Override
        public void onError(String msg) {
            mCanteenListView.dismissDialogLoading();
            CommonUtils.makeEventToast(mContext, msg, false);
        }

        @Override
        public void onException(String msg) {
            mCanteenListView.dismissDialogLoading();
            mCanteenListView.showException(msg);
            //CommonUtils.makeEventToast(mContext, msg, false);
        }

        @Override
        public void onBusinessError(ErrorBean error) {
            mCanteenListView.dismissDialogLoading();
            mCanteenListView.showBusinessError(error);
            //CommonUtils.makeEventToast(mContext, error.getMsg(), false);
        }
    }
}

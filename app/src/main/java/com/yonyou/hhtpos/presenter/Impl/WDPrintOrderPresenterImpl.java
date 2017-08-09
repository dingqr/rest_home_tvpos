package com.yonyou.hhtpos.presenter.Impl;

import android.content.Context;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.CommonUtils;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.interactor.IWDPrintOrderInteractor;
import com.yonyou.hhtpos.interactor.Impl.WDPrintOrderInteractorImpl;
import com.yonyou.hhtpos.presenter.IWDPrintOrderPresenter;
import com.yonyou.hhtpos.view.IWDPrintOrderView;

/**
 * Created by zj on 2017/8/9.
 * 邮箱：zjuan@yonyou.com
 * 描述：外带补打账单
 */
public class WDPrintOrderPresenterImpl implements IWDPrintOrderPresenter {
    private Context mContext;
    private IWDPrintOrderView mPrintView;
    private IWDPrintOrderInteractor mPrintInteractor;

    public WDPrintOrderPresenterImpl(Context context, IWDPrintOrderView printView) {
        this.mContext = context;
        this.mPrintView = printView;
        mPrintInteractor = new WDPrintOrderInteractorImpl(new PrintListListener());
    }


    @Override
    public void requestPrintOrder(String tableBillId, String shopId) {
        mPrintView.showDialogLoading(mContext.getResources().getString(R.string.common_loading_message));
        mPrintInteractor.requestPrintOrder(tableBillId, shopId);
    }

    private class PrintListListener implements BaseLoadedListener<String> {

        @Override
        public void onSuccess(int event_tag, String data) {
            mPrintView.dismissDialogLoading();
            mPrintView.requestPrintOrder(data);
        }

        @Override
        public void onError(String msg) {
            mPrintView.dismissDialogLoading();
            CommonUtils.makeEventToast(mContext, msg, false);
        }

        @Override
        public void onException(String msg) {
            mPrintView.dismissDialogLoading();
            mPrintView.showException(msg);
            CommonUtils.makeEventToast(mContext, msg, false);
        }

        @Override
        public void onBusinessError(ErrorBean error) {
            mPrintView.showBusinessError(error);
            CommonUtils.makeEventToast(mContext, error.getMsg(), false);
        }
    }
}

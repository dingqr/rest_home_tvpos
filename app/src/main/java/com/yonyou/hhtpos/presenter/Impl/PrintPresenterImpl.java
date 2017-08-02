package com.yonyou.hhtpos.presenter.Impl;

import android.content.Context;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.CommonUtils;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.interactor.IPrintInteractor;
import com.yonyou.hhtpos.interactor.Impl.PrintInteractorImpl;
import com.yonyou.hhtpos.presenter.IPrintPresenter;
import com.yonyou.hhtpos.view.IPrintView;


/**
 * 作者：liushuofei on 2017/8/2 18:52
 * 邮箱：lsf@yonyou.com
 */
public class PrintPresenterImpl implements IPrintPresenter{

    private Context mContext;
    private IPrintView mPrintView;
    private IPrintInteractor mPrintInteractor;

    public PrintPresenterImpl(Context mContext, IPrintView mPrintView) {
        this.mContext = mContext;
        this.mPrintView = mPrintView;
        mPrintInteractor = new PrintInteractorImpl(new PrintListener());
    }

    @Override
    public void requestPrintOrder() {
        mPrintInteractor.requestPrintOrder();
    }

    private class PrintListener implements BaseLoadedListener<String[]> {

        @Override
        public void onSuccess(int event_tag, String[] bytes) {
//            mPrintView.hideLoading();
            mPrintView.requestPrintOrder(bytes);
        }

        @Override
        public void onError(String msg) {
//            mPrintView.hideLoading();
            CommonUtils.makeEventToast(mContext, msg, false);
        }

        @Override
        public void onException(String msg) {
//            mPrintView.hideLoading();
            mPrintView.showException(msg);
            //CommonUtils.makeEventToast(mContext, msg, false);
        }

        @Override
        public void onBusinessError(ErrorBean error) {
//            mPrintView.hideLoading();
            mPrintView.showBusinessError(error);
            //CommonUtils.makeEventToast(mContext, error.getMsg(), false);
        }
    }
}

package com.yonyou.hhtpos.presenter.Impl;

import android.content.Context;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.CommonUtils;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.interactor.IActivateAppInteractor;
import com.yonyou.hhtpos.interactor.Impl.ActivateAppInteractorImpl;
import com.yonyou.hhtpos.presenter.IActivateAppPresenter;
import com.yonyou.hhtpos.view.IActivateAppView;

/**
 * 作者：liushuofei on 2017/6/28 11:04
 * 邮箱：lsf@yonyou.com
 */
public class ActivateAppPresenterImpl implements IActivateAppPresenter {

    private Context mContext;
    private IActivateAppView mActivateAppView;
    private IActivateAppInteractor mActivateAppInteractor;

    public ActivateAppPresenterImpl(Context mContext, IActivateAppView mActivateAppView) {
        this.mContext = mContext;
        this.mActivateAppView = mActivateAppView;
        mActivateAppInteractor = new ActivateAppInteractorImpl(new CodeListener(), new ActivateListener());
    }

    @Override
    public void requestActivateCode() {

    }

    @Override
    public void doActivate() {

    }

    private class CodeListener implements BaseLoadedListener<String> {

        @Override
        public void onSuccess(int event_tag, String bean) {
            mActivateAppView.hideLoading();
            mActivateAppView.requestActivateCode();
        }

        @Override
        public void onError(String msg) {
            mActivateAppView.hideLoading();
            CommonUtils.makeEventToast(mContext, msg, false);
        }

        @Override
        public void onException(String msg) {
            mActivateAppView.hideLoading();
            mActivateAppView.showException(msg);
            //CommonUtils.makeEventToast(mContext, msg, false);
        }

        @Override
        public void onBusinessError(ErrorBean error) {
            mActivateAppView.hideLoading();
            mActivateAppView.showBusinessError(error);
            //CommonUtils.makeEventToast(mContext, error.getMsg(), false);
        }
    }

    private class ActivateListener implements BaseLoadedListener<String> {

        @Override
        public void onSuccess(int event_tag, String bean) {
            mActivateAppView.hideLoading();
            mActivateAppView.doActivate();
        }

        @Override
        public void onError(String msg) {
            mActivateAppView.hideLoading();
            CommonUtils.makeEventToast(mContext, msg, false);
        }

        @Override
        public void onException(String msg) {
            mActivateAppView.hideLoading();
            mActivateAppView.showException(msg);
            //CommonUtils.makeEventToast(mContext, msg, false);
        }

        @Override
        public void onBusinessError(ErrorBean error) {
            mActivateAppView.hideLoading();
            mActivateAppView.showBusinessError(error);
            //CommonUtils.makeEventToast(mContext, error.getMsg(), false);
        }
    }

}

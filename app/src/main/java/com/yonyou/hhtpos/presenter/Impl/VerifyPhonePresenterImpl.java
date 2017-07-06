package com.yonyou.hhtpos.presenter.Impl;

import android.content.Context;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.CommonUtils;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.interactor.IVerifyPhoneInteractor;
import com.yonyou.hhtpos.interactor.Impl.VerifyPhoneInteractorImpl;
import com.yonyou.hhtpos.presenter.IVerifyPhonePresenter;
import com.yonyou.hhtpos.view.IVerifyPhoneView;

/**
 * 作者：liushuofei on 2017/7/5 16:39
 * 邮箱：lsf@yonyou.com
 */
public class VerifyPhonePresenterImpl implements IVerifyPhonePresenter {

    private Context mContext;
    private IVerifyPhoneView mVerifyPhoneView;
    private IVerifyPhoneInteractor mVerifyPhoneInteractor;

    public VerifyPhonePresenterImpl(Context mContext, IVerifyPhoneView mVerifyPhoneView) {
        this.mContext = mContext;
        this.mVerifyPhoneView = mVerifyPhoneView;
        mVerifyPhoneInteractor = new VerifyPhoneInteractorImpl(new CodeListener(), new VerifyListener());
    }

    @Override
    public void sendSmsCode(String companyId, String shopId, String mobileNo) {
        mVerifyPhoneView.showDialogLoading(mContext.getString(R.string.network_loading));
        mVerifyPhoneInteractor.sendSmsCode(companyId, shopId, mobileNo);
    }

    @Override
    public void verifyPhone(String companyId, String shopId, String mobileNo, String msgCode) {
        mVerifyPhoneView.showDialogLoading(mContext.getString(R.string.network_loading));
        mVerifyPhoneInteractor.verifyPhone(companyId, shopId, mobileNo, msgCode);
    }

    private class CodeListener implements BaseLoadedListener<String> {

        @Override
        public void onSuccess(int event_tag, String bean) {
            mVerifyPhoneView.dismissDialogLoading();
            mVerifyPhoneView.sendSmsCode();
        }

        @Override
        public void onError(String msg) {
            mVerifyPhoneView.dismissDialogLoading();
            CommonUtils.makeEventToast(mContext, msg, false);
        }

        @Override
        public void onException(String msg) {
            mVerifyPhoneView.dismissDialogLoading();
            mVerifyPhoneView.showException(msg);
            //CommonUtils.makeEventToast(mContext, msg, false);
        }

        @Override
        public void onBusinessError(ErrorBean error) {
            mVerifyPhoneView.dismissDialogLoading();
            mVerifyPhoneView.showBusinessError(error);
            //CommonUtils.makeEventToast(mContext, error.getMsg(), false);
        }
    }

    private class VerifyListener implements BaseLoadedListener<String> {

        @Override
        public void onSuccess(int event_tag, String bean) {
            mVerifyPhoneView.dismissDialogLoading();
            mVerifyPhoneView.verifyPhone();
        }

        @Override
        public void onError(String msg) {
            mVerifyPhoneView.dismissDialogLoading();
            CommonUtils.makeEventToast(mContext, msg, false);
        }

        @Override
        public void onException(String msg) {
            mVerifyPhoneView.dismissDialogLoading();
            mVerifyPhoneView.showException(msg);
            //CommonUtils.makeEventToast(mContext, msg, false);
        }

        @Override
        public void onBusinessError(ErrorBean error) {
            mVerifyPhoneView.dismissDialogLoading();
            mVerifyPhoneView.showBusinessError(error);
            //CommonUtils.makeEventToast(mContext, error.getMsg(), false);
        }
    }

}

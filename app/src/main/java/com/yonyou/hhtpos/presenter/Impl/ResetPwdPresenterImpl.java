package com.yonyou.hhtpos.presenter.Impl;

import android.content.Context;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.CommonUtils;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.interactor.Impl.ResetPwdInteractorImpl;
import com.yonyou.hhtpos.presenter.IResetPwdPresenter;
import com.yonyou.hhtpos.view.IResetPwdView;

/**
 * Created by ybing on 2017/7/5.
 */

public class ResetPwdPresenterImpl implements IResetPwdPresenter{

    private Context mContext;
    private IResetPwdView mView;
    private ResetPwdInteractorImpl mInteractor;

    public ResetPwdPresenterImpl(Context mContext, IResetPwdView mView) {
        this.mContext = mContext;
        this.mView = mView;
        mInteractor = new ResetPwdInteractorImpl(new ResetPwdLoadedListener());
    }

    @Override
    public void resetPwd(String mobileNo, String msgCode, String newPassword) {
        mView.showDialogLoading(mContext.getResources().getString(R.string.common_loading_message));
        mInteractor.resetPwd(mobileNo,msgCode,newPassword);
    }

    private class ResetPwdLoadedListener implements BaseLoadedListener<String>{

        @Override
        public void onSuccess(int event_tag, String data) {
            mView.dismissDialogLoading();
            mView.resetPwd(data);
        }

        @Override
        public void onError(String msg) {
            mView.dismissDialogLoading();
            mView.hideLoading();
            CommonUtils.makeEventToast(mContext, msg, false);
        }

        @Override
        public void onException(String msg) {
            mView.hideLoading();
            mView.dismissDialogLoading();
            CommonUtils.makeEventToast(mContext, msg, false);
        }

        @Override
        public void onBusinessError(ErrorBean error) {
            mView.hideLoading();
            mView.dismissDialogLoading();
            CommonUtils.makeEventToast(mContext, error.getMsg(), false);
        }
    }
}

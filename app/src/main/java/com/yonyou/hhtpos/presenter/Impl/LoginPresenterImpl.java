package com.yonyou.hhtpos.presenter.Impl;

import android.content.Context;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.CommonUtils;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.db.entity.UserEntity;
import com.yonyou.hhtpos.interactor.ILoginInteractor;
import com.yonyou.hhtpos.interactor.Impl.LoginInteractorImpl;
import com.yonyou.hhtpos.presenter.ILoginPresenter;
import com.yonyou.hhtpos.view.ILoginView;

/**
 * Created by ybing on 2017/7/5.
 */

public class LoginPresenterImpl implements ILoginPresenter{

    private Context mContext;
    private ILoginView mView;
    private ILoginInteractor mInteractor;

    public LoginPresenterImpl(Context mContext, final ILoginView mView) {
        this.mContext = mContext;
        this.mView = mView;
        mInteractor = new LoginInteractorImpl(new LoginListener());
    }

    @Override
    public void login(String companyId, String deviceType, String mobileNo, String password, String shopId) {
        mView.showDialogLoading(mContext.getResources().getString(R.string.common_loading_message));
        mInteractor.login(companyId,deviceType,mobileNo,password,shopId);
    }

    private class LoginListener implements BaseLoadedListener<UserEntity>{

        @Override
        public void onSuccess(int event_tag, UserEntity data) {
            mView.dismissDialogLoading();
            mView.login(data);
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

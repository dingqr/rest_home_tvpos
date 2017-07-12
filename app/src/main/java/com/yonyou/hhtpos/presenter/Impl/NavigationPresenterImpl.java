package com.yonyou.hhtpos.presenter.Impl;

import android.content.Context;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.bean.NavigationNewEntity;
import com.yonyou.hhtpos.interactor.INavigationInteractor;
import com.yonyou.hhtpos.interactor.Impl.NavigationInteractorImpl;
import com.yonyou.hhtpos.presenter.INavigationPresenter;
import com.yonyou.hhtpos.view.INavigationView;

/**
 * 作者：liushuofei on 2017/7/11 17:30
 * 邮箱：lsf@yonyou.com
 */
public class NavigationPresenterImpl implements INavigationPresenter {

    private Context mContext;
    private INavigationView mView;
    private INavigationInteractor mInteractor;

    public NavigationPresenterImpl(Context mContext, final INavigationView mView) {
        this.mContext = mContext;
        this.mView = mView;
        mInteractor = new NavigationInteractorImpl(new NavigationListener());
    }

    @Override
    public void requestNavigationList(String functionCode) {
        mInteractor.requestNavigationList(functionCode);
    }

    private class NavigationListener implements BaseLoadedListener<NavigationNewEntity> {

        @Override
        public void onSuccess(int event_tag, NavigationNewEntity bean) {
            mView.requestNavigationList(bean);
        }

        @Override
        public void onError(String msg) {
            //CommonUtils.makeEventToast(mContext, msg, false);
        }

        @Override
        public void onException(String msg) {
            //CommonUtils.makeEventToast(mContext, msg, false);
        }

        @Override
        public void onBusinessError(ErrorBean error) {
            //CommonUtils.makeEventToast(mContext, error.getMsg(), false);
        }
    }

}

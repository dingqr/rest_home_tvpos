package com.yonyou.hhtpos.presenter.Impl;

import android.content.Context;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.CommonUtils;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.bean.TakeoutCompanyEntity;
import com.yonyou.hhtpos.interactor.ITakeoutCompanyInteractor;
import com.yonyou.hhtpos.interactor.Impl.TakeoutCompanyInteractorImpl;
import com.yonyou.hhtpos.presenter.ITakeoutCompanyPresenter;
import com.yonyou.hhtpos.view.ITakeoutCompanyView;

import java.util.List;

/**
 * Created by ybing on 2017/7/5.
 * 获取外卖筛选框中的外卖公司
 */

public class TakeoutCompanyPresenterImpl implements ITakeoutCompanyPresenter{

    private Context mContext;
    private ITakeoutCompanyView mView;
    private ITakeoutCompanyInteractor mInteractor;

    public TakeoutCompanyPresenterImpl(Context mContext, final ITakeoutCompanyView mView) {
        this.mContext = mContext;
        this.mView = mView;
        mInteractor = new TakeoutCompanyInteractorImpl(new TakeoutCompanyListener());
    }

    @Override
    public void getAllTakeOutCompany( String shopId) {
        mView.showDialogLoading(mContext.getResources().getString(R.string.common_loading_message));
        mInteractor.getAllTakeOutCompany(shopId);
    }

    private class TakeoutCompanyListener implements BaseLoadedListener<List<TakeoutCompanyEntity>>{

        @Override
        public void onSuccess(int event_tag, List<TakeoutCompanyEntity> data) {
            mView.dismissDialogLoading();
            mView.getAllTakeOutCompany(data);
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
